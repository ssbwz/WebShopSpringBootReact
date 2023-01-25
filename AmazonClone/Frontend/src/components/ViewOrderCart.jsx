import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import cartServer from '../server/cartServer';

function ViewOrderCart(props) {
    const cart = props.cart;

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
                        </Card.Body>
                    </Card>

                ))} <Card.Header >Total: {cart.total}</Card.Header></Card></>)
    }
    else {
        return (<div><h4>Loading...</h4></div>);
    }
}

export default ViewOrderCart;
