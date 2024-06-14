import React from "react";
import UserHeaderComponent from "../../components/user/UserHeaderComponent";
import { Outlet } from "react-router-dom";

export default function UserMainView() {
    return (
        <>
            <UserHeaderComponent />
            <div className="container">
                <Outlet />
            </div>
        </>

    );
}