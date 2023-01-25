import './App.css';
import { Route, Routes, BrowserRouter as Router } from "react-router-dom";
import React from 'react';
import HomePage from './pages/HomePage';
import ProductsPage from './pages/ProductsPage';
import ProductPage from './pages/ProductPage';
import NavBar from './components/NavBar';
import 'bootstrap/dist/css/bootstrap.min.css';
import NotFoundPage from './pages/notFoundPage';
import LoginPage from './pages/LoginPage';
import AcceessDeniedPage from './pages/AccessDeniedPage';
import CartPage from './pages/CartPage';
import { ToastContainer } from 'react-toastify';
import RegisterPage from './pages/RegisterPage';
import OrdersPage from './pages/OrdersPage';
import OrderPage from './pages/OrderPage';

function App() {
  return (
    <div className="App">
      <Router>
        <NavBar />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/products" element={<ProductsPage />} />
          <Route path="/products/:category" element={<ProductsPage />} />
          <Route path="/product/:productId" element={<ProductPage />} />
          <Route path="/cart" element={<CartPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/Register" element={<RegisterPage />} />
          <Route path="/Orders" element={<OrdersPage />} />
          <Route path="/Orders/:orderId" element={<OrderPage />} />
          <Route path='AccessDenied' element={<AcceessDeniedPage />} />
          <Route path="/404" element={<NotFoundPage />} />
        </Routes>
        <ToastContainer />
      </Router>
    </div>
  );
}

export default App;
