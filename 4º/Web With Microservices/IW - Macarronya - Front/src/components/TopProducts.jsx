import React from "react";
import ProductCard from "./ProductCard";
import { useState, useEffect } from "react";
import productService from "../services/productService";
import Loading from "./Loading";


export default function TopSupermarkets() {
    const [products, setProducts] = useState([]);
    var [loading, setLoading] = useState(true);

    useEffect(() => {
        productService.getTopProducts().then((response) => {
            setProducts(response.data);
            setLoading(false);
        }).catch((error) => {
            alert("Error al cargar los productos:", error);
            console.error(error)
        });
    }, []);

    return (
        <>
            <h2 >Top Products</h2>
            <div className="products-list">

            {loading ? <Loading /> : products.map((product) => (
                <ProductCard 
                    key={product.pk}
                    product={product}
                />
            ))}
            </div>
        </>
    );
}
