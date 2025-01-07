import classNames from 'classnames/bind';
import PropTypes from 'prop-types';

import styles from './ServiceDropDown.module.scss';
import Button from '~/common/Button';
import { memo } from 'react';

const cx = classNames.bind(styles); //bind để khi đặt tên scss có dấu -

function ServiceItemDropDown({ data, onClick }) {
    const classes = cx('menu-item', {
        separate: data.separate,
    });

    return (
        <Button className={classes} leftIcon={data.icon} to={data.to} onClick={data.onClick || onClick}>
            {data.description}
        </Button>
    );
}

ServiceItemDropDown.propTypes = {
    data: PropTypes.object.isRequired,
    onClick: PropTypes.func,
};

export default memo(ServiceItemDropDown);
