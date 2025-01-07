import classNames from 'classnames/bind';
import styles from './Slide.module.scss';
import { Children, useEffect, useState } from 'react';

const cx = classNames.bind(styles);

function Slide({ children }) {
    const [currentIndex, setCurrentIndex] = useState(0);
    const length = Children.count(children);

    useEffect(() => {
        const interval = setInterval(() => {
            setCurrentIndex((prevIndex) => (prevIndex + 1) % length);
        }, 4000); // Thay đổi slide sau mỗi 3 giây
        return () => clearInterval(interval);
    }, [length]);

    const nextSlide = () => {
        setCurrentIndex((prevIndex) => (prevIndex + 1) % length);
    };

    const prevSlide = () => {
        setCurrentIndex((prevIndex) => (prevIndex - 1 + length) % length);
    };

    return (
        <div className={cx('wrapper')}>
            <div className={cx('inner')} style={{ transform: `translateX(-${currentIndex * 100}%)` }}>
                {Children.map(children, (child, index) => (
                    <div className={cx('fade', { active: index === currentIndex })}>{child}</div>
                ))}
            </div>
            <button className={cx('arrow', 'arrow-left')} onClick={prevSlide}>
                &#9664;
            </button>
            <button className={cx('arrow', 'arrow-right')} onClick={nextSlide}>
                &#9654;
            </button>
        </div>
    );
}

export default Slide;
