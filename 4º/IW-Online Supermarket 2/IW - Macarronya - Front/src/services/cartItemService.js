import axios from "axios";

const API_URL = 'http://127.0.0.1:8000/'; // URL de tu backend Django

const cartItemService = {

    getItemById(userId = 1, productId) {
        return axios.get(`${API_URL}itemCarrito/${userId}/${productId}/`);
    },

    getItemByUser(userId) {
        return axios.get(`${API_URL}itemCarrito/user/${userId}/`);
    },

    addItemToCart(productId, quantity = 1, userId = 1) {
        console.log(productId, quantity, userId)
        let itemCart = {
            productId: productId,
            quantity: quantity,
            userId: userId
        }
        return axios.post(`${API_URL}itemCarrito/`, itemCart);
    },

    reduceItemToCart(productId, quantity = 1, userId = 1) {
        console.log(productId, quantity, userId)
        return axios.put(`${API_URL}itemCarrito/updateDown/${userId}/${productId}/`);
    },

    setValueToItemCart(productId, quantity = 1, userId = 1) {
        let itemCart = {
            productId: productId,
            quantity: quantity,
            userId: userId
        }
        return axios.put(`${API_URL}itemCarrito/`, itemCart);
    },

};

export default cartItemService;

