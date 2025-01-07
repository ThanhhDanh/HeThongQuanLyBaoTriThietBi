import classNames from 'classnames/bind';
//Hiểu ứng khi cuộn trang
import Aos from 'aos';
import 'aos/dist/aos.css';

import styles from './Service.module.scss';
import { Wrapper } from '~/common/Menu';
import { Col, Row } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faStar } from '@fortawesome/free-regular-svg-icons';
import image from '~/assets/images/dichvu1.png';
import { useEffect } from 'react';
import ServiceItem from './ServiceItem';

const cx = classNames.bind(styles);

function Services() {
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
            <div className={cx('inner')}>
                <ServiceItem />
            </div>
        </Wrapper>
    );
}

export default Services;
