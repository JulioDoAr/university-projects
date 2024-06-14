import "./CartDetailsView.css";
import React, { useEffect } from "react";
import cartItemService from "../../services/cartItemService"
import { useState } from "react";
import Loading from "../../components/Loading";
import ProductsList from "../../components/ProductsList";
import productService from "../../services/productService";
import PaymentResume from "../../components/user/PaymentResume";


export default function CartDetailsView() {

    var [products, setProducts] = useState([]);
    var [loading, setLoading] = useState(true);

    useEffect(() => {
        setProducts([]);
        cartItemService.getItemByUser(1).then((response) => {
            response.data.forEach(element => {
                productService.getProductById(element.idProducto)
                    .then((response) => {
                        let product = response.data;
                        product.quantity = element.cantidad;
                        setProducts(arr => [...arr, product].sort((a, b) => a.name.localeCompare(b.name)));
                    });
            });
            setLoading(false);
        });
    }, []);

    return (
        <table className="table">
            <tbody>
                <tr>
                    <td>
                        {loading ? <Loading /> : <ProductsList products={products} showAddToCart={true} />}
                    </td>
                    <td className="td-resume">
                        {loading ? <Loading /> : <PaymentResume products={products} />}
                    </td>
                </tr>
            </tbody>
        </table>
    );
}