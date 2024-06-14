import React from "react";
import { useEffect, useState } from "react";
import Loading from "../components/Loading";
import { useParams } from "react-router-dom";
import productService from "../services/productService";
import ProductCard from "../components/ProductCard";


export default function ProductDetailsView() {
    const { id } = useParams();
    const [product, setProduct] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        productService.getProductById(id)
            .then(response => {
                setProduct(response.data);
                setLoading(false);
            })
            .catch(error => {
                console.error('Error fetching supermarket:', error);
            });
    }, []);

    return (
        <div >
            {loading ? <Loading /> : <ProductCard product={product} showAddToCart={true} />}
        </div>
    );
}
