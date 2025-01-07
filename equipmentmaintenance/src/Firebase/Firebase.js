// Import the functions you need from the SDKs you need
import { initializeApp } from 'firebase/app';
import { getAnalytics } from 'firebase/analytics';
import { EmailAuthProvider, getAuth, reauthenticateWithCredential, updatePassword } from 'firebase/auth';
import { getFirestore } from 'firebase/firestore';
import { getStorage } from 'firebase/storage';
import { getMessaging } from 'firebase/messaging';

const firebaseConfig = {
    apiKey: process.env.REACT_APP_VITE_API_KEY,
    authDomain: 'webequipment-f14a6.firebaseapp.com',
    projectId: 'webequipment-f14a6',
    storageBucket: 'webequipment-f14a6.appspot.com',
    messagingSenderId: '728983148112',
    appId: '1:728983148112:web:afb16dfb74ae62de8be355',
    measurementId: 'G-KY16WP2P73',
};

// Xác thực lại người dùng trước khi thay đổi mật khẩu
export const reauthenticate = async (currentPassword) => {
    const user = auth.currentUser;
    const credential = EmailAuthProvider.credential(user.email, currentPassword);

    try {
        await reauthenticateWithCredential(user, credential);
        console.log('Xác thực thành công');
    } catch (error) {
        console.error('Lỗi xác thực lại người dùng:', error);
    }
};

// Cập nhật mật khẩu mới
export const changePasswordOnFirebase = async (newPassword) => {
    const user = auth.currentUser;
    try {
        await updatePassword(user, newPassword);
        console.log('Mật khẩu đã được cập nhật trên Firebase');
    } catch (error) {
        console.error('Lỗi cập nhật mật khẩu trên Firebase:', error);
    }
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);

export const auth = getAuth();
export const db = getFirestore();
export const storage = getStorage();
export const messaging = getMessaging(app);
