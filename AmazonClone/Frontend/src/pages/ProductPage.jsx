import React, { useState, useEffect } from "react"
import { useNavigate, useParams } from "react-router-dom";
import authorisation from "../server/authorisation";
import cartServer from "../server/cartServer";
import productSever from "../server/productServer";
import '../Styles/ProductPage.Style.css'

function ProductPage() {

    const [product, setProduct] = useState();
    const { productId } = useParams();

    const navigator = useNavigate();

    const fetchProduct = async () => {
        try {
            productSever.findProductById(productId).catch(function (error) {
                if (error.response) {
                    if (error.response.status == 404)
                        navigator("/404")
                    return;
                }
            })
                .then((response) => {
                    if (response != undefined)
                        setProduct(response.data)
                })

        }
        catch (error) {
            console.log(error);
        }
    }

    const deleteProductElement = authorisation.isOwner() ? <div class="buttons d-flex flex-row mt-5 gap-3">
        <button onClick={e => onClickDeleteProduct(e)} class="btn btn-dark" >delete product</button>
    </div>
        : <></>

    const addProductToCartButton = !authorisation.isOwner() ? <div class="buttons d-flex flex-row mt-5 gap-3">
        <button onClick={e => onclickAddProduct(e)} class="btn btn-dark" >Add to Basket</button>
    </div> : <></>

    function onClickDeleteProduct(e) {
        e.preventDefault();
        productSever.deleteProduct(product.id).then(() =>{
            alert("Product got deleted successfully");
            navigator("/")
        }).catch(function (error) {
            if (error.response) {
                if (error.response.status == 400) {
                    alert(error.response.data);
                    return;
                }
            }
        });
        
    }

    function onclickAddProduct(e) {
        e.preventDefault();
        cartServer.addProductWithOneQuanyity(product)
    }
    useEffect(() => {
        fetchProduct()
    }, []);

    if (product == undefined) {
        return (<div><h4>Loading...</h4></div>);
    }
    else {
        return (
            <div class="container mt-5 mb-5">
                <div class="card">	<div class="row g-0">
                    <div class="col-md-6 border-end">
                        <div class="d-flex flex-column justify-content-center">
                            <div class="main_image">
                                <img src="https://i.ibb.co/L8Nrb7p/1.jpg" id="main_product_image" />
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="p-3 right-side">
                            <div class="d-flex justify-content-between align-items-center">
                                <h3>{product.name}</h3>	<span class="heart">
                                    <i class='bx bx-heart'></i>
                                </span>
                            </div>
                            <div class="mt-2 pr-3 content">
                                <p>{product.description}</p>
                            </div>	<h3>${product.price}</h3>

                            {addProductToCartButton}

                            {deleteProductElement}

                        </div>
                    </div>
                </div>
                </div>
            </div>
        );
    }
}

export default ProductPage;