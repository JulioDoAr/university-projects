import React, { useEffect, useState } from "react";
import supermarketService from "../services/supermarketService";

/**
 * 
 * @returns Filtro de productos. Permite filtrar por nombre, precio máximo, precio mínimo y supermercado
 */
export default function ProductFilter() {

    // Declaramos los valores iniciales de los campos del formulario como estados, ya que cambiarán
    const [name, setName] = useState("");
    const [maxPrice, setMaxPrice] = useState("");
    const [minPrice, setMinPrice] = useState("");
    const [supermarkets, setSupermarkets] = useState([]);
    const [supermarketId, setSupermarketId] = useState("");

    const handleSubmit = (event) => {
        // Cargamos los parametros del formulario que se envió
        event.preventDefault();
        const name = event.target.elements.name.value;
        const maxPrice = event.target.elements.maxPrice.value;
        const minPrice = event.target.elements.minPrice.value;
        const supermarketId = event.target.elements.supermarketId.value;

        // En caso de tener que hacer validaciones extra, irian aqui

        // Creamos un objeto URLSearchParams para poder manipular los parametros de la URL
        const queryParams = new URLSearchParams()
        if (name)
            queryParams.append("name", name);
        if (maxPrice)
            queryParams.append("maxPrice", maxPrice);
        if (minPrice)
            queryParams.append("minPrice", minPrice);
        if (supermarketId)
            queryParams.append("supermarketId", supermarketId);

        // Redirigimos a la misma página con los parametros de la URL actualizados
        window.location.href = `${window.location.pathname}?${queryParams.toString()}`;
    }

    // Cargamos los parametros de la URL en los campos del formulario para tenerlos inicializados
    useEffect(() => {
        const queryParameters = new URLSearchParams(window.location.search)
        setName(queryParameters.get("name"));
        setMaxPrice(queryParameters.get("maxPrice"));
        setMinPrice(queryParameters.get("minPrice"));
        setSupermarketId(queryParameters.get("supermarketId"));

        supermarketService.getAllSuper().then((response) => {
            setSupermarkets(response.data);
        }).catch((error) => {
            alert("Error al cargar los supermercados:", error);
            console.error(error)
        });
    }, []);

    return (
        <div className="product-filter">
            <h2>Filter</h2>
            <form onSubmit={handleSubmit} className="product-filter-form form-group">

                <div class="form-group">
                    <label for="supermarketId">Supermarket</label>
                    <select id="supermarketId" className="form-control">
                        <option value="">All</option>
                        {supermarkets.map((supermarket, index) => (
                            <option key={index} value={supermarket.pk} selected={supermarket.pk === supermarketId ? true : false}>{supermarket.name}</option>
                        ))}
                    </select>
                </div>

                <div class="form-group">
                    <label for="name">Name</label>
                    <input id="name" type="text" className="form-control" name="name" placeholder="Filter by name" defaultValue={name} />
                </div>

                <div className="row">
                    <div class="form-group col-6">
                        <label for="maxPrice">Name</label>
                        <input id="maxPrice" type="number" className="form-control" name="maxPrice" placeholder="Max price" defaultValue={maxPrice} />
                    </div>

                    <div class="form-group col-6">
                        <label for="minPrice">Name</label>
                        <input id="minPrice" type="number" className="form-control" name="minPrice" placeholder="Min price" defaultValue={minPrice} />
                    </div>
                </div>

                <input type="submit" className="btn btn-primary" value="Filter" />
            </form>
        </div>
    );
}