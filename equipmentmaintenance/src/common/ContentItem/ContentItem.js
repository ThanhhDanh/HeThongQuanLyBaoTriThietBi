import classNames from 'classnames/bind';

import { Link, NavLink } from 'react-router-dom';
import { Col } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheck, faCircleChevronRight, faStar as Star } from '@fortawesome/free-solid-svg-icons';
import { faStar } from '@fortawesome/free-regular-svg-icons';
//Hiểu ứng khi cuộn trang
import Aos from 'aos';
import 'aos/dist/aos.css';

import styles from './ContentItem.module.scss';
import { useEffect, useState } from 'react';
import APIs, { endpoints } from '~/configs/APIs';
import Button from '~/common/Button';
import MySpinner from '~/common/Spinner';
import ReactPaginate from 'react-paginate';

const cx = classNames.bind(styles);
// { name, description, image, alt, icon, seeDetail, to }
function ContentItem() {
    const [equipmences, setEquipmences] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [page, setPage] = useState(1);
    const [totalPages, setTotalPages] = useState(0);

    const loadEquipmences = async () => {
        setIsLoading(true);
        try {
            let url = `${endpoints['equipments']}?equipPage=${page}`;
            let res = await APIs.get(url);
            let resImg = await APIs.get(endpoints['equipmentimages']);

            const equipWithImages = res.data.data.map((equipment) => {
                const image = resImg.data.find((img) => img.equipmentId.id === equipment.id);

                return {
                    ...equipment,
                    image: image?.image,
                };
            });

            setEquipmences(equipWithImages);
            const totalCount = res.data.totalCount || 0;
            setTotalPages(Math.ceil(totalCount / 6));
        } catch (e) {
            console.error('Lỗi tải equipmences: ' + e.message);
        } finally {
            setIsLoading(false);
        }
    };

    Aos.init();

    useEffect(() => {
        loadEquipmences();
    }, [page]);

    const handlePageClick = (selectedItem) => {
        setPage(selectedItem.selected + 1);
    };

    return (
        <>
            {equipmences.map((equipment) => (
                <Col key={equipment.id} data-aos="zoom-in-up" xs={12} md={6} lg={6} className={cx('no-gutters')}>
                    {isLoading && <MySpinner />}
                    <div className={cx('wrapper')}>
                        <Link className={cx('img-equip')}>
                            <img className={cx('image')} src={equipment.image} alt={equipment.name} />
                        </Link>
                        <div className={cx('infos-equip')}>
                            <Link className={cx('name-equip')}>
                                <h2 className={cx('name')}>{equipment.name}</h2>
                            </Link>
                            <span className={cx('description')}>{equipment.description}</span>
                            <ul className={cx('provides')}>
                                <li className={cx('type', 'provide')}>
                                    <FontAwesomeIcon className={cx('check')} icon={faCheck} />
                                    <NavLink className={(nav) => cx('action', { active: nav.isActive })}>
                                        <p className={cx('label')}>Loại:</p>
                                        {equipment.type}
                                        <FontAwesomeIcon className={cx('icon')} icon={faStar} />
                                        <FontAwesomeIcon className={cx('active-icon')} icon={Star} />
                                    </NavLink>
                                </li>
                                <li className={cx('manufacturer', 'provide')}>
                                    <FontAwesomeIcon className={cx('check')} icon={faCheck} />
                                    <NavLink className={(nav) => cx('action', { active: nav.isActive })}>
                                        <p className={cx('label')}>Nhà cung cấp:</p>
                                        {equipment.manufacturer}
                                        <FontAwesomeIcon className={cx('icon')} icon={faStar} />
                                        <FontAwesomeIcon className={cx('active-icon')} icon={Star} />
                                    </NavLink>
                                </li>
                            </ul>
                            <div className={cx('see-add')}>
                                <FontAwesomeIcon className={cx('icon')} icon={faCircleChevronRight} />
                                {/* <span className={cx('see-detail')}>Xem chi tiết</span> */}
                                <Button className={cx('see-detail')} to={`/detail/${equipment.name}`}>
                                    Xem chi tiết
                                </Button>
                            </div>
                        </div>
                    </div>
                </Col>
            ))}
            <Col className={cx('paginates')} xs={12} md={12} lg={12}>
                <ReactPaginate
                    className={cx('paginate')}
                    breakLabel={'...'}
                    breakClassName={'break-me'}
                    pageCount={totalPages}
                    marginPagesDisplayed={2}
                    pageRangeDisplayed={5}
                    onPageChange={handlePageClick}
                    containerClassName={'pagination'}
                    activeClassName={cx('active')}
                />
            </Col>
        </>
    );
}

export default ContentItem;
