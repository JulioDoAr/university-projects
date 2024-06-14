import React from "react";
import "./PaymentResume.css";

export default function PaymentResume(products) {
    return (
        <table className="payment-table">
            <thead>
                <tr className="table-head-tr">
                    <td className="table-head-name"> Nombre </td>
                    <td className="table-head-price"> Precio </td>
                    <td className="table-head-quantity"> Cantidad </td>
                    <td className="table-head-total"> Total </td>
                </tr>
            </thead>
            <tbody>
                {products.products.map((product) => (
                    <tr className="table-body-row" key={product.pk}>
                        <td className="table-body-name"> {product.name} </td>
                        <td className="table-body-price"> {product.price} </td>
                        <td className="table-body-quantity"> {product.quantity} </td>
                        <td className="table-body-total"> {product.price * product.quantity} </td>
                    </tr>
                ))}

                <tr>
                    <td></td>
                    <td></td>
                    <td className="table-total"> Total: </td>
                    <td className="table-total-value"> {products.products.reduce((total, product) => total + (product.price * product.quantity), 0)} </td>
                </tr>
            </tbody>
        </table>
    );
}