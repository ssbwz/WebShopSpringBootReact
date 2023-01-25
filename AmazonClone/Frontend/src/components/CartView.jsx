import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import cartServer from '../server/cartServer';

function CartView(props) {
    const cart = props.cart;

    function deleteProduct(event, cartProduct) {
        event.preventDefault();
        cartServer.deleteProductWithOneQuanyity(cartProduct.product).then(() =>{
            props.reloadCartPage()
        })
    }



    if (cart != undefined && cart != "empty") {
        return (<>
            <Card className="text-start">
                <Card.Header >Products</Card.Header>
                {cart.productList.map(cartProduct => (
                    <Card key={cartProduct.id} className="text-center">
                        <Card.Header >{cartProduct.product.name}</Card.Header>
                        <Card.Body>
                            <Card.Text>
                                Total: {cartProduct.price}
                            </Card.Text>
                            <Card.Text>
                                Quantity: {cartProduct.quantity}
                            </Card.Text>
                            <Button variant="primary" onClick={e => deleteProduct(e, cartProduct)}>Delete one</Button>
                        </Card.Body>
                    </Card>

                ))} <Card.Header >Total: {cart.total}</Card.Header></Card></>)
    }
    else if (cart == "empty") {
        return (<h3>This cart is empty</h3>)
    }
    else {
        return (<div><h4>Loading...</h4></div>);
    }
}

export default CartView;
