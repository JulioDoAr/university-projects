import React from "react"
import { useState, useEffect } from 'react';
import supermarketService from "../../services/supermarketService";
import { SupermarketListAdmin } from "./SupermarketListAdmin";
import SupermarketFilter from "../SupermarketFilter";
import { useLocation } from "react-router-dom";
import { useParams } from "react-router-dom";
export function AdminSupermarketForm() {

    const [supermarkets, setSupermarkets] = useState([])
    const handleSubmit = (data) => {//A través de esta función, se consiguen los datos y estado del formulario.
        let supermarket =
        {
            name: data.target.nombre.value,
            address: data.target.address.value,
            phone: data.target.telefono.value
        }
            supermarketService.createSuper(supermarket)
    }

    useEffect(() => {
        let URLParams = new URLSearchParams(window.location.search)
        let address = URLParams.get('address')
        let name = URLParams.get("name")
        if (window.location.search) {
            supermarketService.getFilteredSuper(name,address ).then((response) => {
                setSupermarkets(response.data)
                })
        }
        else{
            supermarketService.getAllSuper().then(res => {
            setSupermarkets(res.data)
        })
        }
        
    }, [])

    return (
        <>
            <form onSubmit={handleSubmit} className="form-control">
                <label htmlFor="nombre">Nombre del supermercado:</label>
                <input type="text" className="form-control" name="nombre" id="nombre"  required />

                <label htmlFor="telefono">Telefono del supermercado</label>
                <input type="number" className="form-control" name="telefono" id="telefono"  required />

                <label htmlFor="address">Address del supermercado</label>
                <input type="text" className="form-control" name="address" id="address"  required />

                <button className="btn btn-success" type="submit" >Crear supermercado</button>
                <button className="btn btn-danger" type="reset">Limpiar</button>
            </form>

            <SupermarketFilter />

            <SupermarketListAdmin supermarkets={supermarkets} />
        </>
    )
}