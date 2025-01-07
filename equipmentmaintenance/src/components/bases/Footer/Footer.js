import classNames from 'classnames/bind';

import styles from './Footer.module.scss';
import { Col, Row } from 'react-bootstrap';

const cx = classNames.bind(styles);

function Footer() {
    return (
        <div className={cx('wrapper')}>
            <div className={cx('inner')}>
                <Row>
                    <Col xs={12} md={6} lg={6}>
                        <div className={cx('')}>
                            <h2>Tại sao nên chọn đại lý</h2>
                        </div>
                    </Col>
                    <Col xs={12} md={6} lg={6}></Col>
                </Row>
            </div>
        </div>
    );
}

export default Footer;
