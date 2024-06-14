import React from "react"
import { useState, useEffect } from 'react';
import productService from "../../services/productService";
import providerService from "../../services/providerService";
import supermarketService from "../../services/supermarketService";
import ProductFilter from "../ProductFilter";
import { ProductList } from "../ProductList";
import { useParams } from "react-router-dom";
import { ListProductAdmin } from "./ListProductAdmin";
export function AdminProductForm() {

    const [supermarkets, setSupermarkets] = useState([])
    const [providers, setProviders] = useState([])
    const [products, setProducts] = useState([])
    

    const handleSubmit = (data) => {
        
        const product = {
            "name": data.target.nombre.value,
            "description": data.target.descripcion.value,
            "price": data.target.precioProducto.value,
            "supermarketId": data.target.supermarket.value,
            "providerId": data.target.provider.value,
            "stock": data.target.stock.value
            
        }
       
       
        productService.createProduct(product)
            
       
    }

    useEffect(() => {
        if(window.location.search){
        let URLParams = new URLSearchParams(window.location.search)
        let supermarketId = URLParams.get('supermarketId')
        let name = URLParams.get("name")
        let maxPrice = URLParams.get("maxPrice")
        let minPrice = URLParams.get("minPrice")
        
            productService.getFilteredProducts(name,maxPrice,minPrice,supermarketId)
                .then( res => setProducts(res.data))
        }
        else{
            productService.getAllProducts().then(res => {
            setProducts(res.data)
        })
        }
        supermarketService.getAllSuper().then(res => {
            setSupermarkets(res.data)
        })
        providerService.getAllProvider().then(res => {
            setProviders(res.data)
        })
        
    }, [])

    return (
        <>
            <form onSubmit={handleSubmit} className="form-control">
                <label htmlFor="nombre">Nombre del producto:</label>
                <input type="text" className="form-control" name="nombre" id="nombre" required />

                <label htmlFor="precioProducto">Precio del producto</label>
                <input type="number" className="form-control" name="precioProducto" id="precioProducto" required />

                <label htmlFor="descripcion">Descripción del producto</label>
                <input type="text" className="form-control" name="descripcion" id="descripcion" required />

                <label htmlFor="stock">Stock del producto</label>
                <input type="number" className="form-control" name="stock" id="stock" required />

                <label htmlFor="supermarket">Supermeracado del producto</label>
                <select name="supermarket" className="form-control" id="supermarket" required>
                    {supermarkets.map((market) => <option value={market.pk}>{market.name}</option>)}
                </select>

                <label htmlFor="stock">Proveedor del producto</label>
                <select name="provider" className="form-control" id="provider" required>
                    {providers.map((provider) => <option value={provider.pk}>{provider.name}</option>)}
                </select>

                <button className="btn btn-success" type="submit" >Crear producto</button>
                <button className="btn btn-danger" type="reset">Limpiar</button>
            </form>

            <ProductFilter />

            <ListProductAdmin products={products}/>
        </>
    )
}