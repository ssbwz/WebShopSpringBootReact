import './ProductCard.Sytle.css'
import '../Styles/ProductsGrid.Style.css'
import 'https://kit.fontawesome.com/c5209e6f8f.js'
import cartServer from '../server/cartServer'
import { useNavigate } from 'react-router-dom'
import React, { useState, useRef, useEffect } from 'react';
import Button from 'react-bootstrap/Button';
import Overlay from 'react-bootstrap/Overlay';
import Tooltip from 'react-bootstrap/Tooltip';
import authorisation from '../server/authorisation'

function ProductCard({ product }) {

        const [show, setShow] = useState(false);
        const target = useRef(null);

        const navigator = useNavigate();

        const onclickAddProduct = (e) => {
                e.preventDefault();
                cartServer.addProductWithOneQuanyity(product)
                setShow(!show)
        }

        const hideCart = () => {
                if (show === true) {
                        setShow(false)
                }
        }
        useEffect(
                () => {
                        if (show === true) {
                                let timer1 = setTimeout(() => setShow(!show), 1.5 * 1000);

                                // this will clear Timeout
                                // when component unmount like in willComponentUnmount
                                // and show will not change to true
                                return () => {
                                        clearTimeout(timer1);
                                };
                        }
                },
                // useEffect will run only one time with empty []
                // if you pass a value to array,
                // like this - [data]
                // than clearTimeout will run every time
                // this value changes (useEffect re-run)
                [show]
        );

        function onclickRedirectProduct(e) {
                e.preventDefault();
                navigator("/product/" + product.id)
        }

        const userType = authorisation.GetUserRoleFromToken()
        const addProductButton = userType == undefined || userType != "Owner" ? <li><Button id="addProductButton" ref={target} onClick={e => onclickAddProduct(e)}><i className="fa-solid fa-cart-shopping"></i></Button></li>
                : <></>


        return (
                <div id="product-1" className="single-product " onMouseOut={hideCart}>
                        <div className="part-1">
                                <ul>
                                        {addProductButton}
                                        <Overlay target={target.current} show={show} placement="right">
                                                {(props) => (
                                                        <Tooltip id="overlay-example" {...props}>
                                                                Product got added
                                                        </Tooltip>
                                                )}
                                        </Overlay>
                                        <li><Button onClick={e => onclickRedirectProduct(e)}><i className="fas fa-expand"></i></Button></li>
                                </ul>
                        </div>
                        <div className="part-2">
                                <h3 className="product-title">{product.name}</h3>
                                <h4 className="product-price">${product.price}</h4>
                        </div>
                </div>
        );
}

export default ProductCard;