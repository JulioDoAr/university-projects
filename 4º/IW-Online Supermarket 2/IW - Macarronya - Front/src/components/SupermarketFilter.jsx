import React from "react";
import { useEffect, useState } from "react";

/**
 * 
 * @returns Filtro de supermercados. Permite filtrar por nombre, precio máximo, precio mínimo y supermercado
 */
export default function SupermarketFilter() {

    // Declaramos los valores iniciales de los campos del formulario como estados, ya que cambiarán
    const [name, setName] = useState("");
    const [address, setAddress] = useState("");

    const handleSubmit = (event) => {
        // Cargamos los parametros del formulario que se envió
        event.preventDefault();
        const name = event.target.elements.name ? event.target.elements.name.value : null;
        const address = event.target.elements.address ? event.target.elements.address.value : null;

        // En caso de tener que hacer validaciones extra, irian aqui

        // Creamos un objeto URLSearchParams para poder manipular los parametros de la URL
        const queryParams = new URLSearchParams();
        if (name) {
            queryParams.append("name", name);
        }
        if (address) {
            queryParams.append("address", address);
        }

        // Redirigimos a la misma página con los parametros de la URL actualizados
        window.location.href = `${window.location.pathname}?${queryParams.toString()}`;
    }

    // Cargamos los parametros de la URL en los campos del formulario para tenerlos inicializados
    useEffect(() => {
        const queryParameters = new URLSearchParams(window.location.search)
        setName(queryParameters.get("name"));
        setAddress(queryParameters.get("address"));
    }, []);

    return (
        <div>
            <h2>Filter</h2>
            <form onSubmit={handleSubmit} className=" form-group">
                <div class="form-group">
                    <label for="name">Name</label>
                    <input id="name" type="text" className="form-control" name="name" placeholder="Name" defaultValue={name} />

                </div>

                <div class="form-group">
                    <label for="address">Addres</label>
                    <input id="address" type="text" className="form-control" name="address" placeholder="Address" defaultValue={address} />
                </div>

                <input type="submit" className="btn btn-primary" value="Filter" />
            </form>
        </div>
    );
}