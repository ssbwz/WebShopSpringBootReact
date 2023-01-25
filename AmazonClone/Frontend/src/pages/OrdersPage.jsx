import { useEffect, useState } from 'react';
import orderServer from '../server/OrderServer'
import Container from 'react-bootstrap/Container';
import Table from 'react-bootstrap/Table';
import authorisation from '../server/authorisation';
import { Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

function OrdersPage() {

    const [orders, setOrders] = useState();
    const navigator = useNavigate()

    const fetchOrders = async () => {
        orderServer.getOrders().then((response) => {
            setOrders(response.data);
        })
    }


    function onClickRedictToOrderPage(e, orderId) {
        e.preventDefault();
        navigator("/orders/" + orderId)
    }

    function onClickDeleteOrder(e, orderId) {
        e.preventDefault();
        orderServer.deleteOrderById(orderId).then(() =>{
            fetchOrders()
        })
    }

    useEffect(() => {
        fetchOrders()
    }, [])

    if (authorisation.validator(OrdersPage))
        if (orders === undefined) {
            return (<div><h4>Loading...</h4></div>);
        }
        else if (orders.length === 0) {
            return (<div><h4>There are no orders</h4></div>);
        }
        else {
            return (<>
                <Container>
                    <Table striped bordered hover>

                        <thead>
                            <tr>
                                <th>Order Id</th>
                                <th>Customer email</th>
                                <th>Order status</th>
                            </tr>
                        </thead>

                        <tbody hover={true}>
                            {orders.map(orderDetails => (
                                <tr>
                                    <td onClick={e => onClickRedictToOrderPage(e, orderDetails.id)}>{orderDetails.id}</td>
                                    <td onClick={e => onClickRedictToOrderPage(e, orderDetails.id)}>{orderDetails.creatorEmail}</td>
                                    <td onClick={e => onClickRedictToOrderPage(e, orderDetails.id)}>{orderDetails.orderStatus}</td>
                                    <td><Button onClick={e => onClickDeleteOrder(e, orderDetails.id)}>Delete order</Button></td>
                                </tr>))}
                        </tbody>
                        
                    </Table>
                </Container>
            </>);

        }

}

export default OrdersPage;