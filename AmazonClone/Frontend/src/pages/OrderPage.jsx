import { useEffect, useState } from 'react';
import orderServer from '../server/OrderServer'
import Container from 'react-bootstrap/Container';
import authorisation from '../server/authorisation';
import { useParams } from 'react-router-dom';
import ViewOrderCart from '../components/ViewOrderCart';
import UserView from '../components/UserView';
import ViewAddress from '../components/ViewAddress';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import OrderStatusCombobox from '../components/OrderStatusCombobox';


function OrderPage() {

    const [order, setOrder] = useState();
    const { orderId } = useParams();
    const [reload, setReload] = useState(true)

    const fetchOrders = async () => {
        orderServer.getOrderById(orderId).then((response) => {
            setOrder(response.data);
        })
    }

    function reloadPage() {
        setReload(!reload)
    }

    useEffect(() => {
        fetchOrders()
    }, [reload])

    if (authorisation.validator(OrderPage))
        if (order === undefined) {
            return (<div><h4>Loading...</h4></div>);
        }
        else {
            return (<>
                <Container>
                <Row xs={1} md={2} className="g-4">
                    <Col xl={8}><ViewOrderCart cart={order.cart} /></Col>
                    <Col xl={4}><UserView /></Col>
                    <Col xl={4}><ViewAddress cart={order.cart} order={order}/></Col>
                    <Col xl={4}><OrderStatusCombobox order={order} refreshOrderPage={reloadPage}/></Col>
                </Row>
                </Container>
            </>);
        }
}

export default OrderPage;