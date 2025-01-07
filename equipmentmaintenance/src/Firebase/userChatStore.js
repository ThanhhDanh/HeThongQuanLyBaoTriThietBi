import { create } from 'zustand';

const useChatStore = create((set) => ({
    chatId: null,
    changeChat: (id, user) => set({ chatId: id }),
}));

export default useChatStore;
