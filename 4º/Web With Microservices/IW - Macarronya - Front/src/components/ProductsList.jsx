import React from "react";
import ProductCard from "./ProductCard";

export default function ProductsList({ products, showAddToCart = false }) {
    return (
        <div className="products-list">

            {products.map((product) => (
                <ProductCard key={product.pk} product={product} showAddToCart={showAddToCart} />
            ))}
        </div>
    );
}