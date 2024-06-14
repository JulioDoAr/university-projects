import { useNavigate, useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import supermarketService from "../../services/supermarketService"

export function AdminEditSupermarketView(){

    const navigate = useNavigate()
    const [name, setName] = useState("")
    const [address, setAddress] = useState("")
    const [phone, setPhone] = useState("")
    let {idSupermarket} = useParams();
    
    const handleSubmit = (data) => {//A través de esta función, se consiguen los datos y estado del formulario.
        let superEdit =
        {
            name: data.target.nombre.value,
            address: data.target.address.value,
            phone: data.target.telefono.value
        }
        
            supermarketService.updateSuper( idSupermarket,superEdit)
                .then(navigate("/admin/supermarket"))
            window.location.reload()
            
    }

    useEffect(() => {
        
       
            supermarketService.getSuperById(idSupermarket)
                .then(res =>{
                    setAddress(res.data.address)
                    setName(res.data.name)
                    setPhone(res.data.phone)
                } )
                

        
    }, [idSupermarket])
       

    return (
        <>
        
        <form onSubmit={handleSubmit}>
                <label htmlFor="nombre">Nombre del supermercado:</label>
                <input type="text" name="nombre" id="nombre" defaultValue={name} required />

                <label htmlFor="telefono">Telefono del supermercado</label>
                <input type="number" name="telefono" id="telefono" defaultValue={phone}  required />

                <label htmlFor="address">Address del supermercado</label>
                <input type="text" name="address" id="address" defaultValue={address} required />

                <button type="submit" >Editar supermercado</button>
                <button type="reset">Limpiar</button>
            </form>
        </>
        
    )
}