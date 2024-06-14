import axios from 'axios';

const API_URL = 'http://127.0.0.1:8000/'; // URL de tu backend Django

const productService = {

  getFilteredProducts(name = null, maxPrice = null, minPrice = null, supermarketId = null, providerId = null) {
    let params = { 
      name: name, 
      maxPrice: maxPrice, 
      minPrice: minPrice, 
      idSupermercado_id: supermarketId, 
      idProvider_id: providerId 
    }
    return axios.get(`${API_URL}producto/`, { params });
  },

  getAllProducts() {
    return axios.get(`${API_URL}producto/`);
  },

  getTopProducts() {
    return axios.get(`${API_URL}producto/popular`);
  },

   getProductById(productId) {
    return  axios.get(`${API_URL}producto/${productId}/`);
  },

  createProduct(productData) {
    let product = {
      name: productData.name,
      description: productData.description,
      price: productData.price,
      idSupermercado: productData.supermarketId,
      idProvider: productData.providerId,
      stock: productData.stock
    }
    return axios.post(`${API_URL}producto/`, product);
  },

  updateProduct(productId, productData) {
    let product = {
      id: productId,
      name: productData.name,
      description: productData.description,
      price: productData.price,
      idSupermercado: productData.supermarketId,
      idProvider: productData.providerId,
      stock: productData.stock,
    }
    return axios.put(`${API_URL}producto/${productId}/`, product);
  },

  deleteProduct(productId) {
    return axios.delete(`${API_URL}producto/${productId}/`);
  }
};

export default productService;
