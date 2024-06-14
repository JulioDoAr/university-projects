import ProductCard from "./ProductCard";
import React from "react"
export function ProductList({ productList }) {

    return (
        <>
            <section className="container">
                {productList.map((p, index) => (<ProductCard key={index} product={p} />))}
            </section>
        </>
    )
}