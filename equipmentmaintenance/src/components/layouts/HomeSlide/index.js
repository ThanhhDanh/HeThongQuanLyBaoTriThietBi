import classNames from 'classnames/bind';
import PropTypes from 'prop-types';

//Hiểu ứng khi cuộn trang
import Aos from 'aos';
import 'aos/dist/aos.css';

import Slide, { SlideItem } from '~/common/Slides';
import image2 from '~/assets/images/slide2.png';
import image3 from '~/assets/images/slide3.webp';
import image5 from '~/assets/images/slide5.jpg';
import image6 from '~/assets/images/slide6.jpg';

import Header from '~/components/bases/Header';
import SideBar from '~/components/bases/SideBar';
import styles from '~/components/layouts/DefaultLayout.module.scss';

const cx = classNames.bind(styles);

function HomeSlide({ children }) {
    Aos.init();
    return (
        <div className={cx('wrapper')}>
            <Header />
            <div className={cx('container')}>
                <div className={cx('grid', 'ds-flex')}>
                    <div className={cx('row', 'no-gutters')}>
                        <div className={cx('c-12', 'l-o-3', 'm-o-3')}>
                            <SideBar />
                        </div>
                        <div data-aos="fade-left" data-aos-duration="1000" className={cx('c-12', 'l-o-9', 'm-o-9')}>
                            <Slide>
                                <SlideItem className={cx('slide-image')} src={image2} />
                                <SlideItem className={cx('slide-image')} src={image3} />
                                <SlideItem className={cx('slide-image')} src={image5} />
                                <SlideItem className={cx('slide-image')} src={image6} />
                            </Slide>
                        </div>
                    </div>
                </div>
                <div data-aos="fade-up" data-aos-duration="2000" className={cx('content')}>
                    {children}
                </div>
            </div>
        </div>
    );
}

HomeSlide.propTypes = {
    children: PropTypes.node.isRequired,
};

export default HomeSlide;
