import React from "react";
import loadingImage from "../assets/loading.gif";

export default function Loading() {
    return (
        <>
            <p>Cargando productos...
            </p>
            <img src={loadingImage} alt="loading" />
        </>
    );
}