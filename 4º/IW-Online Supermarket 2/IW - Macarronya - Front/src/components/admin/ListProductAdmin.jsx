import { CardProductAdmin } from "./CardProductAdmin"
export function ListProductAdmin({products}){

    return (
        <div className="products-list">

            {products.map((product) => (
                <CardProductAdmin key={product.pk} product={product} />
            ))}
        </div>
    )
}