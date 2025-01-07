import axios from 'axios';
import cookie from 'react-cookies';

const BASE_URL = 'http://localhost:8080/bamboo/api/';

export const endpoints = {
    categories: '/categories',
    equipments: '/equipments',
    equipmentimages: '/equipmentimages',
    services: '/services',
    login: '/login',
    'current-user': '/current-user',
    register: '/users',
    updateuser: (userId) => `/user-update/${userId}`,
    changepassword: '/change-password',
    sendemail: '/send-email',
    forums: '/forums',
    momo: '/process',
    cash: '/invoice',
    invoice: (userId) => `/invoice/${userId}`,
    invoicedetail: (invoiceId) => `/invoicedetail/${invoiceId}`,
};

export const authAPIs = () => {
    const token = cookie.load('access-token');
    return axios.create({
        baseURL: BASE_URL,
        headers: {
            Authorization: token,
        },
    });
};

export default axios.create({
    baseURL: BASE_URL,
});
