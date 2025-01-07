import classNames from 'classnames/bind';
//Hiểu ứng khi cuộn trang
import Aos from 'aos';
import 'aos/dist/aos.css';

import styles from './Profile.module.scss';
import { Col, Form, Row } from 'react-bootstrap';
import video from '~/assets/videos/videoprofile.mp4';
import { useContext, useEffect, useRef, useState } from 'react';
import { MyUserContext } from '~/App';
import image from '~/assets/images/user.png';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAngleLeft, faCamera, faKey, faPause, faPlay, faUserPen } from '@fortawesome/free-solid-svg-icons';
import Button from '~/common/Button';
import APIs, { authAPIs, endpoints } from '~/configs/APIs';
import { formatPrice } from '~/Utils/Utils';
import MyAlert from '~/Utils/Alert';
import MySpinner from '~/common/Spinner';
import { faEye, faEyeSlash } from '@fortawesome/free-regular-svg-icons';
import { changePasswordOnFirebase, reauthenticate } from '~/Firebase/Firebase';

const cx = classNames.bind(styles);

function Profile() {
    const user = useContext(MyUserContext);
    const [visible, setVisible] = useState(false);
    const [visibleNew, setVisibleNew] = useState(false);
    const [visibleNewConfirm, setVisibleNewConfirm] = useState(false);
    const [loading, setLoading] = useState(false);
    const [showAlert, setShowAlert] = useState(false);
    const videoRef = useRef(null);
    const fileInputRef = useRef(null);
    const [isPlaying, setIsPlaying] = useState(false);
    const [, setInvoices] = useState([]);
    const [invoiceDetails, setInvoiceDetails] = useState([]);
    const [isEditing, setIsEditing] = useState(false);
    const [updatedUser, setUpdatedUser] = useState({ ...user });
    const [showCameraIcon, setShowCameraIcon] = useState(false);
    const [showPasswordFields, setShowPasswordFields] = useState(false);

    // OTP
    const [err, setErr] = useState('');
    const [otp, setOtp] = useState('');
    const [generatedOtp, setGeneratedOtp] = useState(null);
    const [otpSent, setOtpSent] = useState(false);
    const [otpExpiry, setOtpExpiry] = useState(null); // Thời gian hết hạn OTP
    const [canResendOtp, setCanResendOtp] = useState(false); // Có thể gửi lại OTP không
    const [countdown, setCountdown] = useState(0); // Thời gian đếm ngược

    // Mã OTP
    const OTP_EXPIRY_DURATION = 1 * 60 * 1000; // 5 phút

    const generateOTP = () => Math.floor(100000 + Math.random() * 900000);

    const loadInvoices = async () => {
        try {
            let resInvoices = await APIs.get(endpoints['invoice'](user.id));
            setInvoices(resInvoices.data);
            if (resInvoices.data.length > 0) {
                let invoiceIds = resInvoices.data.map((invoice) => invoice.id);

                let invoiceDetailsPromises = invoiceIds.map((invoiceId) => {
                    const url = endpoints['invoicedetail'](invoiceId);
                    return APIs.get(url);
                });
                let resInvoiceDetailsArray = await Promise.all(invoiceDetailsPromises);
                let allInvoiceDetails = resInvoiceDetailsArray.flatMap((res) => res.data);

                let resEquipment = await APIs.get(endpoints['equipments']);
                let allFilteredInvoiceDetailbyEquipmentId = allInvoiceDetails.map((invoiceDetail) => {
                    let equipment = resEquipment.data.data.filter((e) => e.id === invoiceDetail.equipmentId.id);
                    if (equipment) {
                        return {
                            ...invoiceDetail,
                        };
                    }
                    return invoiceDetail;
                });
                setInvoiceDetails(allFilteredInvoiceDetailbyEquipmentId);
            }
        } catch (error) {
            console.log('Lỗi invoices: ' + error.message);
        }
    };

    useEffect(() => {
        loadInvoices();
        Aos.init();
    }, []);

    const handlePlayPause = () => {
        if (videoRef.current) {
            if (isPlaying) {
                videoRef.current.pause();
            } else {
                videoRef.current.play();
            }
            setIsPlaying(!isPlaying);
        }
    };

    const handleEditClick = () => {
        setIsEditing(true);
        setShowCameraIcon(true);
        setShowPasswordFields(false);
    };

    const handleBackClick = () => {
        // Quay lại thông tin ban đầu
        setIsEditing(false);
        setShowCameraIcon(false);
        setShowPasswordFields(false);
    };

    const handleSaveClick = async () => {
        const formData = new FormData();

        // Nếu cập nhật thông tin người dùng có cả file ảnh (avatar)
        formData.append('firstName', updatedUser.firstName);
        formData.append('lastName', updatedUser.lastName);
        formData.append('email', updatedUser.email);
        formData.append('phone', updatedUser.phone);

        if (fileInputRef.current && fileInputRef.current.files[0]) {
            formData.append('avatar', fileInputRef.current.files[0]);
        }

        setLoading(true);
        try {
            const response = await authAPIs().put(endpoints['updateuser'](user.id), formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            setIsEditing(false);
            setShowCameraIcon(false);
        } catch (error) {
            if (error.response) {
                console.log('Lỗi cập nhật chi tiết:', error.response.data);
            }
            console.log('Lỗi cập nhật: ' + error.message);
        } finally {
            setLoading(false);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setUpdatedUser((prevUser) => ({ ...prevUser, [name]: value })); //Cập nhật đúng trường dựa trên name
    };

    const handleFileChange = (e) => {
        if (e.target.files && e.target.files.length > 0) {
            const file = e.target.files[0];
            // Cập nhật avatar hiển thị
            setUpdatedUser((prevUser) => ({ ...prevUser, avatar: URL.createObjectURL(file) }));
        }
    };

    const handleChangePasswordClick = () => {
        setShowPasswordFields(true);
        setIsEditing(false);
    };

    const handleChangePassword = async () => {
        if (updatedUser.newPassword !== updatedUser.confirmNewPassword) {
            setShowAlert(true);
            return;
        }

        // Xác thực lại người dùng với mật khẩu cũ
        await reauthenticate(updatedUser.oldPassword);

        if (!otpSent) {
            // Gửi OTP lần đầu khi OTP chưa được gửi
            if (!validatePasswords()) return;

            const otpCode = generateOTP();
            setGeneratedOtp(otpCode);
            await sendOtpSMS(user.email, otpCode);
            setOtpSent(true);
            setOtpExpiry(new Date().getTime() + OTP_EXPIRY_DURATION); // Đặt thời gian hết hạn OTP
            setCountdown(OTP_EXPIRY_DURATION / 1000); // Đặt thời gian đếm ngược
            setErr('OTP đã được gửi đến mail của bạn.');
            return; // Dừng lại ở đây và đợi người dùng nhập OTP
        }

        // Kiểm tra OTP khi đã gửi mã OTP
        if (new Date().getTime() > otpExpiry) {
            setErr('Mã OTP đã hết hạn. Vui lòng yêu cầu mã OTP mới.');
            setCanResendOtp(true);
            return;
        }

        if (otp !== generatedOtp?.toString()) {
            setErr('Mã OTP không hợp lệ!');
            return;
        }

        // Nếu OTP hợp lệ, tiếp tục đăng ký
        setLoading(true);
        try {
            // Cập nhật mật khẩu trên Firebase
            await changePasswordOnFirebase(updatedUser.newPassword);

            const response = await authAPIs().put(endpoints['changepassword'], {
                email: updatedUser.email,
                oldPassword: updatedUser.oldPassword,
                newPassword: updatedUser.newPassword,
            });
            console.log(response.data);
            setShowPasswordFields(false);
        } catch (error) {
            if (error.response) {
                console.warn(error.response.data);
            } else {
                console.warn('Error changing password: ' + error.message);
            }
        } finally {
            setLoading(false);
        }
    };

    const validatePasswords = () => {
        if (updatedUser.newPassword.length <= 6) {
            setErr('Mật khẩu ít nhất phải có 6 ký tự!');
            return false;
        }
        if (
            updatedUser.newPassword &&
            updatedUser.confirmNewPassword &&
            updatedUser.newPassword !== updatedUser.confirmNewPassword
        ) {
            setErr('Mật khẩu KHÔNG khớp!');
            return false;
        }
        setErr('');
        return true;
    };

    // Hàm gửi OTP qua backend API thay vì gọi trực tiếp Twilio
    const sendOtpSMS = async (toEmail, otp) => {
        try {
            const response = await APIs.post(
                endpoints['sendemail'],
                {
                    email: toEmail,
                    message: `Your OTP code is ${otp}`,
                },
                {
                    headers: {
                        'Content-Type': 'application/json',
                    },
                },
            );

            if (response.status === 200) {
                console.log('OTP SMS sent successfully');
            } else {
                throw new Error('Failed to send OTP SMS.');
            }
        } catch (error) {
            console.error('Error sending OTP SMS:', error);
        }
    };

    // Xử lý gửi lại OTP
    const resendOtp = async () => {
        if (new Date().getTime() < otpExpiry) {
            setErr('Mã OTP còn hạn để dùng trước khi gửi mà mới.');
            return;
        }

        const otpCode = generateOTP();
        setGeneratedOtp(otpCode);
        await sendOtpSMS(user.email, otpCode);
        setOtpExpiry(new Date().getTime() + OTP_EXPIRY_DURATION); // Đặt lại thời gian hết hạn
        setCountdown(OTP_EXPIRY_DURATION / 1000); // Đặt lại thời gian đếm ngược
        setCanResendOtp(false); // Ngăn chặn gửi lại OTP cho đến khi OTP mới hết hạn
        setErr('OTP mới đã được gửi.');
    };

    useEffect(() => {
        if (otpSent) {
            const timer = setInterval(() => {
                setCountdown((prevCountdown) => {
                    if (prevCountdown <= 1) {
                        clearInterval(timer);
                        setCanResendOtp(true); // Cho phép gửi lại OTP
                        return 0;
                    }
                    return prevCountdown - 1;
                });
            }, 1000);

            return () => clearInterval(timer);
        }
    }, [otpSent]);

    useEffect(() => {
        if (otpSent && new Date().getTime() > otpExpiry) {
            setErr('Mã OTP đã hết hạn. Vui lòng yêu cầu mã OTP mới.');
            setCanResendOtp(true);
        }
    }, [otpSent, otpExpiry]);

    return (
        <div className={cx('wrapper')}>
            <div className={cx('container')}>
                <div data-aos="zoom-in-up" data-aos-duration="2000" className={cx('profiles')}>
                    <video ref={videoRef} muted loop id="myVideo" className={cx('myVideo')}>
                        <source src={video} type="video/mp4" />
                    </video>
                    <FontAwesomeIcon
                        className={cx('play-pause')}
                        icon={isPlaying ? faPause : faPlay}
                        onClick={handlePlayPause}
                    />
                    <div className={cx('profile')}>
                        <div className={cx('inner-image')}>
                            <img
                                className={cx('img-profile')}
                                src={updatedUser.avatar || image}
                                alt={updatedUser.fullName || ''}
                            />
                            {showCameraIcon && isEditing && (
                                <div className={cx('camera')}>
                                    <FontAwesomeIcon
                                        icon={faCamera}
                                        className={cx('camera-icon')}
                                        onClick={() => fileInputRef.current.click()} // Mở trình chọn tệp khi nhấn vào icon camera
                                    />
                                </div>
                            )}
                            <input
                                type="file"
                                ref={fileInputRef}
                                style={{ display: 'none' }}
                                onChange={handleFileChange}
                            />
                        </div>
                        <span className={cx('name-profile')}>{user ? user.fullName : ''}</span>
                    </div>
                </div>
                <Row className={cx('info-client')}>
                    <Col data-aos="fade-right" data-aos-duration="2000" className={cx('gutters')} xs={12} md={6} lg={6}>
                        <div
                            className={classNames(
                                cx('d-flex'),
                                cx('align-items-center'),
                                cx('justify-content-between'),
                                cx('header-info-clinet'),
                            )}
                        >
                            {(isEditing || showPasswordFields) && (
                                <FontAwesomeIcon className={cx('back')} onClick={handleBackClick} icon={faAngleLeft} />
                            )}
                            <span className={cx('name')}>Thông tin khách hàng</span>
                            {!isEditing && !showPasswordFields ? (
                                <FontAwesomeIcon className={cx('edit')} icon={faUserPen} onClick={handleEditClick} />
                            ) : (
                                <>
                                    {isEditing && !showPasswordFields ? (
                                        <FontAwesomeIcon
                                            className={cx('password-change')}
                                            icon={faKey}
                                            onClick={handleChangePasswordClick} // Đổi mật khẩu khi nhấn vào icon
                                        />
                                    ) : null}
                                    <Button
                                        className={cx('btn-submit')}
                                        onClick={!showPasswordFields ? handleSaveClick : handleChangePassword}
                                    >
                                        {loading ? <MySpinner /> : 'Lưu thông tin'}
                                    </Button>
                                </>
                            )}
                        </div>
                        {!showPasswordFields ? (
                            <Form className={cx('info-customer')}>
                                <div className={classNames(cx('d-flex'), cx('justify-content-between'))}>
                                    <Form.Group className="mb-4">
                                        <Form.Label className={cx('title')}>Họ</Form.Label>
                                        <div className={cx('input')}>
                                            <Form.Control
                                                className={cx('form-input')}
                                                type="text"
                                                placeholder="Họ..."
                                                name="firstName"
                                                value={updatedUser.firstName || ''}
                                                onChange={isEditing ? handleInputChange : null}
                                                // onChange={(e) => setUser({ ...user, fullName: e.target.value })}
                                                readOnly={!isEditing}
                                            />
                                        </div>
                                    </Form.Group>
                                    <Form.Group className="mb-4">
                                        <Form.Label className={cx('title')}>Tên</Form.Label>
                                        <div className={cx('input')}>
                                            <Form.Control
                                                className={cx('form-input')}
                                                type="text"
                                                placeholder="Tên lót và Tên..."
                                                name="lastName"
                                                value={updatedUser.lastName || ''}
                                                // onChange={(e) => setUser({ ...user, fullName: e.target.value })}
                                                onChange={isEditing ? handleInputChange : null}
                                                readOnly={!isEditing}
                                            />
                                        </div>
                                    </Form.Group>
                                </div>
                                <Form.Group className="mb-4">
                                    <Form.Label className={cx('title')}>Email</Form.Label>
                                    <div className={cx('input')}>
                                        <Form.Control
                                            className={cx('form-input')}
                                            type="text"
                                            placeholder="Email..."
                                            name="email"
                                            value={updatedUser.email || ''}
                                            // onChange={(e) => setUser({ ...user, fullName: e.target.value })}
                                            onChange={isEditing ? handleInputChange : null}
                                            readOnly={!isEditing}
                                        />
                                    </div>
                                </Form.Group>
                                <Form.Group className="mb-4">
                                    <Form.Label className={cx('title')}>Số điện thoại</Form.Label>
                                    <div className={cx('input')}>
                                        <Form.Control
                                            className={cx('form-input')}
                                            type="number"
                                            placeholder="Số điện thoại..."
                                            name="phone"
                                            value={updatedUser.phone || ''}
                                            // onChange={(e) => setUser({ ...user, fullName: e.target.value })}
                                            onChange={isEditing ? handleInputChange : null}
                                            readOnly={!isEditing}
                                        />
                                    </div>
                                </Form.Group>
                            </Form>
                        ) : (
                            <Form className={cx('info-customer')}>
                                {/* Change password */}
                                <Form.Group className="mb-4">
                                    <Form.Label className={cx('title')}>Mật khẩu cũ</Form.Label>
                                    <div className={cx('input')}>
                                        <Form.Control
                                            className={cx('form-input')}
                                            type={!visible ? 'password' : 'text'}
                                            placeholder="Mật khẩu cũ..."
                                            name="oldPassword"
                                            value={updatedUser.oldPassword || ''}
                                            onChange={handleInputChange}
                                        />
                                        <div onClick={() => setVisible(!visible)}>
                                            {visible && <FontAwesomeIcon className={cx('icon')} icon={faEye} />}
                                            {!visible && (
                                                <FontAwesomeIcon className={classNames(cx('icon'))} icon={faEyeSlash} />
                                            )}
                                        </div>
                                    </div>
                                </Form.Group>
                                <Form.Group className="mb-4">
                                    <Form.Label className={cx('title')}>Mật khẩu mới</Form.Label>
                                    <div className={cx('input')}>
                                        <Form.Control
                                            className={cx('form-input')}
                                            type={!visibleNew ? 'password' : 'text'}
                                            placeholder="Mật khẩu mới..."
                                            name="newPassword"
                                            value={updatedUser.newPassword || ''}
                                            onChange={handleInputChange}
                                        />
                                        <div onClick={() => setVisibleNew(!visibleNew)}>
                                            {visibleNew && <FontAwesomeIcon className={cx('icon')} icon={faEye} />}
                                            {!visibleNew && (
                                                <FontAwesomeIcon className={classNames(cx('icon'))} icon={faEyeSlash} />
                                            )}
                                        </div>
                                    </div>
                                </Form.Group>
                                <Form.Group className="mb-4">
                                    <Form.Label className={cx('title')}>Xác nhận mật khẩu mới</Form.Label>
                                    <div className={cx('input')}>
                                        <Form.Control
                                            className={cx('form-input')}
                                            type={!visibleNewConfirm ? 'password' : 'text'}
                                            placeholder="Xác nhận mật khẩu mới..."
                                            name="confirmNewPassword"
                                            value={updatedUser.confirmNewPassword || ''}
                                            onChange={handleInputChange}
                                        />
                                        <div onClick={() => setVisibleNewConfirm(!visibleNewConfirm)}>
                                            {visibleNewConfirm && (
                                                <FontAwesomeIcon className={cx('icon')} icon={faEye} />
                                            )}
                                            {!visibleNewConfirm && (
                                                <FontAwesomeIcon className={classNames(cx('icon'))} icon={faEyeSlash} />
                                            )}
                                        </div>
                                    </div>
                                </Form.Group>
                                <MyAlert
                                    text="Mật khẩu mới không khớp"
                                    show={showAlert}
                                    onClose={() => setShowAlert(false)}
                                />
                                {otpSent && (
                                    <Form.Group className="mb-3">
                                        <Form.Label className={cx('title')}>
                                            Nhập mã OTP đã gửi đến Email của bạn
                                        </Form.Label>
                                        <div className={cx('input')}>
                                            <Form.Control
                                                className={cx('form-input')}
                                                type="text"
                                                placeholder="Mã OTP..."
                                                onChange={(e) => setOtp(e.target.value)}
                                                required
                                            />
                                        </div>
                                        <div className={cx('countdown')}>
                                            {countdown > 0 ? (
                                                <span className={cx('otp-time')}>
                                                    Thời gian còn lại: {Math.floor(countdown / 60)}:
                                                    {('0' + (countdown % 60)).slice(-2)}
                                                </span>
                                            ) : (
                                                <span className={cx('otp')}>
                                                    Mã OTP đã hết hạn. Vui lòng yêu cầu mã mới.
                                                </span>
                                            )}
                                        </div>
                                        {canResendOtp && (
                                            <Button className={cx('resend-otp')} onClick={resendOtp}>
                                                Gửi lại mã OTP
                                            </Button>
                                        )}
                                    </Form.Group>
                                )}
                            </Form>
                        )}
                    </Col>
                    <Col data-aos="fade-left" data-aos-duration="2000" className={cx('gutters')} xs={12} md={6} lg={6}>
                        <span className={cx('name')}>Thông tin đơn hàng</span>
                        <div className={cx('inner')}>
                            <div className={cx('invoice-client')}>
                                {invoiceDetails.map((invoiceDetail) => (
                                    <div key={invoiceDetail.id} className={cx('invoice-menu')}>
                                        <div className={classNames(cx('invoice-equipment'), cx('invoice-item'))}>
                                            Tên thiết bị:
                                            <span>{invoiceDetail.equipmentId.name}</span>
                                        </div>
                                        <div className={classNames(cx('invoice-amount'), cx('invoice-item'))}>
                                            Số lượng:
                                            <span>{invoiceDetail.quantity}</span>
                                        </div>
                                        <div className={classNames(cx('invoice-price'), cx('invoice-item'))}>
                                            Giá thiết bị:
                                            <span>{formatPrice(invoiceDetail.unitPrice)}</span>
                                        </div>
                                        <div className={classNames(cx('created-date'), cx('invoice-item'))}>
                                            Ngày mua:
                                            <span>
                                                {new Date(invoiceDetail.invoiceId.createdDate).toLocaleDateString()}
                                            </span>
                                        </div>
                                        <div className={classNames(cx('total-price'), cx('invoice-item'))}>
                                            Tổng đơn hàng:
                                            <span>{formatPrice(invoiceDetail.invoiceId.totalAmount)}</span>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        </div>
                    </Col>
                </Row>
            </div>
        </div>
    );
}

export default Profile;
