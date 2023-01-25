import authorisation from "./authorisation";
import http from "./serverBase";



const addProductWithOneQuanyity = (product) => {
    authorisation.authorizationValidator()

    const addProductRequest = {
        creatorId: authorisation.GetUserIdFromToken(),
        productId: product.id,
        increasedQuantity: 1
    }

    http.put("carts/addProduct", addProductRequest)
};

const deleteProductWithOneQuanyity = (product) => {
    authorisation.authorizationValidator()

    const deleteConfig = {
        headers: {
            'Authorization': "Bearer " + JSON.parse(localStorage.getItem("accessToken")).accessToken,
            "Content-Type": "application/json"
        },
        data: {
            productId: product.id,
            creatorId: authorisation.GetUserIdFromToken(),
            decreasedQuantity: 1
        }
    }

  return  http.delete("carts/deleteProduct", deleteConfig)
};

const getMyCart = () => {
    authorisation.authorizationValidator()

    return http.get("carts/creator")
};

const cartServer = {
    addProductWithOneQuanyity,
    deleteProductWithOneQuanyity,
    getMyCart
};

export default cartServer;