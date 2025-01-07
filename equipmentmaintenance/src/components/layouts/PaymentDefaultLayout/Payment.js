import classNames from 'classnames/bind';
//Hiểu ứng khi cuộn trang
import Aos from 'aos';
import 'aos/dist/aos.css';

import styles from './Payment.module.scss';
import { Col, Form, Modal, Row } from 'react-bootstrap';
import Button from '~/common/Button';
import MySpinner from '~/common/Spinner';
import { useContext, useEffect, useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCircleCheck, faCreditCard } from '@fortawesome/free-regular-svg-icons';
import { MyUserContext } from '~/App';
import { useLocation } from 'react-router-dom';
import APIs, { endpoints } from '~/configs/APIs';
import { formatPrice } from '~/Utils/Utils';
import MyAlert from '~/Utils/Alert';
import { config } from '~/routes/routes';

const cx = classNames.bind(styles);

function Payment() {
    // const [loading, setLoading] = useState(true);
    const user = useContext(MyUserContext);
    const [paymentMethod, setPaymentMethod] = useState('');
    const [showSubPayment, setShowSubPayment] = useState(false);
    const [subPaymentMethod, setSubPaymentMethod] = useState('');
    const location = useLocation();
    const data = location.state?.data;

    // Khai báo state cho modal
    const [showModal, setShowModal] = useState(false);
    const [paymentStatus, setPaymentStatus] = useState('');
    const [paymentMessage, setPaymentMessage] = useState('');
    const [paymentData, setPaymentData] = useState(data);

    const [showAlert, setShowAlert] = useState(false);

    useEffect(() => {
        Aos.init();
    }, []);

    const handlePaymentChange = (e) => {
        setPaymentMethod(e.target.value);
        setShowSubPayment(e.target.value === 'Chuyển khoản');
    };

    const handleSubPaymentChange = (e) => {
        setSubPaymentMethod(e.target.value);
    };

    const handlePayment = async () => {
        if (paymentMethod === 'Chuyển khoản') {
            if (subPaymentMethod === 'Momo') {
                try {
                    const payload = {
                        equipmentId: data?.id.toString(),
                        price: data?.price.toString(),
                        userId: user?.id.toString(),
                        quantity: 1,
                    };

                    console.log(payload);

                    const inforPay = await APIs.post(endpoints['momo'], payload, {
                        withCredentials: true,
                        crossdomain: true,
                        headers: {
                            'Content-Type': 'application/json',
                        },
                    });

                    const url = inforPay.data.payUrl;
                    if (url) {
                        window.location.href = url;
                    }
                } catch (error) {
                    console.error('Error fetching URL:', error.response ? error.response.data : error.message);
                }
                console.log('Đang xử lý thanh toán qua Momo');
            } else if (subPaymentMethod === 'VNPAY') {
                // Thực hiện xử lý thanh toán cho VNPAY
                console.log('Đang xử lý thanh toán qua VNPAY');
            } else {
                setShowAlert(true);
            }
        } else if (paymentMethod === 'Tiền mặt') {
            try {
                let res = await APIs.post(endpoints['cash'], {
                    data: {
                        equipmentId: data?.id.toString(),
                        price: data?.price.toString(),
                        userId: user?.id.toString(),
                        quantity: 1,
                    },
                });
                // Hiển thị thông báo thành công
                setPaymentData(null);
                setPaymentStatus('Thành công');
                setPaymentMessage('Bạn đã đặt hàng thành công!');
                setShowModal(true);
            } catch (error) {
                console.log('Lỗi thanh toán tiền mặt: ' + error.message);
            }
        } else {
            setShowAlert(true);
        }
    };

    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const status = queryParams.get('status');
        const message = queryParams.get('message');

        if (status === 'success') {
            setPaymentStatus(status);
            setPaymentMessage(message);
            setShowModal(true);
            setPaymentData(null);
        }
    }, [location]);

    return (
        <div className={cx('wrapper')}>
            <div className={cx('container')}>
                <div className={cx('header-payment')}>
                    <FontAwesomeIcon className={cx('icon-payment')} icon={faCreditCard} />
                    <h1 className={cx('title')}>Thanh toán</h1>
                    <span className={cx('note')}>
                        Vui lòng kiểm tra thông tin Khách hàng, thông tin Đơn hàng trước khi Đặt hàng.
                    </span>
                </div>
                <Row>
                    <Col xs={12} md={8} lg={8}>
                        <div className={cx('inner')}>
                            <span className={cx('name')}>Thông tin khách hàng</span>
                            <Form className={cx('info-customer')}>
                                <Form.Group className="mb-4">
                                    <Form.Label className={cx('title')}>Họ tên</Form.Label>
                                    <div className={cx('input')}>
                                        <Form.Control
                                            className={cx('form-input')}
                                            type="text"
                                            placeholder="Họ tên..."
                                            value={user ? user.fullName : 'Họ tên...'}
                                            // onChange={(e) => setUser({ ...user, fullName: e.target.value })}
                                            readOnly
                                        />
                                    </div>
                                </Form.Group>
                                <Form.Group className="mb-4">
                                    <Form.Label className={cx('title')}>Email</Form.Label>
                                    <div className={cx('input')}>
                                        <Form.Control
                                            className={cx('form-input')}
                                            type="text"
                                            placeholder="Email..."
                                            value={user ? user.email : 'Email...'}
                                            // onChange={(e) => setUser({ ...user, fullName: e.target.value })}
                                            readOnly
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
                                            value={user ? user.phone : 'Số điện thoại...'}
                                            // onChange={(e) => setUser({ ...user, fullName: e.target.value })}
                                            readOnly
                                        />
                                    </div>
                                </Form.Group>
                                <div className={cx('form-payment')}>
                                    <span>Hình thức thanh toán</span>
                                    <div className={cx('choose-payment')}>
                                        <input
                                            type="radio"
                                            value="Tiền mặt"
                                            checked={paymentMethod === 'Tiền mặt'}
                                            onChange={handlePaymentChange}
                                        />
                                        <label>Tiền mặt</label>
                                    </div>
                                    <div className={cx('choose-payment')}>
                                        <input
                                            type="radio"
                                            value="Chuyển khoản"
                                            checked={paymentMethod === 'Chuyển khoản'}
                                            onChange={handlePaymentChange}
                                        />
                                        <label>Chuyển khoản</label>
                                    </div>
                                    <MyAlert
                                        text="Vui lòng chọn hình thức thanh toán."
                                        show={showAlert}
                                        onClose={() => setShowAlert(false)}
                                    />
                                </div>

                                {/* Menu con cho Chuyển khoản */}
                                {showSubPayment && (
                                    <div className={cx('sub-payment', { 'fade-in': true, show: showSubPayment })}>
                                        <span>Chọn phương thức chuyển khoản</span>
                                        <div className={cx('choose-sub-payment')}>
                                            <input
                                                type="radio"
                                                name="sub-payment"
                                                value="Momo"
                                                checked={subPaymentMethod === 'Momo'}
                                                onChange={handleSubPaymentChange}
                                            />
                                            <label>Momo</label>
                                        </div>
                                        <div className={cx('choose-sub-payment')}>
                                            <input
                                                type="radio"
                                                name="sub-payment"
                                                value="VNPAY"
                                                checked={subPaymentMethod === 'VNPAY'}
                                                onChange={handleSubPaymentChange}
                                            />
                                            <label>VNPAY</label>
                                        </div>
                                    </div>
                                )}

                                <Form.Group className="my-4">
                                    <Button onClick={handlePayment} className={cx('button')} type="button">
                                        {/* {loading ? <MySpinner /> : 'Thanh toán'} */}Thanh toán
                                    </Button>
                                </Form.Group>
                            </Form>
                        </div>
                    </Col>
                    <Col xs={12} md={4} lg={4}>
                        <div className={cx('inner')}>
                            <span className={cx('name')}>Đơn hàng</span>
                            <div className={cx('detail-invoice')}>
                                <div className={cx('invoice')}>
                                    <span className={cx('key')}>Tên thiết bị: </span>
                                    <span className={cx('value')}>{data?.name}</span>
                                </div>
                                <div className={cx('invoice')}>
                                    <span className={cx('key')}>Mã thiết bị:</span>
                                    <span className={cx('value')}>{data?.code}</span>
                                </div>
                                <div className={cx('invoice')}>
                                    <span className={cx('key')}>Loại thiết bị:</span>
                                    <span className={cx('value')}>{data?.type}</span>
                                </div>
                                <div className={cx('invoice')}>
                                    <span className={cx('key')}>Nhà sản xuất:</span>
                                    <span className={cx('value')}>{data?.manufacturer}</span>
                                </div>
                                <div className={cx('invoice')}>
                                    <span className={cx('key')}>Giá thiết bị:</span>
                                    <span className={cx('value')}>{formatPrice(data ? data?.price : 0)}</span>
                                </div>
                            </div>
                        </div>
                    </Col>
                </Row>
            </div>

            <Modal
                className={cx('modal')}
                show={showModal}
                onHide={() => setShowModal(false)}
                centered
                animation={true}
            >
                <Modal.Header closeButton className={cx('notification-header')}>
                    <Modal.Title className={cx('notification-header-payment')}>
                        {paymentStatus && 'Thông báo thanh toán'}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body className={cx('notification-payment')}>
                    <FontAwesomeIcon className={cx('notification-icon')} icon={faCircleCheck} />
                    <p>{paymentMessage}</p>
                </Modal.Body>
                <Modal.Footer>
                    <Button to={config.routes.profile} onClick={() => setShowModal(false)} className={cx('button')}>
                        Đóng
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}

export default Payment;
