import classNames from 'classnames/bind';
import { Col, Row } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faStar } from '@fortawesome/free-solid-svg-icons';
import { useEffect } from 'react';

//Hiểu ứng khi cuộn trang
import Aos from 'aos';
import 'aos/dist/aos.css';

import styles from './TypeDevice.module.scss';
import { Wrapper } from '../Menu';
import image from '~/assets/images/typedevice2.jpg';

const cx = classNames.bind(styles);

function TypeDeices({ children }) {
    useEffect(() => {
        Aos.init();
    }, []);

    return (
        <Wrapper className={cx('wrapper')}>
            <Row data-aos="fade-left" className={cx('inner-intro')}>
                <Col xs={12} md={4} lg={4}>
                    <div className={cx('image-devices')}>
                        <img className={cx('image-device')} src={image} alt="Thiết bị" />
                    </div>
                </Col>
                <Col xs={12} md={8} lg={8}>
                    <div className={cx('intro-device')}>
                        <ul className={cx('menu-benefit')}>
                            <li className={cx('item-benefit')}>
                                <FontAwesomeIcon className={cx('icon-benefit')} icon={faStar} />
                                Kiểu dáng hiện đại, bắt mắt
                            </li>
                            <li className={cx('item-benefit')}>
                                <FontAwesomeIcon className={cx('icon-benefit')} icon={faStar} />
                                Tiết kiệm điện năng
                            </li>
                            <li className={cx('item-benefit')}>
                                <FontAwesomeIcon className={cx('icon-benefit')} icon={faStar} />
                                Tiết kiệm thời gian
                            </li>
                        </ul>
                        <ul className={cx('menu-benefit')}>
                            <li className={cx('item-benefit')}>
                                <FontAwesomeIcon className={cx('icon-benefit')} icon={faStar} />
                                Linh hoạt, thuận tiện khi điều khiển từ xa
                            </li>
                            <li className={cx('item-benefit')}>
                                <FontAwesomeIcon className={cx('icon-benefit')} icon={faStar} />
                                Mang lại giá trị về sức khỏe
                            </li>
                            <li className={cx('item-benefit')}>
                                <FontAwesomeIcon className={cx('icon-benefit')} icon={faStar} />
                                Đa dạng tính năng cơ bản đến nâng cao
                            </li>
                        </ul>
                        <ul className={cx('menu-benefit')}>
                            <li className={cx('item-benefit')}>
                                <FontAwesomeIcon className={cx('icon-benefit')} icon={faStar} />
                                Cần thời gian tiếp cận và làm quen khi sử dụng
                            </li>
                            <li className={cx('item-benefit')}>
                                <FontAwesomeIcon className={cx('icon-benefit')} icon={faStar} />
                                Giá thành cao hơn so với đồ gia dụng thông thường
                            </li>
                        </ul>
                    </div>
                </Col>
            </Row>
            <div className={cx('inner')}>{children}</div>
        </Wrapper>
    );
}

export default TypeDeices;
