import classNames from 'classnames/bind';
import { Link, useParams } from 'react-router-dom';
import { memo, useState } from 'react';
import { useEffect } from 'react';
import { Col, Row } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBars, faCircleChevronRight } from '@fortawesome/free-solid-svg-icons';
import Aos from 'aos';
import 'aos/dist/aos.css';

import { Wrapper } from '~/common/Menu';
import APIs, { endpoints } from '~/configs/APIs';
import styles from './DetailDevice.module.scss';
import MySpinner from '~/common/Spinner';
import DeviceItem from '~/common/DeviceItem';
import DetailDeviceItem from '~/common/DetailDeviceItem';
import { faCheckSquare } from '@fortawesome/free-regular-svg-icons';

const cx = classNames.bind(styles);

function DetailDevice() {
    const { id } = useParams();
    const [deviceData, setDeviceData] = useState([]);
    const [deviceManufacturer, setDeviceManufacturer] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        Aos.init();
    }, []);

    useEffect(() => {
        const detailDevice = async () => {
            setLoading(true);
            let url = `${endpoints['equipments']}`;
            try {
                // Lấy chi tiết thiết bị dựa trên ID
                const res = await APIs.get(`${url}?q=${id}`);

                if (res.data.data && res.data.data.length > 0) {
                    const selectedDevice = res.data.data[0];
                    setDeviceData(selectedDevice);

                    // Lấy tất cả thiết bị để lọc các thiết bị có cùng nhà sản xuất
                    const resManufacturer = await APIs.get(url);
                    // console.log(resManufacturer.data);
                    const filteredDevices = resManufacturer.data.data.filter(
                        (device) =>
                            device.manufacturer === selectedDevice.manufacturer && device.id !== selectedDevice.id,
                    );

                    setDeviceManufacturer(filteredDevices);
                } else {
                    console.warn('Không tìm thấy thiết bị với name:', id);
                }
            } catch (e) {
                console.error('Lỗi lấy dữ liệu chi tiết thiết bị: ' + e.message);
            } finally {
                setLoading(false);
            }
        };
        detailDevice();
    }, [id]);

    return (
        <div className={cx('container')}>
            <div className={cx('info-device')}>
                <div className={cx('info-content')}>
                    <span className={cx('info')}>
                        <FontAwesomeIcon className={cx('icon')} icon={faBars} />
                        <h2 className={cx('title')}>{deviceData.name}</h2>
                    </span>
                </div>
                <Row className={cx('wrapper-manufacturer')}>
                    <Col xs={12} lg={6} md={6} className={cx('manufacturer')}>
                        <FontAwesomeIcon className={cx('icon')} icon={faCheckSquare} />
                        <span className={cx('name-manufacturer')}>{deviceData.manufacturer}</span>
                    </Col>
                    <Col xs={12} lg={6} md={6}></Col>
                </Row>
            </div>
            <div className={cx('device-same')}></div>
            {deviceManufacturer.length > 0 ? (
                deviceManufacturer.map((device) => (
                    <Wrapper className={cx('wrapper')} key={device.id}>
                        {loading && <MySpinner />}
                        <Link to={`/detail/${device.name}`} className={cx('device-diff-link')} key={device.id}>
                            <Row data-aos="fade-up" className={cx('device-diff')}>
                                <DeviceItem
                                    xsImg="12"
                                    mdImg="3"
                                    lgImg="3"
                                    xsContent="12"
                                    mdContent="9"
                                    lgContent="9"
                                    className={classNames(styles.name, styles.image)}
                                    data={device}
                                    title="Xem chi tiết"
                                    leftIcon={<FontAwesomeIcon icon={faCircleChevronRight} />}
                                />
                            </Row>
                        </Link>
                    </Wrapper>
                ))
            ) : (
                <p className={cx('no-manufacturer')}>Không có thiết bị nào khác từ nhà sản xuất này.</p>
            )}
            <DetailDeviceItem data={deviceData} />
        </div>
    );
}

export default memo(DetailDevice);
