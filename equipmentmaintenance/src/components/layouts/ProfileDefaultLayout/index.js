import classNames from 'classnames/bind';
import PropTypes from 'prop-types';

//Hiểu ứng khi cuộn trang
import Aos from 'aos';
import 'aos/dist/aos.css';

import Header from '~/components/bases/Header';
import styles from '~/components/layouts/DefaultLayout.module.scss';
import { useEffect } from 'react';

const cx = classNames.bind(styles);

function ProfileDefaultLayout({ children }) {
    useEffect(() => {
        Aos.init();
    }, []);

    return (
        <div className={cx('wrapper')}>
            <Header />
            <div className={cx('container')}>
                <div data-aos="fade-up" className={cx('content')}>
                    {children}
                </div>
            </div>
        </div>
    );
}

ProfileDefaultLayout.propTypes = {
    children: PropTypes.node.isRequired,
};

export default ProfileDefaultLayout;
