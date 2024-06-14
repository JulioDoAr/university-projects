import axios from "axios";

const API_URL = 'http://127.0.0.1:8000/'; // URL de tu backend Django

const providerService = {
    getAllProvider() {
        return axios.get(`${API_URL}proveedor/`);
    },

    getProviderById(providerId) {
        return axios.get(`${API_URL}proveedor/${providerId}/`);
    },

    createProvider(providerData) {
        let provider = { name: providerData.name,  phone: providerData.description}
        return axios.post(`${API_URL}proveedor/`, provider);
    },

    updateProvider(providerId, providerData) {
        let provider = {id: providerId, name: providerData.name, address: providerData.address, phone: providerData.phone}
        return axios.put(`${API_URL}proveedor/${providerId}/`, provider);
    },

    deleteProvider(superId) {
        return axios.delete(`${API_URL}proveedor/${superId}/`);
    }
};

export default providerService;




















