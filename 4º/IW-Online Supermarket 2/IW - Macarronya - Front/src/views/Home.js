export default function Home() {
    return (
        <>
            <h1>
                Bienvenido a la página principal.
            </h1>
            <p>
                El sitio esta dividido de la siguiente manera:
            </p>

            <ul>
                <li>
                    <b>Home:</b> Página principal.
                </li>
                <li>
                    <b>Admin:</b> Página de administración de productos.
                    <ul>
                        <li>
                            <b>Products:</b> Página de administración de productos.
                        </li>
                        <li>
                            <b>Supermarket:</b> Página de administración de supermercados.
                        </li>
                    </ul>
                </li>
                <li>
                    <b>User:</b> Página a la que tendria acceso el usuario.
                    <ul>
                        <li>
                            <b>Home:</b> Página principal del usuario. Se mostrará un top de supermercados y un top de productos
                        </li>
                        <li>
                            <b>Products:</b> Página de búsqueda de productos. Permite añadir un producto al carrito.
                        </li>
                        <li>
                            <b>Supermarket:</b> Página de búsqueda de supermercados.
                        </li>
                        <li>
                            <b>Cart:</b> Página de resumen del carrito. Muestra la lista de productos añadidos y una tabla resumen de la factura.
                        </li>
                    </ul>
                </li>
            </ul>
        </>
    );
}