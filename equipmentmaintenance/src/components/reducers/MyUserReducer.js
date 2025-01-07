import cookie from 'react-cookies';
import { auth } from '~/Firebase/Firebase';
import useUserStore from '~/Firebase/userStore';

const MyUserReducer = (currentState, action) => {
    // eslint-disable-next-line default-case
    switch (action.type) {
        case 'login':
            return action.payload;
        case 'logout':
            cookie.remove('access-token');
            cookie.remove('user');
            cookie.remove('selectedChatId');
            auth.signOut();
            useUserStore.getState().clearUser();

            return null;
    }
    return currentState;
};

export default MyUserReducer;
