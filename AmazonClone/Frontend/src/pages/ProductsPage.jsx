import React, { useState, useEffect } from "react"
import productSever from "../server/productServer";
import ProductsGrid from "../components/ProductsGrid";
import { useParams } from "react-router-dom";

function ProductsPage() {
  const [products, setProducts] = useState([]);
  const { category } = useParams();
  const [categoryState, setcategory] = useState(category);

  const fetchProducts = async () => {

    try {
      productSever.findAll(category).catch(function (error) {
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
  }, []);

  return (
    <div>
      <ProductsGrid products={products} productsType={categoryState} />
    </div>
  )
}

export default ProductsPage;