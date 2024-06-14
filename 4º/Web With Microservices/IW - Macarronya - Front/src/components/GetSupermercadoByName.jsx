import React, { useState } from "react";
import supermarketService from "../services/supermarketService";

export default function GetSupermercadoByName() {
    const [SupermercadoName, setSupermercadoName] = useState('');
    const [Supermercados, setSupermercados] = useState([]);

    const handleSubmit = async (e) => {
        supermarketService.getSuperByName(SupermercadoName)
            .then((response) => {
                setSupermercados(response.data);
            }).catch((error) => {
                alert("Error al cargar los supermercados:", error);
                console.error(error)
            });
    };

    return (
        <div>
            <h2>Consultar Supermercado por Nombre</h2>
            <form onSubmit={handleSubmit}>
                <label>Nombre del Supermercadoo:</label>
                <input type="text" value={SupermercadoName} onChange={(e) => setSupermercadoName(e.target.value)} />
                <button type="submit">Consultar Supermercado</button>
            </form>
            {Supermercados.length > 0 ? (
                <div>
                    <h3>Supermercadoos Encontrados:</h3>
                    {Supermercados.map((Supermercado, index) => (
                        <div key={index}>
                            <p>Nombre: {Supermercado.name}</p>
                            <p>Precio: {Supermercado.price}</p>
                        </div>
                    ))}
                </div>
            ) : (
                <p>No se encontraron Supermercados.</p>
            )}
        </div>
    );
}
