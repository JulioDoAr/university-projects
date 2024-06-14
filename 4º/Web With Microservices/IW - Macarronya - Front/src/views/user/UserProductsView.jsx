import './UserProductsView.css';
import React from "react";
import { useState, useEffect } from "react";
import ProductFilter from "../../components/ProductFilter";
import productService from "../../services/productService";
import Loading from "../../components/Loading";
import ProductsList from '../../components/ProductsList';

/**
 * 
 * @returns Vista de los filtros de productos y productos que cumplan con los filtros
 */
export default function UserProductsView() {

    // Lista de productos que mostraremos
    var [products, setProducts] = useState([]);
    // Variable que indica si se están cargando los productos
    var [loading, setLoading] = useState(true);

    useEffect(() => {
        // Parametros cargados de la url para cargar los productos que cumplan con los filtros
        const queryParameters = new URLSearchParams(window.location.search)
        const name = queryParameters.get("name");
        const maxPrice = queryParameters.get("maxPrice");
        const minPrice = queryParameters.get("minPrice");
        const supermarketId = queryParameters.get("supermarketId");

        // Obtenemos los productos que cumplan con los filtros
        productService.getFilteredProducts(name, maxPrice, minPrice, supermarketId).then((response) => {
            // Actualizamos el estado de los productos
            setProducts(response.data);
            // Dejamos de mostrar el iconito de carga
            setLoading(false);
        }).catch((error) => {
            // En caso de error, tiramos el eror por consola y no dejamos de cargar
            alert("Error al cargar los productos:", error);
            console.error(error)
        });
    }, []);


    return (
        <div>
            <h1>User Products View</h1>
            <ProductFilter />
            <br />
            <div >
                {loading ? <Loading /> : <ProductsList products={products} showAddToCart={true} />}
            </div>
        </div>
    );
}

