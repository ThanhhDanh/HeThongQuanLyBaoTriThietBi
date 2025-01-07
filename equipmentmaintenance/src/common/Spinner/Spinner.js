import { Spinner } from 'react-bootstrap';
import classNames from 'classnames/bind';

import styles from './Spinner.module.scss';

const cx = classNames.bind(styles);

function MySpinner({ animation = 'border', size = 'sm', variant = 'light' }) {
    return <Spinner className={cx('spinner')} animation={animation} size={size} variant={variant} />;
}

export default MySpinner;
