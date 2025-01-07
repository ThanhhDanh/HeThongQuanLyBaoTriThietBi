import classNames from 'classnames/bind';
import { useEffect, useRef, useState } from 'react';
import { Alert, Button, Form, Modal } from 'react-bootstrap';
import { createUserWithEmailAndPassword } from 'firebase/auth';
import { doc, setDoc } from 'firebase/firestore';
import { Wrapper } from '~/common/Menu';
import APIs, { endpoints } from '~/configs/APIs';
import styles from './Register.module.scss';
import MySpinner from '~/common/Spinner/Spinner';
import { auth, db } from '~/Firebase/Firebase';
import upload from '~/Firebase/upload';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-regular-svg-icons';

const cx = classNames.bind(styles);

const Register = ({ show, handleClose }) => {
    const [visible, setVisible] = useState(false);
    const [visibleConfirm, setVisibleConfirm] = useState(false);
    const [loading, setLoading] = useState(false);
    const [isValidEmail, setValidEmail] = useState(true);
    const [user, setUser] = useState({});
    const [err, setErr] = useState('');
    const [otp, setOtp] = useState('');
    const [generatedOtp, setGeneratedOtp] = useState(null);
    const [otpSent, setOtpSent] = useState(false);
    const [otpExpiry, setOtpExpiry] = useState(null); // Thời gian hết hạn OTP
    const [canResendOtp, setCanResendOtp] = useState(false); // Có thể gửi lại OTP không
    const [countdown, setCountdown] = useState(0); // Thời gian đếm ngược
    const avatar = useRef();

    const OTP_EXPIRY_DURATION = 1 * 60 * 1000; // 5 phút

    const generateOTP = () => Math.floor(100000 + Math.random() * 900000); // 6-digit OTP

    const register = async (e) => {
        e.preventDefault();

        if (!isValidEmail) {
            setErr('Email không hợp lệ!');
            return;
        }

        if (!otpSent) {
            // Gửi OTP lần đầu khi OTP chưa được gửi
            if (!validatePasswords()) return;

            if (!avatar.current || !avatar.current.files[0]) {
                setErr('Ảnh đại diện là bắt buộc!');
                return;
            }

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
            let form = new FormData();
            for (let f in user)
                if (f !== 'confirm') {
                    form.append(f, user[f]);
                }

            form.append('avatar', avatar.current.files[0]);

            let res = await APIs.post(endpoints['register'], form, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });

            const userFire = await createUserWithEmailAndPassword(auth, user.email, user.password);
            const imgUrl = await upload(avatar.current.files[0]);

            await setDoc(doc(db, 'users', userFire.user.uid), {
                avatar: imgUrl,
                username: user.username,
                email: user.email,
                id: userFire.user.uid,
                blocked: [],
            });

            await setDoc(doc(db, 'userchats', userFire.user.uid), {
                chats: [],
            });

            handleClose();
            setUser('');
        } catch (err) {
            console.log('Lỗi đăng ký: ' + err.message);
        } finally {
            setLoading(false);
        }
    };

    const change = (e, field) => {
        setUser({ ...user, [field]: e.target.value });
    };

    const validatePasswords = () => {
        if (user.password.length <= 6) {
            setErr('Mật khẩu ít nhất phải có 6 ký tự!');
            return false;
        }
        if (user.password && user.confirm && user.password !== user.confirm) {
            setErr('Mật khẩu KHÔNG khớp!');
            return false;
        }
        setErr('');
        return true;
    };

    //Kiểm tra email
    const verifyEmail = (email) => {
        let regex = new RegExp(
            // eslint-disable-next-line no-useless-escape
            /([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\"\(\[\]!#-[^-~ \t]|(\\[\t -~]))+@([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\[[\t -Z^-~]*])/,
        );
        return regex.test(email);
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
        <Modal show={show} onHide={handleClose} centered animation={true}>
            <Wrapper className={cx('wrapper')}>
                <Modal.Header closeButton className={cx('title-login')}>
                    <Modal.Title>ĐĂNG KÝ NGƯỜI DÙNG</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {err && <Alert variant="danger">{err}</Alert>}
                    <Form method="post" onSubmit={register} className={cx('form')}>
                        {!otpSent ? (
                            <>
                                <Form.Group className="mb-3">
                                    <Form.Label className={cx('title')}>Tên</Form.Label>
                                    <div className={cx('input')}>
                                        <Form.Control
                                            className={cx('form-input')}
                                            type="text"
                                            placeholder="Tên..."
                                            value={user['firstName']}
                                            onChange={(e) => change(e, 'firstName')}
                                            required
                                        />
                                    </div>
                                </Form.Group>
                                <Form.Group className="mb-3">
                                    <Form.Label className={cx('title')}>Họ và tên lót</Form.Label>
                                    <div className={cx('input')}>
                                        <Form.Control
                                            className={cx('form-input')}
                                            type="text"
                                            placeholder="Họ và tên lót..."
                                            value={user['lastName']}
                                            onChange={(e) => change(e, 'lastName')}
                                            required
                                        />
                                    </div>
                                </Form.Group>
                                <Form.Group className="mb-3">
                                    <Form.Label className={cx('title')}>Email</Form.Label>
                                    <div className={cx('input')}>
                                        <Form.Control
                                            className={cx('form-input', { 'is-invalid': !isValidEmail })}
                                            type="text"
                                            placeholder="Email..."
                                            value={user['email']}
                                            onChange={(e) => {
                                                change(e, 'email');
                                                const isValid = verifyEmail(e.target.value);
                                                setValidEmail(isValid);
                                            }}
                                            required
                                        />
                                        <Form.Control.Feedback className={cx('validate')} type="invalid">
                                            Email không hợp lệ
                                        </Form.Control.Feedback>
                                    </div>
                                </Form.Group>
                                <Form.Group className="mb-3">
                                    <Form.Label className={cx('title')}>Số điện thoại</Form.Label>
                                    <div className={cx('input')}>
                                        <Form.Control
                                            className={cx('form-input')}
                                            type="number"
                                            placeholder="Số điện thoại..."
                                            value={user['phone']}
                                            onChange={(e) => change(e, 'phone')}
                                            required
                                        />
                                    </div>
                                </Form.Group>
                                <Form.Group className="mb-3">
                                    <Form.Label className={cx('title')}>Tên đăng nhập</Form.Label>
                                    <div className={cx('input')}>
                                        <Form.Control
                                            className={cx('form-input')}
                                            type="text"
                                            placeholder="Tên đăng nhập..."
                                            value={user['username']}
                                            onChange={(e) => change(e, 'username')}
                                            required
                                        />
                                    </div>
                                </Form.Group>
                                <Form.Group className="mb-3">
                                    <Form.Label className={cx('title')}>Mật khẩu</Form.Label>
                                    <div className={cx('input')}>
                                        <Form.Control
                                            className={cx('form-input')}
                                            type={!visible ? 'password' : 'text'}
                                            placeholder="Mật khẩu..."
                                            value={user['password']}
                                            onChange={(e) => change(e, 'password')}
                                            required
                                        />
                                        <div onClick={() => setVisible(!visible)}>
                                            {visible && <FontAwesomeIcon className={cx('icon')} icon={faEye} />}
                                            {!visible && (
                                                <FontAwesomeIcon className={classNames(cx('icon'))} icon={faEyeSlash} />
                                            )}
                                        </div>
                                    </div>
                                </Form.Group>
                                <Form.Group className="mb-3">
                                    <Form.Label className={cx('title')}>Xác nhận mật khẩu</Form.Label>
                                    <div className={cx('input')}>
                                        <Form.Control
                                            className={cx('form-input')}
                                            type={!visibleConfirm ? 'password' : 'text'}
                                            placeholder="Xác nhận mật khẩu..."
                                            value={user['confirm']}
                                            onChange={(e) => change(e, 'confirm')}
                                            required
                                        />
                                        <div onClick={() => setVisibleConfirm(!visibleConfirm)}>
                                            {visibleConfirm && <FontAwesomeIcon className={cx('icon')} icon={faEye} />}
                                            {!visibleConfirm && (
                                                <FontAwesomeIcon className={classNames(cx('icon'))} icon={faEyeSlash} />
                                            )}
                                        </div>
                                    </div>
                                </Form.Group>
                                <Form.Group className="mb-3">
                                    <Form.Label className={cx('title')}>Ảnh đại diện</Form.Label>
                                    <div className={cx('input')}>
                                        <Form.Control
                                            className={cx('form-input')}
                                            accept=".png,.jpg"
                                            type="file"
                                            ref={avatar}
                                            required
                                        />
                                    </div>
                                </Form.Group>
                            </>
                        ) : (
                            <Form.Group className="mb-3">
                                <Form.Label className={cx('title')}>Nhập mã OTP đã gửi đến Email của bạn</Form.Label>
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
                                        <span className={cx('otp')}>Mã OTP đã hết hạn. Vui lòng yêu cầu mã mới.</span>
                                    )}
                                </div>
                                {canResendOtp && (
                                    <Button className={cx('resend-otp')} onClick={resendOtp}>
                                        Gửi lại mã OTP
                                    </Button>
                                )}
                            </Form.Group>
                        )}
                        <Form.Group className="mb-3">
                            <Button className={cx('button')} type="submit">
                                {loading ? <MySpinner /> : 'Đăng ký'}
                            </Button>
                        </Form.Group>
                    </Form>
                </Modal.Body>
            </Wrapper>
        </Modal>
    );
};

export default Register;
