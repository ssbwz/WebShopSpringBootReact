import react, { useState } from "react"
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import orderServer from '../server/OrderServer'
import Card from 'react-bootstrap/Card';
import authorisation from "../server/authorisation";

function CreateOrder(props) {

    const [street, setStreet] = useState();
    const [houseNumber, setHouseNumber] = useState();
    const [postcode, setPostcode] = useState();

    const [postcodeValidateionMessage, setPostcodeValidateionMessage] = useState();
    const [houseNumberValidateionMessage, setHouseNumberValidateionMessage] = useState();


    const createOrder = () => {
        try {

            const createOrderRequest = {
                creatorId: authorisation.GetUserIdFromToken(),
                street: street,
                houseNumber: houseNumber,
                postcode: postcode
            }
            
            if (!validateHouseNumber(createOrderRequest.houseNumber)) {
                setHouseNumberValidateionMessage("Please enter a valid house number")
                return;
            }

            if (!validatePostCode(createOrderRequest.postcode)) {
                setPostcodeValidateionMessage("Please enter a valid post code")
                return;
            }

            orderServer.createOrder(createOrderRequest).then(() => {
                props.reloadCartPage()
            }).catch(function (error) {
                if (error.response) {
                    console.log(error.response)
                }
            });

           
        }
        catch (error) {
            console.log(error);
        }
    }

    function validateHouseNumber(HouseNum) {
        var re = /^[1-9]\d*(?:[ -]?(?:[a-zA-Z]+|[1-9]\d*))?$/;
        return re.test(HouseNum);
    }
    function validatePostCode(postcode) {
        var re = /^[1-9][0-9]{3}[\s]?[A-Za-z]{2}$/;
        return re.test(postcode);
    }
    
    if (props.cart != undefined) {
        if(props.cart != "empty" ){
            return (<>
                <Card>
                    <Card.Header >Create an order</Card.Header>
                    <Card.Body>
                        <Form.Group className="mb-3">
                            <Form.Label>Street</Form.Label>
                            <Form.Control id="StreetField" type="text" placeholder="Street" onChange={e => setStreet(e.target.value)} />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>House number</Form.Label>
                            <Form.Control  id="HouseNumberField" type="text" placeholder="House number" onChange={e => setHouseNumber(e.target.value)} />
                            <Form.Label>{houseNumberValidateionMessage}</Form.Label>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Postcode</Form.Label>
                            <Form.Control id="PostcodeField" type="text" placeholder="Postcode" onChange={e => setPostcode(e.target.value)} />
                            <Form.Label>{postcodeValidateionMessage}</Form.Label>
                        </Form.Group>
    
                        <Button id="CreateOrderButton" variant="primary" type="submit" onClick={createOrder}>
                            Create
                        </Button>
                    </Card.Body>
                </Card>
            </>)}
            else
            return (<></>)
        
    }
   
}


export default CreateOrder;