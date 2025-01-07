import classNames from 'classnames/bind';
import { Col, Row } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMoneyBillWave } from '@fortawesome/free-solid-svg-icons';
import { faCheckSquare, faStar } from '@fortawesome/free-regular-svg-icons';
import { useLocation } from 'react-router-dom';
import { useEffect, useState } from 'react';

//Hiểu ứng khi cuộn trang
import Aos from 'aos';
import 'aos/dist/aos.css';

import styles from './Service.module.scss';
import APIs, { endpoints } from '~/configs/APIs';
import { formatPrice } from '~/Utils/Utils';
import MySpinner from '~/common/Spinner';

const cx = classNames.bind(styles);

function ServiceItem() {
    const [loading, setLoading] = useState(false);
    const [imageUrl, setImageUrl] = useState('');
    const location = useLocation();
    const data = location.state?.service;

    const loadingEquipmentImage = async () => {
        setLoading(true);
        try {
            let res = await APIs.get(endpoints['equipmentimages']);

            let filterImage = res.data.find((img) => img.equipmentId.id === data.equipmentId.id);
            setImageUrl(filterImage.image);
        } catch (error) {
            console.warn('Lỗi lấy ảnh: ' + error.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadingEquipmentImage();
    }, [data]);

    useEffect(() => {
        Aos.init();
    }, []);

    return (
        <>
            <Row data-aos="zoom-in" data-aos-duration="2000" className={cx('align-center')}>
                <Col xs={12} md={4} lg={4}>
                    <div className={cx('service-type')}>
                        Loại dịch vụ: <span>{data.serviceType}</span>
                        <FontAwesomeIcon className={cx('icon-star')} icon={faStar} />
                        <FontAwesomeIcon className={cx('icon-star')} icon={faStar} />
                        <FontAwesomeIcon className={cx('icon-star')} icon={faStar} />
                        <FontAwesomeIcon className={cx('icon-star')} icon={faStar} />
                        <FontAwesomeIcon className={cx('icon-star')} icon={faStar} />
                    </div>
                </Col>
                <Col xs={12} md={8} lg={8}>
                    <div className={cx('line')}></div>
                </Col>
            </Row>
            {loading && <MySpinner />}
            <Row className={classNames(cx('align-center'), cx('container-service'))}>
                <Col data-aos="flip-left" data-aos-duration="2000" xs={12} md={4} lg={4}>
                    <div className={cx('image-container')}>
                        <img className={cx('image')} src={imageUrl} alt={data.equipmentId.name} />
                    </div>
                </Col>
                <Col data-aos="flip-right" data-aos-duration="2000" xs={12} md={8} lg={8}>
                    <div className={cx('info-service')}>
                        <div className={classNames(cx('service-item'), cx('service-name'))}>
                            <FontAwesomeIcon className={cx('service-icon')} icon={faCheckSquare} />
                            <span>{data.equipmentId.name}</span>
                        </div>
                        <div className={classNames(cx('service-item'), cx('service-description'))}>
                            <FontAwesomeIcon className={cx('service-icon')} icon={faCheckSquare} />
                            <span>Thông tin: {data.description}</span>
                        </div>
                        <div className={classNames(cx('service-item-icon'), cx('service-description-detail'))}>
                            <FontAwesomeIcon className={cx('service-icon')} icon={faCheckSquare} />
                            <span>Thông tin chi tiết: {data.descriptionDetail}</span>
                        </div>
                        <div className={classNames(cx('service-item'), cx('service-cost'))}>
                            <FontAwesomeIcon className={cx('service-icon')} icon={faMoneyBillWave} />
                            Giá dịch vụ: <span>{formatPrice(data.cost)}</span>
                        </div>
                    </div>
                </Col>
            </Row>
        </>
    );
}

export default ServiceItem;
