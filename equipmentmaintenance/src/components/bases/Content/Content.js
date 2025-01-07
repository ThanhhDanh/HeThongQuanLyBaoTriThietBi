import classNames from 'classnames/bind';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faListDots } from '@fortawesome/free-solid-svg-icons';
import { Col, Row } from 'react-bootstrap';
//Hiểu ứng khi cuộn trang
import Aos from 'aos';
import 'aos/dist/aos.css';

import styles from './Content.module.scss';
import { faLightbulb } from '@fortawesome/free-regular-svg-icons';

const cx = classNames.bind(styles);

function Content({ children }) {
    Aos.init();

    return (
        <div data-aos="fade-up" className={cx('wrapper')}>
            <Row>
                <div className={cx('intro')}>
                    <Col xs={12} md={3} lg={3}>
                        <div className={cx('intro-provide')}>
                            <FontAwesomeIcon className={cx('icon')} icon={faListDots} />
                            <span className={cx('label')}>SẢN PHẨM</span>
                        </div>
                    </Col>
                    <Col xs={12} md={9} lg={9}>
                        <span className={cx('content')}>
                            Nhà quản lý bảo trì vật tư thiết bị và công cụ chuyên nghiệp, hiện đại. Là nơi được các nhà
                            cung cấp tin cậy phân phối của các hãng Honda, SamSung, Bosch, Yamaha, Daikin, Canon, Dell,
                            Philips. Đơn vị sản xuất máy tủ điện, máy hút ẩm, máy lạnh, máy xay sinh tố...
                        </span>
                    </Col>
                </div>
            </Row>
            <Row className={cx('inner')}>{children}</Row>
            <Row>
                <div className={cx('intro')}>
                    <Col xs={12} md={3} lg={3}>
                        <div className={cx('intro-provide')}>
                            <FontAwesomeIcon className={cx('icon')} icon={faLightbulb} />
                            <span className={cx('label')}>GIẢI PHÁP</span>
                        </div>
                    </Col>
                    <Col xs={12} md={9} lg={9}>
                        <span className={cx('content')}>
                            Với đội ngũ kỹ thuật nhiều năm kinh nghiệm làm việc trong lĩnh vực điện công nghiệp, tự động
                            hoá. Đại lý cam kết đáp ứng chất lượng dịch vụ thiết kế, thi công lắp đặt tủ điện, sửa chữa
                            hệ thống điện nhà xưởng, nhà máy, toà nhà mang đến giải pháp tốt nhất cho khách hàng.
                        </span>
                    </Col>
                </div>
            </Row>
        </div>
    );
}

export default Content;
