import React from "react";
import { NavLink } from "react-router-dom";
export default function Header() {
    return (
        <div className="container">
            <div className="row justify-content-end">
                <nav className="navbar navbar-light bg-light">
                    
                    <NavLink className="navbar-brand col-2" to="/">Home</NavLink>
                    <NavLink className="navbar-brand col-2" to="/admin">Admin</NavLink>
                    <NavLink className="navbar-brand col-2" to="/user">User</NavLink>
                </nav>
            </div>
        </div>
    );
}