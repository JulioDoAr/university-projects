import React from "react";

import { NavLink } from "react-router-dom";
export default function AdminHeaderComponent() {
    return (
        <div className="container">
            <div className="row justify-content-end">
                <nav className="navbar navbar-light bg-light">
                    <NavLink className="nav-link col-2" to="/admin">Home</NavLink>
                    <NavLink className="nav-link col-2" to="/admin/products">Products</NavLink>
                    <NavLink className="nav-link col-2" to="/admin/supermarket">Supermarket</NavLink>
                </nav>
            </div>
        </div>
    );
}