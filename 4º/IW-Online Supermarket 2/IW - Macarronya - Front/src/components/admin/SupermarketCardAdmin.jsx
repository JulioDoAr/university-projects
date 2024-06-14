import { useNavigate } from "react-router-dom";
import supermarketService from "../../services/supermarketService";
export function SupermarketCardAdmin({supermarket}){
    const navigate = useNavigate();
    
    const handleDelete= ()=>{
        supermarketService.deleteSuper(supermarket.pk)
        window.location.reload()
    }
    const handleEdit = ()=>{
        navigate(`/admin/supermarket/${supermarket.pk}`)
        window.location.reload()
    }
    

    return (
        <div className="supermarket-card">
            <p className="supermarket-card__name">{supermarket.name}</p>
            <p className="supermarket-card__phone">{supermarket.phone}</p>
            <p className="supermarket-card__address">{supermarket.address}</p>
            <button className="btn btn-danger" onClick={handleDelete}>Eliminar</button>
            <button className="btn btn-primary" onClick={handleEdit}>Editar</button>
        </div>
    )
}