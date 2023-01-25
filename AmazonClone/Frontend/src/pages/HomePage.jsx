import React, { useState, useEffect } from "react"
import ProductsGrid from "../components/ProductsGrid";
import CategoriesGrid from "../components/CategoriesGrid";
import productSever from "../server/productServer";
import CustomerSupportChatbotButton from "../components/CustomerSupportChatbotButton";
import CreateProductButton from "../components/CreateProductButton";


function HomePage() {
  const [products, setProducts] = useState([]);
  const [reload,setReload] = useState(true);

  const fetchProducts = async () => {
    try {
      productSever.findAll().catch(function (error) {
        if (error.response) {
          if (error.response.status == 404)
            navigator("/404")
          return;
        }
      })
        .then((response) => {
          if (response != undefined)
            setProducts(response.data);
        })
    }

    catch (error) {
      console.log(error)
    }
  }
  
  useEffect(() => {
    fetchProducts()
  }, [reload]);

  return (
    <>
      <CategoriesGrid />
      <ProductsGrid products={products} productsType="Popular" />
      <CustomerSupportChatbotButton />
      <CreateProductButton handleFetchProducts={fetchProducts} reloadProducts={setReload} reloadValue={reload} />
    </>
  )
}

export default HomePage;