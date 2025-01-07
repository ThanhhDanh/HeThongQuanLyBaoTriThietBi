import classNames from 'classnames/bind';
import { faCircleXmark, faMagnifyingGlass, faSpinner } from '@fortawesome/free-solid-svg-icons';
import HeadlessTippy from '@tippyjs/react/headless';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useEffect, useState, useRef } from 'react';

import Wrapper from '../Menu';
import styles from './Search.module.scss';
import { useDebounce } from '~/hooks';
import APIs, { endpoints } from '~/configs/APIs';
import DeviceItem from '~/common/DeviceItem';
import { Link } from 'react-router-dom';
import MySpinner from '../Spinner/Spinner';
import { Row } from 'react-bootstrap';

const cx = classNames.bind(styles); //bind để khi đặt tên scss có dấu -

function Search() {
    const [searchEquipment, setSearchEquipment] = useState([]);
    const [searchValue, setSearchValue] = useState('');
    const [showResults, setShowResults] = useState(true);
    const [loading, setLoading] = useState(false);

    const debouncedValue = useDebounce(searchValue, 800);
    const inputRef = useRef();

    useEffect(() => {
        if (!debouncedValue.trim()) {
            setSearchEquipment([]);
            return;
        }

        const fetchApi = async () => {
            let url = `${endpoints['equipments']}?q=${debouncedValue}`;
            try {
                setLoading(true);
                let res = await APIs.get(url);
                setSearchEquipment(res.data.data);
            } catch (e) {
                console.error('Lỗi tìm kiếm thiết bị: ' + e.message);
            } finally {
                setLoading(false);
            }
        };
        fetchApi();
    }, [debouncedValue]);

    const handleClear = () => {
        setSearchValue('');
        setSearchEquipment([]);
        inputRef.current.focus();
    };

    const hanhdleHideResult = () => {
        setShowResults(false);
    };

    const handleChange = (e) => {
        const searchValue = e.target.value;
        if (!searchValue.startsWith(' ')) {
            setSearchValue(searchValue);
        }
    };

    return (
        //Warning của Tippy nên phải thêm div
        <div>
            <HeadlessTippy
                interactive
                visible={showResults && searchEquipment.length > 0}
                render={(attrs) => (
                    <div className={cx('search-result')} tabIndex={-1} {...attrs}>
                        {loading && <MySpinner />}
                        <Wrapper>
                            <h4 className={cx('search-title')}>Thiết bị</h4>
                            {searchEquipment.map((result) => (
                                <Link to={`/detail/${result.name}`} key={result.id} className={cx('wrapper')}>
                                    <Row className={cx('align-items-center')}>
                                        <DeviceItem
                                            xsImg="3"
                                            mdImg="3"
                                            lgImg="3"
                                            xsContent="9"
                                            mdContent="9"
                                            lgContent="9"
                                            data={result}
                                        />
                                    </Row>
                                </Link>
                            ))}
                        </Wrapper>
                    </div>
                )}
                onClickOutside={hanhdleHideResult}
            >
                <div className={cx('search')}>
                    <input
                        ref={inputRef}
                        value={searchValue}
                        placeholder="Tìm kiếm thiết bị......"
                        spellCheck={false}
                        onChange={handleChange}
                        onFocus={() => setShowResults(true)}
                    />
                    {!!searchValue && !loading && (
                        <button className={cx('clear')} onClick={handleClear}>
                            <FontAwesomeIcon icon={faCircleXmark} />
                        </button>
                    )}
                    {loading && <FontAwesomeIcon className={cx('loading')} icon={faSpinner} />}

                    <button className={cx('search-btn')} onMouseDown={(e) => e.preventDefault()}>
                        <FontAwesomeIcon icon={faMagnifyingGlass} />
                    </button>
                </div>
            </HeadlessTippy>
        </div>
    );
}

export default Search;
