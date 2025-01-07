import classNames from 'classnames/bind';
import PropTypes from 'prop-types';

//Hiểu ứng khi cuộn trang
import Aos from 'aos';
import 'aos/dist/aos.css';

import Header from '../bases/Header';
import SideBar from '../bases/SideBar';
import styles from './DefaultLayout.module.scss';
import { useEffect } from 'react';

const cx = classNames.bind(styles);

function DefaultLayout({ children }) {
    useEffect(() => {
        Aos.init();
    }, []);

    return (
        <div className={cx('wrapper')}>
            <Header />
            <div className={cx('container')}>
                <div className={cx('grid', 'ds-flex')}>
                    <div className={cx('row', 'no-gutters')}>
                        <div className={cx('c-12', 'l-o-3', 'm-o-3')}>
                            <SideBar />
                        </div>
                        <div data-aos="fade-up" className={cx('content')}>
                            {children}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

DefaultLayout.propTypes = {
    children: PropTypes.node.isRequired,
};

export default DefaultLayout;
