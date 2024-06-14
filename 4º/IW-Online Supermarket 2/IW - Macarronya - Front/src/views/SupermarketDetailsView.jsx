import React from "react";
import { useEffect, useState } from "react";
import SupermarketCard from "../components/SupermarketCard";
import supermarketService from "../services/supermarketService";
import Loading from "../components/Loading";
import { useParams } from "react-router-dom";

export default function SupermarketDetailsView() {
    const { id } = useParams();
    const [supermarket, setSupermarket] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        supermarketService.getSuperById(id)
            .then(response => {
                setSupermarket(response.data);
                setLoading(false);
            })
            .catch(error => {
                console.error('Error fetching supermarket:', error);
            });
    }, []);

    return (
        <div >
            {loading ? <Loading /> : <SupermarketCard supermarket={supermarket} />}
        </div>
    );
}
