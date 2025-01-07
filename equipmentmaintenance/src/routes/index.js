//Layouts
import Tools from '~/pages/Tools';
import { config } from './routes';
import Home from '~/pages/Home';
import Devices from '~/pages/Devices';
import { HomeSlide, ProfileDefaultLayout } from '~/components/layouts';
import DetailDevice from '~/pages/DetailDevice';
import SupportChat from '~/components/Chat/SupportChat';
import AdminChat from '~/pages/AdminChat';
import AdminChatDefaultLayout from '~/components/layouts/AdminChatDefaultLayout';
import PaymentDefaultLayout from '~/components/layouts/PaymentDefaultLayout/PaymentDefaultLayout';
import Payment from '~/components/layouts/PaymentDefaultLayout/Payment';
import Profile from '~/pages/Profile';
import Services from '~/pages/Services';
import Forum from '~/pages/Forum';

//public routes
const publicRoutes = [
    { path: config.routes.home, component: Home, layout: HomeSlide },
    { path: config.routes.profile, component: Profile, layout: ProfileDefaultLayout },
    { path: config.routes.tools, component: Tools },
    { path: config.routes.devices, component: Devices },
    { path: config.routes.forum, component: Forum },
    { path: config.routes.service, component: Services },
    { path: config.routes.detaildevice, component: DetailDevice },
    { path: config.routes.chat, component: SupportChat },
    { path: config.routes.adminchat, component: AdminChat, layout: AdminChatDefaultLayout },
    { path: config.routes.payment, component: Payment, layout: PaymentDefaultLayout },
];

const privateRoutes = [];

export { publicRoutes, privateRoutes };
