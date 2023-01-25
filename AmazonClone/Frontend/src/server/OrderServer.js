import http from './serverBase'
import authorisation from "./authorisation";

const createOrder = (request) => {
    authorisation.authorizationValidator();
    return http.post('/orders', request)
}

const getOrders = () => {
    authorisation.authorizationValidator();
    return http.get('/orders')
}

const getOrderById = (orderId) => {
    authorisation.authorizationValidator();
    return http.get('/orders/' + orderId)
}

const updateStatus = (request) => {
    authorisation.authorizationValidator();
    return http.put('/orders', request)
}

const deleteOrderById = (orderId) => {
    authorisation.authorizationValidator();
    return http.delete('/orders/' + orderId)
}


const orderServer = {
    createOrder,
    getOrders,
    getOrderById,
    updateStatus,
    deleteOrderById
}

export default orderServer;