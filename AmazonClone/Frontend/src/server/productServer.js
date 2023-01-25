import http from './serverBase'

const findProductById = (productId) => {
  return http.get('products/' + productId);
}

const deleteProduct = productId => {
  return http.delete(`products/${productId}`);
}

const createProduct = (createProductRequest) => {
  return http.post(`products/`,createProductRequest).catch(function (error) {
      if (error.response) {
       
        if (error.response.status == 401)
          navigator("/AccessDenied")
        return;
      }
    });;
}

const findAll = (category) => {
  if (category !== undefined) {
    return http.get(
      'http://localhost:8080/products' + "?category=" + category,
    );
  }
  else {
    return http.get(
      'http://localhost:8080/products',
    );
  }
}

const productSever = {
  findProductById,
  findAll,
  deleteProduct,
  createProduct
}

export default productSever;