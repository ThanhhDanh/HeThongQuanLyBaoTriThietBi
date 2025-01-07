import classNames from 'classnames/bind';
import styles from './Slide.module.scss';

const cx = classNames.bind(styles);

function SlideItem({ src, alt, className }) {
    return (
        <div className={cx('slide-item')}>
            <img src={src} alt={alt} className={className} />
        </div>
    );
}

export default SlideItem;
