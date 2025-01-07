import React, { useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCircleArrowUp } from '@fortawesome/free-solid-svg-icons';
import styles from './ScrollToTop.module.scss'; // Bạn có thể tạo file SCSS này để tùy chỉnh giao diện
import classNames from 'classnames/bind';

const cx = classNames.bind(styles);

function ScrollToTop() {
    const [visible, setVisible] = useState(false);

    useEffect(() => {
        const toggleVisibility = () => {
            if (window.scrollY > 300) {
                setVisible(true);
            } else {
                setVisible(false);
            }
        };

        window.addEventListener('scroll', toggleVisibility);

        return () => window.removeEventListener('scroll', toggleVisibility);
    }, []);

    const scrollToTop = () => {
        window.scrollTo({
            top: 0,
            behavior: 'smooth',
        });
    };

    return (
        <div className={`${styles.scrollToTop} ${visible ? styles.visible : ''}`}>
            {visible && (
                <button onClick={scrollToTop} className={styles.button}>
                    <FontAwesomeIcon className={cx('icon')} icon={faCircleArrowUp} />
                </button>
            )}
        </div>
    );
}

export default ScrollToTop;
