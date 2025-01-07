import classNames from 'classnames/bind';
import PropTypes from 'prop-types';

import Header from '~/components/bases/Header';
import styles from '~/components/layouts/DefaultLayout.module.scss';

const cx = classNames.bind(styles);

function PaymentDefaultLayout({ children }) {
    return (
        <div className={cx('wrapper')}>
            <Header />
            <div className={cx('container')}>
                <div className={cx('content')}>{children}</div>
            </div>
        </div>
    );
}

PaymentDefaultLayout.propTypes = {
    children: PropTypes.node.isRequired,
};

export default PaymentDefaultLayout;
