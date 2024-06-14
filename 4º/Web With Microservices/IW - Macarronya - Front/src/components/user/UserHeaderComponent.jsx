import React from "react";
import { NavLink } from "react-router-dom";
export default function UserHeaderComponent() {
    return (
        <div className="container">
            <div className="row justify-content-end">
                <nav className="navbar navbar-light bg-light">
                   <NavLink className="nav-link col-2" to="/user">Home</NavLink>
                   <NavLink className="nav-link col-2" to="/user/products">Products</NavLink>
                   <NavLink className="nav-link col-2" to="/user/supermarket">Supermarket</NavLink>
                   <NavLink className="nav-link col-2" to="/user/cart">Cart</NavLink>
                    
                </nav>
            </div>
        </div>
    );
}