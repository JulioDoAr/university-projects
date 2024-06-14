import axios from 'axios';

const API_URL = 'http://127.0.0.1:8000/'; // URL de tu backend Django

const userService = {

    getAllUsers(){
        return axios.get(`${API_URL}user/`);
    },

    getUserById (userId) {
        return axios.get(`${API_URL}user/${userId}/`);
    },

    createUser (userData) {
        let user = {
            name: userData.name,
            email: userData.email,
            document: userData.document,
            phone: userData.phone,
            registrationDate: userData.registrationDate
        }
        return axios.post(`${API_URL}user/`, user);
    },

    updateUser  (userId, userData) {
        let user = {
            id: userId,
            name: userData.name,
            email: userData.email,
            document: userData.document,
            phone: userData.phone,
            registrationDate: userData.registrationDate
        }
        return axios.put(`${API_URL}user/${userId}/`, user);
    },

    deleteUser (userId) {
        return axios.delete(`${API_URL}user/${userId}/`);
    },

};

export default userService;

