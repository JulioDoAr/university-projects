import React from "react";
import UserHeaderComponent from "../../components/user/UserHeaderComponent";
import { Outlet } from "react-router-dom";

export default function UserHomeView() {
    return (
        <div>
            <UserHeaderComponent />
            <h1>User Home View</h1>

            <Outlet />
        </div>
    );
}