import cookie from 'react-cookies';
import { create } from 'zustand';

const useUserStore = create((set) => ({
    useUser: cookie.load('user') || null,
    setCurrentUser: (user) => {
        set({ currentUser: user });
    },
    clearUser: () => set({ currentUser: null }),
}));

export default useUserStore;
