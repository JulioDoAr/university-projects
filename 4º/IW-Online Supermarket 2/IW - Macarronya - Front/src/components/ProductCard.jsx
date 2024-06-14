import cartItemService from '../services/cartItemService';
import './ProductCard.css';
import React from "react";

function addToCart(product) {
    cartItemService.addItemToCart(product.pk)
        .then((response) => {
            alert("Producto agregado al carrito");
            window.location.reload();
        }).catch((error) => {
            alert("Error al agregar el producto al carrito");
            console.log(error);
        });
}
function reduceItemToCart(product) {
    cartItemService.reduceItemToCart(product.pk)
        .then((response) => {
            if (response.status === 201) {
                alert("Producto eliminado del carrito");
            }
            if (response.status === 200) {
                alert("Producto reducido en el carrito");
            }
            window.location.reload();
        }).catch((error) => {
            alert("Error al eliminar el producto del carrito");
            console.log(error);
        });
}

function verDetalles(product) {
    const url = `${window.location.origin}/product/${product.pk}`;
    window.location.href = url;
};

export default function ProductCard({ product, showAddToCart }) {
    return (
        <div className="product-card">
            <p className="product-card-name">{product.name}</p>
            <p className="product-card-description">{product.description}</p>
            <p className="product-card-price">{product.price}€</p>
            <p className="product-card-stock">{product.stock} unidades en stock</p>
            {showAddToCart ?
                <button className="btn btn-success" onClick={() => addToCart(product)}>
                    Agregar al carrito
                </button> : null}

            {showAddToCart ?
                <button className="btn btn-danger" onClick={() => reduceItemToCart(product)}>
                    Eliminar del carro
                </button> : null}
            <button className="btn btn-primary" onClick={() => verDetalles(product)}>Ver detalles</button>
        </div>
    );
}