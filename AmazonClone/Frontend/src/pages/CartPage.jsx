import { useState } from "react";
import { useEffect } from "react";
import authorisation from "../server/authorisation";
import cartServer from "../server/cartServer";
import CartView from "../components/CartView";
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import UserView from "../components/UserView";
import CreateOrder from "../components/CreateOrder";
import Container from 'react-bootstrap/Container';


function CartPage() {

    const [cart, setCart] = useState()
    const [reload, setReload] = useState(true)

    const fetchCart = async () => {
        try {
            await cartServer.getMyCart()
                .catch(function (error) {
                    if (error.response) {
                        if (error.response.status == 404) {
                            setCart("empty")
                        }
                    }
                })
                .then((response) => {
                    if (response != undefined) {
                        setCart(response.data)
                    }
                })
        }
        catch (error) {
            console.log(error);
        }
    }

    useEffect(() => {
        fetchCart()

    }, [reload])

    function reloadPage() {
        setReload(!reload)
    }

    if (authorisation.validator(CartPage))
        return (
            <Container >
                <Row xs={1} md={2} className="g-4">
                    <Col xl={8}><CartView reloadCartPage={reloadPage} cart={cart} fetchCart={fetchCart} /></Col>
                    <Col xl={4}><UserView /></Col>
                    <Col xl={4}><CreateOrder fetchCart={fetchCart} cart={cart} reloadCartPage={reloadPage} /></Col>
                </Row>
            </Container>
        )
}
export default CartPage;
