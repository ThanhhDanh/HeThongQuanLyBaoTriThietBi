import classNames from 'classnames/bind';
import styles from './Home.module.scss';

import Content from '~/components/bases/Content';
import ContentItem from '~/common/ContentItem';
import FooterBody from '~/components/bases/Footer/FooterBody';

const cx = classNames.bind(styles);

function Home() {
    return (
        <>
            <div className={cx('container')}>
                <div className={cx('inner')}>
                    <Content>
                        <ContentItem />
                    </Content>
                </div>
                <FooterBody />
            </div>
        </>
    );
}

export default Home;
