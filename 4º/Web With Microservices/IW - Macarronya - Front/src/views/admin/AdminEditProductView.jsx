import { useNavigate, useParams } from "react-router-dom"
import productService from "../../services/productService"
import supermarketService from "../../services/supermarketService"
import providerService from "../../services/providerService"
import { useEffect, useState } from "react"

export function AdminEditProductView(){
    const {productId} = useParams()
    const [supermarkets, setSupermarkets] = useState([])
    const [providers, setProviders] = useState([])
    const [name, setName] = useState("")
    const [precioProducto, setPrecioProducto] = useState("")
    const [descripcion, setDescripcion] = useState("")
    const [stock, setStock] = useState("")
    const [supermarketId, setSupermarketId] = useState("")
    const [providerId, setProviderId] = useState("")
    const navigate = useNavigate()
    const handleSubmit = (data) => {
        
        const product = {
            "name": data.target.nombre.value,
            "description": data.target.descripcion.value,
            "price": data.target.precioProducto.value,
            "supermarketId": data.target.supermarket.value,
            "providerId": data.target.provider.value,
            "stock": data.target.stock.value
            
        }
       
       
        productService.updateProduct(productId, product)
            .then(navigate("/admin/products", {replace:true}))
        window.location.reload()
       
    }

    useEffect(()=>{

        productService.getProductById(productId).then((response) => {
            setDescripcion(response.data.description)
            setName(response.data.name)
            setPrecioProducto(response.data.price)
            setProviderId(response.data.idProvider)
            setStock(response.data.stock)
            setSupermarketId(response.data.idSupermercado)
            })
            supermarketService.getAllSuper().then(res => {
            setSupermarkets(res.data)
        })
        providerService.getAllProvider().then(res => {
            setProviders(res.data)
        })
    }, [productId])
    return(
        <>
            <form onSubmit={handleSubmit}>
                <label htmlFor="nombre">Nombre del producto:</label>
                <input type="text" name="nombre" id="nombre" defaultValue={name} required />

                <label htmlFor="precioProducto">Precio del producto</label>
                <input type="number" name="precioProducto" id="precioProducto" defaultValue={precioProducto} required />

                <label htmlFor="descripcion">Descripción del producto</label>
                <input type="text" name="descripcion" id="descripcion" defaultValue={descripcion} required />

                <label htmlFor="stock">Stock del producto</label>
                <input type="number" name="stock" id="stock" defaultValue={stock} required />

                <label htmlFor="supermarket">Supermeracado del producto</label>
                <select name="supermarket" id="supermarket" required>
                    {supermarkets.map((market) => <option selected={supermarketId===market.pk} value={market.pk}>{market.name}</option>)}
                </select>

                <label htmlFor="stock">Proveedor del producto</label>
                <select name="provider" id="provider" required>
                    {providers.map((provider) => <option selected={providerId===provider.pk} value={provider.pk}>{provider.name}</option>)}
                </select>

                <button type="submit" >Editar producto</button>
                <button type="reset">Limpiar</button>
            </form>
            
        </>
    )
}