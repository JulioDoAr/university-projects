import React from "react";
import SupermarketCard from "./SupermarketCard";
import { useState, useEffect } from "react";
import supermarketService from "../services/supermarketService";
import Loading from "./Loading";

export default function TopSupermarkets() {
    const [supermarkets, setSupermarkets] = useState([]);
    var [loading, setLoading] = useState(true);

    useEffect(() => {
        supermarketService.getTopSuper().then((response) => {
            setSupermarkets(response.data);
            setLoading(false);
        }).catch((error) => {
            alert("Error al cargar los supermercados:", error);
            console.error(error)
        });
    }, []);

    return (
        <>
            <h2>Top Supermarkets</h2>
            {loading ? <Loading /> : supermarkets.map((supermarket) => (
                <SupermarketCard
                    key={supermarket.pk}
                    supermarket={supermarket}
                />
            ))}
        </>
    );
}
