import { Col, Row } from 'react-bootstrap';
import classNames from 'classnames/bind';
import { useEffect, useState } from 'react';
//Hiểu ứng khi cuộn trang
import Aos from 'aos';
import 'aos/dist/aos.css';

import styles from './Service.module.scss';
import Button from '~/common/Button';
import imgae from '~/assets/images/certificate.jpg';
import APIs, { endpoints } from '~/configs/APIs';
import MySpinner from '~/common/Spinner/Spinner';
import image from '~/assets/images/introduce.jpg';

const cx = classNames.bind(styles);

function Services({ children }) {
    const [forum, setForum] = useState([]);
    const [loading, setLoading] = useState(false);

    const loadingForum = async () => {
        setLoading(true);
        try {
            let res = await APIs.get(endpoints['forums']);
            setForum(res.data.data);
        } catch (error) {
            console.log('Lỗi forum: ' + error.message);
        } finally {
            setLoading(false);
        }
    };

    Aos.init();
    useEffect(() => {
        loadingForum();
    }, []);

    return (
        <div className={cx('wrapper')}>
            <Row>{children}</Row>
            <Row className={cx('container')}>
                <Col data-aos="fade-right" xs={12} md={3} lg={3} className={classNames(cx('gutters'), cx('h-100'))}>
                    <div className={cx('inner-news')}>
                        <h3 className={cx('title-news')}>Tin tức nổi bật</h3>
                        <div className={cx('content-news')}>
                            Cập nhật các thông tin hữu ít về kiến thức kỹ thuật trong lĩnh vực điện công nghiệp và những
                            hoạt động nổi trội của đại lý của chúng tôi.
                        </div>
                        <div className={cx('see-add')}>
                            <Button className={cx('see-detail')}>Xem chi tiết</Button>
                        </div>
                    </div>
                </Col>
                <Col data-aos="zoom-in-up" xs={12} md={5} lg={5} className={cx('gutters')}>
                    <div className={cx('inner-certificate')}>
                        <img className={cx('certificate')} src={imgae} alt="Agent's Certificate" />
                        <div className={cx('content-certificate')}>Đại lý phân phối thiết bị tiêu dùng</div>
                    </div>
                </Col>
                <Col data-aos="fade-left" xs={12} md={4} lg={4} className={classNames(cx('gutters'), cx('h-100'))}>
                    <div className={cx('inner-forum')}>
                        <h3 className={cx('title-forum')}>Những lỗi hay gặp?</h3>
                        {loading && <MySpinner />}
                        {forum.map((f) => (
                            <div key={f.id} className={cx('forums')}>
                                <div className={cx('name-forum')}>{f.title}</div>
                                <div className={cx('content-forum')}>{f.content}</div>
                            </div>
                        ))}
                    </div>
                </Col>
            </Row>
            <Row className={cx('container')}>
                <Col data-aos="fade-up-right" xs={12} md={5} lg={5}>
                    <div className={cx('inner-intro')}>
                        <h2 className={cx('name-intro')}>
                            <span>Giới thiệu</span> đại lý
                        </h2>
                        <div className={cx('intro')}>
                            Đại lý là đối tác chính thức phân phối thiết bị điện và các thiết bị tiêu dụng hằng ngày của
                            các hãng như: Schneider, Mitsubishi, ABB, LS, Hangyoung, Idec, Panasonic... Là đơn vị quản
                            lý và bảo trì các thiết bị về nhu cầu của người dùng để đảm bảo chất lượng, an toàn đển tay
                            mọi nhà.
                        </div>
                    </div>
                </Col>
                <Col data-aos="fade-up-left" xs={12} md={7} lg={7}>
                    <div className={cx('inner')}>
                        <img src={image} alt="" className={cx('imgae-intro')} />
                    </div>
                </Col>
            </Row>
            <div data-aos="fade-up" data-aos-anchor-placement="center-bottom" className={cx('container-logo')}>
                <div className={cx('carousel')}>
                    <div className={cx('logos')}></div>
                    <div className={cx('mask')}></div>
                </div>
            </div>
        </div>
    );
}

export default Services;
