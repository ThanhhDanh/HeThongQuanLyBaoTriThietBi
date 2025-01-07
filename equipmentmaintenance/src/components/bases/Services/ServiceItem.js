import { useEffect, useState } from 'react';
import { Col } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import classNames from 'classnames/bind';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCircleChevronRight } from '@fortawesome/free-solid-svg-icons';
//Hiểu ứng khi cuộn trang
import Aos from 'aos';
import 'aos/dist/aos.css';

import styles from './Service.module.scss';
import APIs, { endpoints } from '~/configs/APIs';
import MySpinner from '~/common/Spinner/Spinner';
import Button from '~/common/Button';
import image from '~/assets/images/dichvu2.jpg';

const cx = classNames.bind(styles);

function ServiceItem() {
    const [service, setService] = useState([]);
    const [loading, setLoading] = useState(false);
    const nav = useNavigate();

    const loadingServices = async () => {
        setLoading(true);
        try {
            let res = await APIs.get(endpoints['services']);
            let filter = res.data.filter((s) => s.serviceType === 'Bảo trì');
            setService(filter);
        } catch (err) {
            console.warn('Lỗi service: ' + err.message);
        } finally {
            setLoading(false);
        }
    };

    Aos.init();

    useEffect(() => {
        loadingServices();
    }, []);

    return (
        <>
            {loading && <MySpinner />}
            {service.map((service) => (
                <Col key={service.id} data-aos="fade-up" xs={12} md={4} lg={4} className={cx('gutters')}>
                    <div className={cx('inner')}>
                        <div className={cx('services')}>
                            <Link>
                                <h4 className={cx('title')}>{service.description}</h4>
                            </Link>
                            <div className={cx('content')}>{service.descriptionDetail}</div>
                            <div className={cx('see-add')}>
                                <FontAwesomeIcon className={cx('icon')} icon={faCircleChevronRight} />
                                <Button
                                    onClick={() => nav('/service/' + service.id, { state: { service } })}
                                    className={cx('see-detail')}
                                >
                                    Xem chi tiết
                                </Button>
                            </div>
                        </div>
                        <Link className={cx('img-service')}>
                            <img className={cx('image')} src={image} alt={service.description} />
                        </Link>
                    </div>
                </Col>
            ))}
        </>
    );
}

export default ServiceItem;
