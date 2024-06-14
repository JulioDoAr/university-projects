import React from "react";
import { Outlet } from "react-router-dom";
import AdminHeaderComponent from "../../components/admin/AdminHeaderComponent";
export default function AdminHomeView() {
    return (
        <div>
            <AdminHeaderComponent />
            <div className="container">
                <Outlet />
            </div>
        </div>
    );
}