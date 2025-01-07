import classNames from 'classnames/bind';
import PropTypes from 'prop-types';
import styles from './DetailDeviceItem.module.scss';
import { Wrapper } from '../Menu';
import { memo, useCallback, useContext, useEffect, useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCircleDown, faEarthAsia, faEnvelope, faPhone } from '@fortawesome/free-solid-svg-icons';
import { Col, Row, Table } from 'react-bootstrap';
import APIs, { endpoints } from '~/configs/APIs';
import { useNavigate, useParams } from 'react-router-dom';
import MySpinner from '~/common/Spinner';
import { formatPrice } from '~/Utils/Utils';
import Button from '../Button';
import { config } from '~/routes/routes';
import { faAddressBook, faSquareCheck } from '@fortawesome/free-regular-svg-icons';
import Login from '~/components/bases/Login';
import { MyUserContext } from '~/App';

const cx = classNames.bind(styles);

function DetailDeviceItem({ data, ...props }) {
    const [imageUrl, setImageUrl] = useState('');
    const [loading, setLoading] = useState(false);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const { id } = useParams();
    const user = useContext(MyUserContext);
    const navigate = useNavigate();
    const [showLoginModal, setShowLoginModal] = useState(false);
    const handleShowLogin = useCallback(() => setShowLoginModal(true), []);
    const handleCloseLogin = useCallback(() => setShowLoginModal(false), []);

    const [zoomStyle, setZoomStyle] = useState({
        zoomShow: 'none',
        zoomX: '50%',
        zoomY: '50%',
    });

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            let url = `${endpoints['equipments']}?q=${id.id}`;
            try {
                let res = await APIs.get(url);
                if (res.data.data) {
                    const fetchedData = res.data.data;
                    loadEquipmentImage(fetchedData);
                }
            } catch (e) {
                console.error('Lỗi lấy dữ liệu thiết bị: ' + e.message);
            } finally {
                setLoading(false);
            }
        };

        if (!data || Object.keys(data).length === 0) {
            fetchData();
        } else {
            loadEquipmentImage(data);
        }
    }, [data, id]);

    const loadEquipmentImage = async (deviceData) => {
        if (!deviceData || !deviceData.id) {
            console.warn('Dữ liệu thiết bị không hợp lệ hoặc không có ID');
            return;
        }

        setLoading(true);
        try {
            let res = await APIs.get(endpoints['equipmentimages']);

            // Tìm ảnh dựa trên equipmentId của thiết bị
            const equipmentImage = res.data.find((img) => img.equipmentId.id === deviceData.id);

            if (equipmentImage) {
                setImageUrl(equipmentImage.image); // Gán URL ảnh tìm được
            } else {
                console.warn(`Không tìm thấy ảnh cho thiết bị ID: ${deviceData.id}`);
            }
        } catch (e) {
            console.error('Lỗi tải ảnh: ' + e.message);
        } finally {
            setLoading(false);
        }
    };

    // Mở modal khi double-click
    const handleDoubleClick = () => {
        setIsModalOpen(true);
    };

    // Đóng modal khi click vào overlay hoặc nút close
    const handleCloseModal = () => {
        setIsModalOpen(false);
    };

    const handleMouseMove = (e) => {
        const positionPx = e.clientX - e.currentTarget.getBoundingClientRect().left;
        const positionPy = e.clientY - e.currentTarget.getBoundingClientRect().top;

        const positionX = (100 * positionPx) / e.currentTarget.offsetWidth;
        const positionY = (100 * positionPy) / e.currentTarget.offsetHeight;

        setZoomStyle({
            zoomShow: 'block', // Hiện vòng tròn zoom
            zoomX: `${positionX}%`,
            zoomY: `${positionY}%`,
        });
    };

    const handleMouseLeave = () => {
        setZoomStyle({
            ...zoomStyle,
            zoomShow: 'none',
        });
    };

    return (
        <>
            <Wrapper className={cx('wrapper')}>
                {loading && <MySpinner />}
                <div className={cx('inner')}>
                    <div className={cx('header-content')}>
                        <h2 className={cx('title')}>THIẾT BỊ {data.name} GIÁ TỐT</h2>
                        <div className={cx('icon-devide')}>
                            <FontAwesomeIcon className={cx('icon')} icon={faCircleDown} />
                        </div>
                        <span className={cx('content')}>
                            Đại lý phân phối thiết bị <span className={cx('title-sup')}>{data.name}</span> chất lượng
                            cao của nhiều hãng và nhà cung cấp nổi tiếng trên thị trường. Địa chỉ đáng tin cậy cho các
                            khách hàng là nhà máy, thương mại khi có nhu cầu lựa chọn sản phẩm.
                        </span>
                    </div>
                    <div className={cx('body-content')}>
                        <div className={cx('content')}>
                            <h2 className={cx('title-body')}>Thiết bị {data.name}</h2>
                            <Row>
                                <Col xs={12} md={7} lg={7}>
                                    <p className={cx('content-body')}>
                                        Không ít người vẫn đang băn khoăn không biết thiết bị {data.name} là gì và ý
                                        nghĩa của chúng trong cuộc sống hiện đại này như thế nào? Đây được hiệu là
                                        thường sử dụng cho các công việc đặc thù, có tính chất chuyên biệt. Vì vậy, loại
                                        này ít được giao dịch, mua bán phổ biến trên thị trường. Các thiết bị này đóng
                                        vai trò rất quan trọng trong đời sống của mỗi người. Sự hiện diện của thiết bị
                                        không chỉ giúp đảm bảo an toàn, tiết kiệm thời gian mà còn góp phần không nhỏ
                                        trong việc giúp ích con người vận hành trong cuộc sống này.
                                    </p>
                                </Col>
                                <Col
                                    className={cx('info-img')}
                                    onMouseMove={handleMouseMove}
                                    onMouseLeave={handleMouseLeave}
                                    onDoubleClick={handleDoubleClick}
                                    xs={12}
                                    md={5}
                                    lg={5}
                                >
                                    <img className={cx('image')} src={imageUrl} alt={data.name} />
                                    <img
                                        className="img"
                                        src={imageUrl}
                                        alt={data.name}
                                        style={{
                                            position: 'absolute',
                                            left: 0,
                                            bottom: 0,
                                            transform: 'scale(1.5)',
                                            pointerEvents: 'none',
                                            clipPath: `circle(100px at ${zoomStyle.zoomX} ${zoomStyle.zoomY})`,
                                            display: zoomStyle.zoomShow,
                                            zIndex: 10,
                                            height: '300px',
                                            width: '100%',
                                        }}
                                    />
                                </Col>
                            </Row>
                        </div>
                        <div className={cx('content')}>
                            <h2 className={cx('title-body')}>Phân loại các thiết bị {data.name}</h2>
                            <div className={cx('content-body')}>
                                <p className={cx('content-item')}>
                                    Là những sản phẩm được sử dụng khá phổ biến trong đời sống của con người. Chúng được
                                    mua bán, trao đổi khá nhiều trên thị trường. Bạn có thể dễ dàng tìm kiếm các thông
                                    tin liên quan tới sản phẩm mà mình mong muốn. Để đảm bảo chọn đúng loại thiết bị{' '}
                                    {data.name} mình cần và giúp sản phẩm có thể phát huy tối đa vai trò của mình trong
                                    mọi hoàn cảnh, khách hàng cần phải biết cách phân loại chúng. Với một vài thông tin
                                    cơ bản về các loại thiết bị dưới đây, đại lý tin là quý khách hàng sẽ dễ dàng hơn
                                    trong việc đưa ra sự lựa chọn cho mình.
                                </p>
                                <ul className={cx('content-list')}>
                                    <li className={cx('content-item')}>
                                        &#9594; Máy, thiết bị động lực: là những loại máy phát động lực, máy biến áp,
                                        máy phát điện, các thiết bị nguồn,…
                                    </li>
                                    <li className={cx('content-item')}>
                                        &#9594; Máy, thiết bị công tác: là máy móc, thiết bị dùng trong tất cả các lĩnh
                                        vực của cuộc sống. Chẳng hạn như: ngành công nghiệp khai khoáng, nông, lâm
                                        nghiệp, cơ khí, lọc hóa dầu, xây dựng, in ấn, công nghệ điện tử,…
                                    </li>
                                    <li className={cx('content-item')}>
                                        &#9594; Dụng cụ làm việc thí nghiệm, đo lường: là các loại thiết bị được sử dụng
                                        để đo các đại lượng cơ học, nhiệt học, âm học, thiết bị điện và điện tử, thiết
                                        bị đo và phân tích lý hóa, thiết bị chuyên ngành đặc biệt,…
                                    </li>
                                    <li className={cx('content-item')}>
                                        &#9594; Thiết bị và phương tiện vận tải: phương tiện vận tải đường bộ, đường
                                        sắt, đường thủy, đường hàng không, phương tiện bốc dỡ, thiết bị vận chuyển đường
                                        ống,…
                                    </li>
                                    <li className={cx('content-item')}>
                                        &#9594; Dụng cụ quản lý: gồm các thiết bị tính toán, đo lường, trang thiết bị
                                        thông tin, điện tử và các phầm mềm tin học để phục vụ cho công tác quản lý.
                                    </li>
                                    <li className={cx('content-item')}>
                                        &#9594; Máy móc, thiết bị mới: là những loại máy, thiết bị được chế tạo mới, mua
                                        sắm mới, chưa qua sử dụng.
                                    </li>
                                    <li className={cx('content-item')}>
                                        &#9594; Máy móc thiết bị cũ: là những loại máy,thiết bị đã qua sử dụng.
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div className={cx('content')}>
                            <h2 className={cx('title-body')}>Ứng dụng của thiết bị {data.name}</h2>
                            <div className={cx('content-body')}>
                                <p className={cx('content-item')}>
                                    Do trên thị trường có rất nhiều thiết bị {data.name} khác nhau và tất nhiên là ứng
                                    dụng của chúng cũng không giống nhau. Khách hàng cần phải nắm được ứng dụng của sản
                                    phẩm để có thể đưa ra sự lựa chọn chính xác với nhu cầu sử dụng của mình.
                                </p>
                                <ul className={cx('content-list')}>
                                    <li className={cx('content-item')}>
                                        &#9594; Trong sinh hoạt, thiết bị điện tử là những vật dụng thiết yếu sử dụng
                                        hàng ngày, giúp bạn giảm được công sức và thời gian cho việc dọn dẹp như: máy
                                        giặt, máy hút bụi, máy rửa chén, nồi cơm điện, quạt điện,…Hay các thiết bị điện
                                        tử có chức năng giải trí, nâng cao đời sống tinh thần như: tivi, máy tính,…
                                    </li>
                                    <li className={cx('content-item')}>
                                        &#9594; Trước kia khi thiết bị điện từ chưa ra đời thì mọi hoạt động sản xuất,
                                        lao động đều sử dụng sức người là chính khiến cho năng xuất lao động không được
                                        cao, bên cạnh đó chất lượng sản phẩm không đảm bảo.
                                    </li>
                                    <li className={cx('content-item')}>
                                        &#9594; Từ khi có thiết bị điện tử, mọi hoạt động của con người trở nên đơn giản
                                        và dễ dàng hơn rất nhiều, giúp tăng năng xuất làm việc lên đáng kể nhờ có máy
                                        kéo, máy gặt,…
                                    </li>
                                    <li className={cx('content-item')}>
                                        &#9594; Với sự phát triển của các thiết bị điện tử trong cuộc sống hàng ngày của
                                        mỗi gia đình, góp phần rất lớn cho sự phát triển xã hội.
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div className={cx('content')}>
                            <h2 className={cx('title-body')}>Các hãng sản xuất thiết bị {data.name}</h2>
                            <p className={cx('content-body')}>
                                Với thị trường đa dạng các hãng sản xuất thiết bị {data.name} như hiện nay thì khách
                                hàng cần phải cân nhắc để chọn được thương hiệu uy tín. Đại lý sẽ “bật mí” một số hãng
                                nổi tiếng với độ uy tín và chất lượng cao ngay dưới đây để khách hàng tham khảo hiệu quả
                                hơn.
                            </p>
                        </div>
                        <div className={cx('content')}>
                            <h2 className={cx('title-body')}>Thiết bị điện SCHNEIDER</h2>
                            <div className={cx('content-body')}>
                                <p className={cx('content-item')}>
                                    Thiết bị điện Schneider là thiết bị điện nhập khẩu đến từ tập đoàn sản xuất thiết bị
                                    điện công nghiệp hàng đầu Châu Âu xuất xứ nước Pháp. Với sự phát triển mạnh mẽ không
                                    ngừng nghỉ, tính đến nay Schneider đã có các nhà máy sản xuất ở trên 30 quốc gia
                                    trên thế giới với lượng nhân viên hùng hậu trên 20,000 người.
                                </p>
                                <ul className={cx('content-list')}>
                                    <li className={cx('content-item')}>&#9594; Aptomat dạng tép: RCCB, RCBO</li>
                                    <li className={cx('content-item')}>
                                        &#9594; Cầu dao tự động dạng khối: MCCB, MCB, ELCB
                                    </li>
                                    <li className={cx('content-item')}>&#9594; Khởi động từ: contactor</li>
                                    <li className={cx('content-item')}>&#9594; Rơ le nhiệt và rơ le bảo vệ</li>
                                </ul>
                            </div>
                        </div>
                        <div className={cx('content')}>
                            <h2 className={cx('title-body')}>Thiết bị điện PANASONIC</h2>
                            <p className={cx('content-body')}>
                                Thiết bị điện Panasonic mang đến đa dạng về các sản phẩm thiết bị như: ổ cắm, công tắc
                                điện, quạt trần, quạt hút các loại, máy bơm, máy nước nóng, đèn chiếu sáng… đều được
                                phân phối đạt chất lượng tốt nhất, mang đến sự yên tâm cho mọi người khi lắp đặt ở các
                                công trình khác nhau.
                            </p>
                        </div>
                        <div className={cx('content')}>
                            <h2 className={cx('title-body')}>Thiết bị điện MITSUBISHI</h2>
                            <p className={cx('content-body')}>
                                Mitsubishi Electric một thương hiệu uy tín xuất xứ đến từ Nhật Bản sản xuất thiết bị
                                điện và kiến trúc được thành lập vào ngày 15 tháng 1 năm 1921. Đến nay đã nổi tiếng là
                                một Tập đoàn công nghệ thân thiện với môi trường hàng đầu trên toàn thế giới. Thiết bị
                                điện MITSUBISHI là một sản phẩm được sử dụng rộng rãi trên toàn thế giới với sự uy tín
                                và chất lượng cao, đáp ứng được nhu cầu khách hàng khó tính nhất.
                            </p>
                        </div>
                        <div className={cx('content')}>
                            <h2 className={cx('title-body')}>Quy trình bảo dưỡng máy móc thiết bị</h2>
                            <div className={cx('content-body')}>
                                <p className={cx('content-item')}>
                                    Trang thiết bị là gì? Quy trình bảo dưỡng ra sao luôn là mối quan tâm hàng đầu của
                                    khách hàng nhà mình. Dưới đây là các bước cơ bản để bảo quản máy móc thiết bị một
                                    cách tốt nhất
                                </p>
                                <ul className={cx('content-list')}>
                                    <li className={cx('content-item')}>
                                        <ul className={cx('sup-list')}>
                                            <span className={cx('sup-title')}>&#8658; Xác định mục địch bảo trì</span>
                                            <li className={cx('sup-item')}>
                                                &#9594; Bảo trì máy móc, thiết bị được thực hiện nhằm duy trì tình trạng
                                                hoạt động tốt nhất của chúng với chi phí tiết kiệm nhất có thể. Nhiệm vụ
                                                chính của công tác bảo dưỡng là: tăng độ tin tưởng, tối ưu hóa các chi
                                                phí trong quá trình sử dụng, đảm bảo an toàn, bảo vệ môi trường và thực
                                                hiện trách nhiệm với cán bộ, công nhân viên và xã hội. Để đạt được mục
                                                tiêu này, các nhà máy cần phải tìm ra cho mình được phương án bảo dưỡng
                                                máy móc phù hợp, hiệu quả và an toàn.
                                            </li>
                                        </ul>
                                    </li>
                                    <li className={cx('content-item')}>
                                        <ul className={cx('sup-list')}>
                                            <span className={cx('sup-title')}>
                                                &#8658; Lựa chọn hình thức bảo trì thích hợp với từng loại thiết bị
                                            </span>
                                            <li className={cx('sup-item')}>
                                                &#9594; Với thiết bị sống còn: là những thiết bị không thể thiếu trong
                                                quá làm việc, hoạt động của nhà máy, quyết định đến sản lượng, độ an
                                                toàn và chất lượng của sản phẩm. Với loại thiết bị này, bạn cần bảo
                                                dưỡng định kỳ hàng tháng, hàng quý, hàng năm. Ngoài ra, có thể dựa vào
                                                tình trạng của máy móc để bảo trì, chẳng hạn như theo dõi nhiệt độ, chất
                                                lượng sản phẩm, tiếng ồn, độ rung lắc trong quá trình hoạt động,….
                                            </li>
                                            <li className={cx('sup-item')}>
                                                &#9594; Thiết bị quan trọng: là những loại thiết bị ảnh hưởng trực tiếp
                                                đến dây chuyền sản xuất những có dự phòng hoặc thiết bị được đầu tư với
                                                số vốn lớn. Các thiết bị này sẽ được bảo dưỡng dựa trên tình trạng của
                                                máy. Nếu thấy có bất kỳ dấu hiệu hư hỏng nào cần lên kế hoạch để sửa
                                                chữa kịp thời, phù hợp.
                                            </li>
                                            <li className={cx('sup-item')}>
                                                &#9594; Thiết bị phụ trợ: là thiết bị không thật sự quá cần thiết với
                                                hoạt động sản xuất. Bạn có thể lựa chọn hình thức sửa chữa để phục hồi
                                                hoặc sửa chữa khi gặp sự cố hư hỏng. Với những thiết bị có chi phí sửa
                                                chữa cao bên nên đưa vào mục cần bảo trì định kỳ.
                                            </li>
                                        </ul>
                                    </li>
                                    <li className={cx('content-item')}>
                                        <ul className={cx('sup-list')}>
                                            <span className={cx('sup-title')}>
                                                &#8658; 3 cơ cấu tổ chức cần có trong hoạt động bảo dưỡng
                                            </span>
                                            <li className={cx('sup-item')}>
                                                &#9594; Bộ phận lập kế hoạch: gồm các kỹ sư giàu kinh nghiệm trong việc
                                                lên kế hoạch vật tư, bảo dưỡng định kỳ. Đồng thời, thường xuyên kiểm tra
                                                các trang thiết bị, máy móc để có phương án bảo dưỡng tốt nhất nhằm đảm
                                                bảo an toàn cho toàn nhà máy.
                                            </li>
                                            <li className={cx('sup-item')}>
                                                &#9594; Bộ phận thực thi: gồm các kỹ sư, công nhân trực tiếp bảo dưỡng,
                                                sửa chữa các thiết bị, máy móc trong nhà máy.
                                            </li>
                                            <li className={cx('sup-item')}>
                                                &#9594; Xây dựng quy trình sửa chữa, bảo dưỡng: cần nêu cụ thể các bước
                                                triển khai công việc bảo dưỡng, sửa chữa, người thực hiện, người giám
                                                sát, thống kê,…
                                            </li>
                                        </ul>
                                    </li>
                                    <li className={cx('content-item')}>
                                        <ul className={cx('sup-list')}>
                                            <span className={cx('sup-title')}>
                                                &#8658; Lên kế hoạch bảo trì định kỳ thiết bị
                                            </span>
                                            <li className={cx('sup-item')}>
                                                &#9594; Bạn cần lập kế hoạch cho các thiết bị sống còn và thiết bị quan
                                                trọng các loại hình bảo dưỡng phù hợp, có thể là đại tu, trùng tu hay
                                                tiểu tu. Việc lựa chọn loại hình bảo dưỡng thường phụ thuộc vào nhiều
                                                yếu tố. Chẳng hạn như: số giờ vận hành máy, thời gian bảo dưỡng trước đó
                                                là bao lâu, tình hình hoạt động thực tế của máy, khuyến cáo bảo dưỡng
                                                của nhà sản xuất,…
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div className={cx('content')}>
                            <h2 className={cx('title-body')}>Thông tin thiết bị {data.name}</h2>
                            <Table className={cx('table')} responsive>
                                <tbody>
                                    <tr className={cx('table-item')}>
                                        <td className={cx('text-center')}>Tên thiết bị:</td>
                                        <td>{data.name}</td>
                                    </tr>
                                    <tr className={cx('table-item')}>
                                        <td className={cx('text-center')}>Mã thiết bị:</td>
                                        <td>{data.code}</td>
                                    </tr>
                                    <tr className={cx('table-item')}>
                                        <td className={cx('text-center')}>Loại thiết bị:</td>
                                        <td>{data.type}</td>
                                    </tr>
                                    <tr className={cx('table-item')}>
                                        <td className={cx('text-center')}>Nhà cung cấp:</td>
                                        <td>{data.manufacturer}</td>
                                    </tr>
                                    <tr className={cx('table-item')}>
                                        <td className={cx('text-center')}>Chi tiết thiết bị:</td>
                                        <td>{data.description}</td>
                                    </tr>
                                    <tr className={cx('table-item')}>
                                        <td className={cx('text-center')}>Giá thiết bị:</td>
                                        <td className={cx('price')}>{formatPrice(data.price)}</td>
                                    </tr>
                                </tbody>
                            </Table>
                        </div>
                        <div className={classNames(cx('content'), cx('mt-5'))}>
                            <Row>
                                <Col className={cx('benefit')} xs={12} md={5} lg={5}>
                                    <div className={cx('header-benefit')}>
                                        Tại sao nên chọn <span className={cx('title-benefit')}>đại lý</span> chúng tôi
                                    </div>
                                    <div className={cx('icon-benefit')}>
                                        <FontAwesomeIcon className={'icon-global'} icon={faEarthAsia} />
                                    </div>
                                    <div className={cx('menu-benefit')}>
                                        <p className={cx('item-benefit')}>
                                            <FontAwesomeIcon className={cx('icon-checkbox')} icon={faSquareCheck} />
                                            Thiết bị được đảm bảo giá tốt
                                        </p>
                                        <p className={cx('item-benefit')}>
                                            <FontAwesomeIcon className={cx('icon-checkbox')} icon={faSquareCheck} />
                                            Giải pháp kiểm định chất lượng tốt
                                        </p>
                                        <p className={cx('item-benefit')}>
                                            <FontAwesomeIcon className={cx('icon-checkbox')} icon={faSquareCheck} />
                                            Đội ngũ nhân viên tận tình
                                        </p>
                                        <p className={cx('item-benefit')}>
                                            <FontAwesomeIcon className={cx('icon-checkbox')} icon={faSquareCheck} />
                                            Đội ngũ kĩ thuật giàu kinh nghiệm
                                        </p>
                                        <p className={cx('item-benefit')}>
                                            <FontAwesomeIcon className={cx('icon-checkbox')} icon={faSquareCheck} />
                                            Thiết bị tốt, giao hàng nhanh
                                        </p>
                                        <p className={cx('item-benefit')}>
                                            <FontAwesomeIcon className={cx('icon-checkbox')} icon={faSquareCheck} />
                                            Bảo hành, bảo trì nhanh, uy tín
                                        </p>
                                    </div>
                                </Col>
                                <Col xs={12} md={7} lg={7}>
                                    <div className={cx('info-payment')}>
                                        <span className={cx('title-payment')}>
                                            Để đảm bảo chất lượng thiết bị và mua hàng của khách hàng mọi thắc mắc liên
                                            hệ với chúng tôi:
                                        </span>
                                        <div className={cx('icon-benefit')}>
                                            <FontAwesomeIcon className={'icon-global'} icon={faAddressBook} />
                                        </div>
                                        <div className={cx('ms-4')}>
                                            <div className={cx('phone')}>
                                                <FontAwesomeIcon className={cx('icon-phone')} icon={faPhone} />
                                                Điện thoại: <span> 9999999999</span>
                                            </div>
                                            <span className={cx('email')}>
                                                <FontAwesomeIcon className={cx('icon-email')} icon={faEnvelope} />
                                                Email: baotrithietvn@gmail.com
                                            </span>
                                            {user ? (
                                                <Button
                                                    onClick={() => {
                                                        navigate('/payment', { state: { data } });
                                                    }}
                                                    className={cx('pay-btn')}
                                                >
                                                    Đặt hàng
                                                </Button>
                                            ) : (
                                                <div className={cx('phone')}>
                                                    <Button
                                                        onClick={handleShowLogin}
                                                        className={cx('menu-item', cx('btn-login'))}
                                                    >
                                                        Đăng nhập
                                                    </Button>
                                                    để đặt hàng.
                                                </div>
                                            )}
                                        </div>
                                    </div>
                                </Col>
                            </Row>
                        </div>
                    </div>
                </div>
                <Login show={showLoginModal} handleClose={handleCloseLogin} />
            </Wrapper>
            {/* Modal hiển thị ảnh */}
            {isModalOpen && (
                <div className={cx('modal-overlay')} onClick={handleCloseModal}>
                    <div className={cx('modal-content')} onClick={(e) => e.stopPropagation()}>
                        <img className={cx('modal-image')} src={imageUrl} alt={data.name} />
                        <button className={cx('modal-close')} onClick={handleCloseModal}>
                            Đóng
                        </button>
                    </div>
                </div>
            )}
        </>
    );
}

DetailDeviceItem.propTypes = {
    data: PropTypes.oneOfType([PropTypes.object, PropTypes.array]).isRequired,
};

export default memo(DetailDeviceItem);
