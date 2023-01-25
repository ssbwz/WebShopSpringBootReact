import Form from 'react-bootstrap/Form';
import Card from 'react-bootstrap/Card';

function ViewAddress(props) {

    if (props.cart != undefined) {
        return (<>
            <Card>
                <Card.Header >Create an order</Card.Header>
                <Card.Body>

                    <Form.Group className="mb-3">
                        <Form.Label>Street</Form.Label>
                        <Form.Control type="text" value={props.order.street} disabled />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>House number</Form.Label>
                        <Form.Control type="text" value={props.order.houseNumber} disabled />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Postcode</Form.Label>
                        <Form.Control type="text" value={props.order.postcode} disabled />
                    </Form.Group>

                </Card.Body>
            </Card>
        </>)
    }
    else
        return (<></>)
}

export default ViewAddress;