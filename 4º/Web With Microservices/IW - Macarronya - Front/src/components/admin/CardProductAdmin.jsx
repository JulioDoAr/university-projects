import { useNavigate } from "react-router-dom"
import productService from "../../services/productService"
export function CardProductAdmin({product}){
    const navigate = useNavigate()

    const handleDelete= async ()=>{
        productService.deleteProduct(product.pk)
        window.location.reload()
    }
    const handleEdit = ()=>{
        navigate(`/admin/products/${product.pk}`)
        
    }

    return(
        <div className="product-card">
            <p className="product-card-name">{product.name}</p>
            <p className="product-card-description">{product.description}</p>
            <p className="product-card-price">{product.price}€</p>
            <p className="product-card-stock">{product.stock} unidades en stock</p>
            <button className="btn btn-danger" onClick={handleDelete}>Eliminar</button>
            <button className="btn btn-primary" onClick={handleEdit}>Editar</button>
        </div>
    )
}