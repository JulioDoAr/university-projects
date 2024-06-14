import React from "react";
import './SupermarketCard.css';

export default function SupermarketCard({ supermarket }) {


    const verProductos = () => {
        const url = `${window.location.origin}/user/products?supermarketId=${supermarket.pk}`;
        window.location.href = url;
    };
    const verDetalles = () => {
        const url = `${window.location.origin}/supermarket/${supermarket.pk}`;
        window.location.href = url;
    };

    return (
        <div className="supermarket-card">
            <p className="supermarket-card__name">{supermarket.name}</p>
            <p className="supermarket-card__phone">{supermarket.phone}</p>
            <p className="supermarket-card__address">{supermarket.address}</p>
            <button className="btn btn-primary" onClick={verProductos}>Ver productos</button>
            <button className="btn btn-primary" onClick={verDetalles}>Ver detalles</button>

        </div>
    );
}