import classNames from 'classnames/bind';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faListDots } from '@fortawesome/free-solid-svg-icons';

//Hiểu ứng khi cuộn trang
import Aos from 'aos';
import 'aos/dist/aos.css';

import { config } from '~/routes/routes';
import { Device, DeviceActive, HomeActiveIcon, HomeIcon, Tool, ToolActive, Faqs, FaqsActive } from '~/common/Icons';
import styles from './SideBar.module.scss';
import Menu from '~/common/Menu';
import MenuItem from '~/common/Menu/MenuItems';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import APIs, { endpoints } from '~/configs/APIs';

const cx = classNames.bind(styles);

function SideBar() {
    const navigate = useNavigate();
    const [equipment, setEquipment] = useState([]);
    const [device, setDevice] = useState([]);
    const [tool, setTool] = useState([]);

    const loadEquipment = async () => {
        try {
            let res = await APIs.get(endpoints['equipments']);
            setEquipment(res.data.data);
            let devices = res.data.data.filter((dev) => dev.type === 'Thiết bị');
            setDevice(devices);
            let tools = res.data.data.filter((dev) => dev.type === 'Công cụ');
            setTool(tools);
        } catch (error) {
            console.log('Lỗi lấy loại thiết bị: ' + error.message);
        }
    };

    useEffect(() => {
        loadEquipment();
        Aos.init();
    }, []);

    return (
        <aside className={cx('wrapper')}>
            <MenuItem to={config.routes.home} title="Trang chủ" icon={<HomeIcon />} activeIcon={<HomeActiveIcon />} />
            <div>
                <span className={cx('header')}>
                    <FontAwesomeIcon className={cx('header-icon')} icon={faListDots} />
                    <p className={cx('header-title')}>DANH MỤC THIẾT BỊ</p>
                </span>
                <Menu>
                    <MenuItem
                        onClick={() => {
                            navigate('/tools', { state: { tool } });
                        }}
                        to={config.routes.tools}
                        title="Công cụ"
                        icon={<Tool />}
                        activeIcon={<ToolActive />}
                    />
                    <MenuItem
                        onClick={() => {
                            navigate('/devices', { state: { device } });
                        }}
                        to={config.routes.devices}
                        title="Thiết bị"
                        icon={<Device />}
                        activeIcon={<DeviceActive />}
                    />
                    <MenuItem
                        onClick={() => {
                            navigate('/forum');
                        }}
                        to={config.routes.forum}
                        title="FAQS"
                        icon={<Faqs />}
                        activeIcon={<FaqsActive />}
                    />
                </Menu>
            </div>
        </aside>
    );
}

export default SideBar;
