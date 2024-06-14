import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from './views/Home';
import Header from './components/Header';
import Footer from './components/Footer';
import UserMainView from './views/user/UserMainView';
import UserProductsView from './views/user/UserProductsView';
import UserSupermarketView from './views/user/UserSupermarketView';
import AdminHomeView from './views/admin/AdminHomeView';
import AdminProductsView from './views/admin/AdminProductsView';
import AdminSupermarketView from './views/admin/AdminSupermarketView';
import CartDetailsView from './views/user/CartDetailsView';
import UserTopView from './views/user/UserTopView';
import { AdminEditSupermarketView } from './views/admin/AdminEditSupermarketView';

import { AdminEditProductView } from './views/admin/AdminEditProductView';

import SupermarketDetailsView from './views/SupermarketDetailsView';
import ProductDetailsView from './views/ProductDetailsView';


function App() {
  return (
    <>
      <BrowserRouter>
        <Header />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/user" element={<UserMainView />}>
            <Route path="" element={<UserTopView />} />
            <Route path="products" element={<UserProductsView />} />
            <Route path="supermarket" element={<UserSupermarketView />} />
            <Route path="cart" element={<CartDetailsView />} />
          </Route>
          <Route path="/admin" element={<AdminHomeView />}>
            <Route path="products" element={<AdminProductsView />} />
            <Route path="products/:productId" element={<AdminEditProductView />} />
            <Route path="supermarket" element={<AdminSupermarketView />} />
            <Route path='supermarket/:idSupermarket' element={<AdminEditSupermarketView />} />
          </Route>
          <Route path="supermarket/:id" element={<SupermarketDetailsView />} />
          <Route path="product/:id" element={<ProductDetailsView />} />
        </Routes>
        <Footer />
      </BrowserRouter>
    </>
  );
}

export default App;
