import React from "react";
import { useEffect, useState } from "react";
import SupermarketFilter from "../../components/SupermarketFilter";
import SupermarketList from "../../components/SupermarketList";
import Loading from "../../components/Loading";
import supermarketService from "../../services/supermarketService";
import './UserSupermarketsView.css';


/**
 * 
 * @returns Vista de los filtros de supermercados y supermercados que cumplan con los filtros
 */
export default function UserSupermarketView() {

    // Lista de supermercados que mostraremos
    var [supermercados, setSupermercados] = useState([]);
    // Variable que indica si se están cargando los supermercados
    var [loading, setLoading] = useState(true);

    useEffect(() => {
        // Parametros cargados de la url para cargar los supermercados que cumplan con los filtros
        const queryParameters = new URLSearchParams(window.location.search)
        const name = queryParameters.get("name");
        const address = queryParameters.get("address");

        // Obtenemos los supermercados que cumplan con los filtros
        supermarketService.getFilteredSuper(name, address).then((response) => {
            // Actualizamos el estado de los supermercados
            setSupermercados(response.data);
            // Dejamos de mostrar el iconito de carga
            setLoading(false);
        }).catch((error) => {
            // En caso de error, tiramos el eror por consola y no dejamos de cargar
            alert("Error al cargar los supermercados:", error);
            console.error(error)
        });
    }, []);


    return (
        <div>
            <h1>User Supermarket View</h1>
            <br />
            <SupermarketFilter />
            <br />
            
            {loading ? <Loading /> : <SupermarketList supermarkets={supermercados} />}

            
        </div>
    );
}