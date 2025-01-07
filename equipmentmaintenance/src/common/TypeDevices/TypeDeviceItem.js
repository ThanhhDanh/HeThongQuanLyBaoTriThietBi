import classNames from 'classnames/bind';
import { Col, Row } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCircleChevronRight, faWarehouse } from '@fortawesome/free-solid-svg-icons';
import { useLocation } from 'react-router-dom';
import { useEffect, useState } from 'react';

//Hiểu ứng khi cuộn trang
import Aos from 'aos';
import 'aos/dist/aos.css';

import styles from './TypeDevice.module.scss';
import Button from '../Button';
import image from '~/assets/images/typedevice2.jpg';
import APIs, { endpoints } from '~/configs/APIs';
import MySpinner from '../Spinner';

const cx = classNames.bind(styles);

function TypeDeviceItem() {
    const [imageUrl, setImageUrl] = useState([]);
    const [loading, setLoading] = useState(false);
    const location = useLocation();
    const { tool, device } = location.state || {};

    const equipmentList = tool || device || [];

    const loadEquipmentImage = async () => {
        setLoading(true);
        try {
            let res = await APIs.get(endpoints['equipmentimages']);
            const updatedEquipmentList = equipmentList.map((equipment) => {
                const equipmentImage = res.data.find((img) => img.equipmentId.id === equipment.id);
                if (equipmentImage) {
                    return { ...equipment, imageUrl: equipmentImage.image };
                }
                return equipment;
            });
            setImageUrl(updatedEquipmentList);
        } catch (e) {
            console.error('Lỗi tải ảnh: ' + e.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadEquipmentImage();
        Aos.init();
    }, []);

    return (
        <>
            {loading && <MySpinner />}
            {imageUrl.map((equipment) => (
                <Row data-aos="flip-down" key={equipment.id} className={cx('device-wrapper')}>
                    <Col xs={12} md={3} lg={3}>
                        <img
                            className={cx('device-item-image')}
                            src={equipment.imageUrl ? equipment.imageUrl : image}
                            alt={equipment.name}
                        />
                    </Col>
                    <Col xs={12} md={9} lg={9} className={cx('devices')}>
                        <div className={cx('device-item')}>
                            <div className={cx('device-info')}>
                                <p className={cx('device-title')}>{equipment.name}</p>
                                <p className={cx('device-content')}>{equipment.description}</p>
                                <div className={cx('device-manufacturer')}>
                                    <FontAwesomeIcon className={cx('device-icon')} icon={faWarehouse} />
                                    Nhà sản xuất: <span>{equipment.manufacturer}</span>
                                </div>
                                <div className={cx('see-add')}>
                                    <FontAwesomeIcon className={cx('icon')} icon={faCircleChevronRight} />
                                    <Button to={`/detail/${equipment.name}`} className={cx('see-detail')}>
                                        Xem chi tiết
                                    </Button>
                                </div>
                            </div>
                        </div>
                    </Col>
                </Row>
            ))}
        </>
    );
}

export default TypeDeviceItem;
