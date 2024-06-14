import axios from "axios";

const API_URL = 'http://127.0.0.1:8000/'; // URL de tu backend Django

const supermarketService = {
    getAllSuper() {
        return axios.get(`${API_URL}supermercado/`);
    },

    getFilteredSuper(name = null, address = null) {
        let params = { name: name, address: address }
        return axios.get(`${API_URL}supermercado/`, { params });
    },

    getTopSuper() {
        return axios.get(`${API_URL}supermercado/popular`);
    },

    getSuperById(superId) {
        return axios.get(`${API_URL}supermercado/${superId}/`);
    },

    createSuper(superData) {
        let supermarket = { name: superData.name, address: superData.address, phone: superData.phone}
        return axios.post(`${API_URL}supermercado/`, supermarket);
    },

    updateSuper(superId, superData) {
        let supermarket = {id: superId, name: superData.name, address: superData.address, phone: superData.phone}
        return axios.put(`${API_URL}supermercado/${superId}/`, supermarket);
    },

    deleteSuper(superId) {
        return axios.delete(`${API_URL}supermercado/${superId}/`);
    }
};

export default supermarketService;




















