import classNames from 'classnames/bind';
import PropTypes from 'prop-types';
import styles from './ServiceDropDown.module.scss';
import { memo, useEffect, useState } from 'react';
import Wrapper from '../Wrapper';
import Tippy from '@tippyjs/react/headless';
import 'tippy.js/dist/tippy.css';
import Header from './Header';
import ServiceItemDropDown from './ServiceItemDropDown';
import { useNavigate } from 'react-router-dom';

const cx = classNames.bind(styles);

const defaultFn = () => {};

function ServiceDropDown({ children, items = [], onChange = defaultFn }) {
    const [history, setHistory] = useState([{ data: items }]);
    const nav = useNavigate();

    useEffect(() => {
        if (items.length > 0) {
            setHistory([{ data: items }]);
        }
    }, [items]);

    const current = history[history.length - 1] || { data: [] };

    const renderItems = () => {
        return current.data.map((service) => {
            const isParent = !!service.children;

            return (
                <ServiceItemDropDown
                    key={service.id}
                    data={service}
                    onClick={() => {
                        nav('/service/' + service.id, { state: { service } });
                        if (isParent) {
                            setHistory((prev) => [...prev, service.children]);
                        } else {
                            onChange(service);
                        }
                    }}
                />
            );
        });
    };

    const handleBack = () => {
        setHistory((prev) => prev.slice(0, prev.length - 1));
    };

    const renderResult = (attrs) => {
        return (
            <div className={cx('menu-list')} tabIndex={-1} {...attrs}>
                <Wrapper className={cx('menu-popper')}>
                    {history.length > 1 && <Header title={current.title} onBack={handleBack} />}
                    <div className={cx('menu-body')}>{renderItems()}</div>
                </Wrapper>
            </div>
        );
    };

    const handleResetToFirstPage = () => {
        setHistory((prev) => prev.slice(0, 1));
    };

    return (
        <div>
            <Tippy
                delay={[0, 600]}
                offset={[16, 2]}
                interactive
                placement="bottom-end"
                // hideOnClick={hideOnClick}
                render={renderResult}
                onHide={handleResetToFirstPage} // khi hover ra bên ngoài thì nó đóng menu và quay lại đầu
            >
                {children}
            </Tippy>
        </div>
    );
}

ServiceDropDown.propTypes = {
    children: PropTypes.node.isRequired,
    items: PropTypes.array,
    hideOnClick: PropTypes.bool,
    onChange: PropTypes.func,
};

export default memo(ServiceDropDown);
