import classNames from 'classnames/bind';
import PropTypes from 'prop-types';
import styles from './DeviceItem.module.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheckCircle } from '@fortawesome/free-solid-svg-icons';
import { memo, useEffect, useState } from 'react';
import APIs, { endpoints } from '~/configs/APIs';
import { Col } from 'react-bootstrap';
import MySpinner from '../Spinner/Spinner';

const cx = classNames.bind(styles);

function DeviceItem({ data, ...props }) {
    const [imageUrl, setImageUrl] = useState('');
    const [loading, setLoading] = useState(false);

    const loadEquipmentImage = async () => {
        setLoading(true);
        try {
            let res = await APIs.get(endpoints['equipmentimages']);

            // Tìm ảnh dựa trên equipmentId của thiết bị
            const equipmentImage = res.data.find((img) => img.equipmentId.id === data.id);

            if (equipmentImage) {
                setImageUrl(equipmentImage.image); // Gán URL ảnh tìm được
            } else {
                console.warn(`Không tìm thấy ảnh cho thiết bị ID: ${data.id}`);
            }
        } catch (e) {
            console.error('Lỗi tải ảnh: ' + e.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadEquipmentImage();
    }, []);

    return (
        <>
            {loading && <MySpinner />}
            <Col xs={props.xsImg} md={props.mdImg} lg={props.lgImg}>
                <img className={classNames(props.className, styles.avatar)} src={imageUrl} alt={data.name} />
            </Col>
            <Col xs={props.xsContent} md={props.mdContent} lg={props.lgContent}>
                <div className={cx('info')}>
                    <p className={cx('name')}>
                        <span className={props.className}>{data.name}</span>
                        {data.currentStatus === 'Hoạt động' && (
                            <FontAwesomeIcon className={cx('check')} icon={faCheckCircle} />
                        )}
                    </p>
                    <span className={cx('username')}>{data.manufacturer}</span>
                </div>
                {props.rightIcon ||
                    (props.leftIcon && (
                        <button className={cx('detail')}>
                            {props.leftIcon && <span className={cx('icon')}>{props.leftIcon}</span>}
                            <span className={cx('title')}>{props.title}</span>
                            {props.rightIcon && <span className={cx('icon')}>{props.rightIcon}</span>}
                        </button>
                    ))}
            </Col>
        </>
    );
}

DeviceItem.propTypes = {
    data: PropTypes.object.isRequired,
};

export default memo(DeviceItem);
