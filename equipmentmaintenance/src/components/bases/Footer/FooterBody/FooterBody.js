import classNames from 'classnames/bind';
import styles from './FooterBody.module.scss';
import Services, { ServiceItem } from '~/components/bases/Services';

const cx = classNames.bind(styles);

function FooterBody() {
    return (
        <>
            <Services>
                <ServiceItem />
            </Services>
        </>
    );
}

export default FooterBody;
