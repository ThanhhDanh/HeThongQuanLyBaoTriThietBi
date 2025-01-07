import classNames from 'classnames/bind';
import Tippy from '@tippyjs/react';
import 'tippy.js/dist/tippy.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChevronDown, faEarthAsia, faGear, faHeadset, faPhone, faSignOut } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';
import { faIdCard, faUser } from '@fortawesome/free-regular-svg-icons';
import { useCallback, useContext, useEffect, useState } from 'react';

import styles from './Header.module.scss';
import { config } from '~/routes/routes';
import image from '~/assets/images/anhweb.png';
import Button from '~/common/Button';
import APIs, { endpoints } from '~/configs/APIs';
import MenuDropDown from '~/common/Menu/MenuDropDown';
import Search from '~/common/Search';
import ScrollToTop from '~/common/ScrollToTop';
import SupportChat from '~/components/Chat/SupportChat';
import Login from '~/components/bases/Login';
import Register from '~/components/bases/Register';
import { MyDispatchContext, MyUserContext } from '~/App';
import ServiceDropDown from '~/common/Menu/ServiceDropDown';

const cx = classNames.bind(styles);

function Header() {
    const [services, setServices] = useState([]);
    const user = useContext(MyUserContext);
    const dispatch = useContext(MyDispatchContext);

    //Form login and register
    const [showLoginModal, setShowLoginModal] = useState(false);
    const [showRegisterModal, setShowRegisterModal] = useState(false);

    const handleShowLogin = useCallback(() => setShowLoginModal(true), []);
    const handleCloseLogin = useCallback(() => setShowLoginModal(false), []);

    const handleShowRegister = useCallback(() => setShowRegisterModal(true), []);
    const handleCloseRegister = useCallback(() => setShowRegisterModal(false), []);

    const loadServices = async () => {
        try {
            let res = await APIs.get(endpoints['services']);
            setServices(res.data);
        } catch (e) {
            console.error('Lỗi: ' + e.message);
        }
    };

    useEffect(() => {
        loadServices();
    }, []);

    const handleMenuChange = useCallback((menItem) => {
        switch (menItem.type) {
            case 'language':
                break;
            default:
        }
    }, []);

    const MENU_ITEMS = [
        {
            id: 3,
            icon: <FontAwesomeIcon icon={faEarthAsia} />,
            description: 'Tiếng Việt',
            children: {
                title: 'Language',
                data: [
                    {
                        id: 3,
                        type: 'language',
                        code: 'en',
                        description: 'English',
                    },
                    {
                        id: 4,
                        type: 'language',
                        code: 'vi',
                        description: 'Tiếng Việt',
                    },
                ],
            },
        },
    ];

    const userMenu = [
        {
            id: 1,
            icon: <FontAwesomeIcon icon={faUser} />,
            description: 'Hồ sơ',
            to: `${user ? `/${user.fullName}` : ''}`,
        },
        {
            id: 2,
            icon: <FontAwesomeIcon icon={faGear} />,
            description: 'Cài đặt',
            to: '/settings',
        },
        ...MENU_ITEMS,
        ...(user && user.role === 'ROLE_SUPPORT'
            ? [
                  {
                      id: 5,
                      icon: <FontAwesomeIcon icon={faHeadset} />,
                      description: 'Nhắn tin khách hàng',
                      to: config.routes.adminchat,
                  },
              ]
            : []),
        {
            id: 6,
            icon: <FontAwesomeIcon icon={faSignOut} />,
            description: 'Đăng xuất',
            onClick: () => dispatch({ type: 'logout' }),
            to: config.routes.home,
            separate: true,
        },
    ];

    return (
        <div className={cx('wrapper')}>
            <div className={cx('inner')}>
                <Link to={config.routes.home} className={cx('infos')}>
                    <Tippy delay={[0, 200]} content="Xin Chào bạn !!!" placement="bottom">
                        <img className={cx('info-image')} src={image} alt="Bảo Trì Thiết Bị" />
                    </Tippy>
                    <span className={cx('label')}>
                        QUẢN LÝ VÀ BẢO TRÌ THIẾT BỊ
                        <p className={cx('address')}>Địa chỉ: .....</p>
                    </span>
                </Link>

                {/* Search */}
                <Search />

                <div className={cx('navbar-menu')}>
                    <div className={cx('menu')}>
                        <div className={cx('menu-items')}>
                            <Button className={cx('menu-item')}>Giới thiệu</Button>
                            <ServiceDropDown items={services}>
                                <button className={cx('menu-item')}>
                                    <span className={cx('menu-title')}>Dịch vụ</span>
                                    <FontAwesomeIcon className={cx('menu-icon')} icon={faChevronDown} />
                                </button>
                            </ServiceDropDown>
                            {user === null ? (
                                <>
                                    <Button
                                        onClick={handleShowLogin}
                                        className={cx('menu-item', cx('btn-login'))}
                                        rightIcon={<FontAwesomeIcon icon={faUser} />}
                                    >
                                        Đăng nhập
                                    </Button>
                                    <Button
                                        onClick={handleShowRegister}
                                        className={cx('menu-item', cx('btn-register'))}
                                        rightIcon={<FontAwesomeIcon icon={faIdCard} />}
                                    >
                                        Đăng ký
                                    </Button>
                                </>
                            ) : (
                                <div className={cx('btn-avatar')}>
                                    <MenuDropDown onChange={handleMenuChange} items={userMenu}>
                                        <img className={cx('user-avatar')} src={user.avatar} alt={user.lastName} />
                                    </MenuDropDown>
                                </div>
                            )}
                        </div>
                    </div>
                    {user && (
                        <div className={cx('hotline')}>
                            <FontAwesomeIcon className={cx('hotline-icon')} icon={faPhone} />
                            <Button href={'tel:9999999999'} className={cx('hotline-phone')}>
                                9999999999
                            </Button>
                        </div>
                    )}
                </div>
            </div>

            <Login show={showLoginModal} handleClose={handleCloseLogin} />
            <Register show={showRegisterModal} handleClose={handleCloseRegister} />

            {user && user.role !== 'ROLE_SUPPORT' && <SupportChat />}
            <ScrollToTop />
        </div>
    );
}

export default Header;
