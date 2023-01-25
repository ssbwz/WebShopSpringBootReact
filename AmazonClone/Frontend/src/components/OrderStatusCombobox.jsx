import ButtonGroup from 'react-bootstrap/ButtonGroup';
import Dropdown from 'react-bootstrap/Dropdown';
import DropdownButton from 'react-bootstrap/DropdownButton';
import Card from 'react-bootstrap/Card';
import orderServer from '../server/OrderServer';

function OrderStatusCombobox(props) {

    const order = props.order;
    const onClickChangeStatus = (e, status) => {
        e.preventDefault();

        const changeOrderStatusRequest = {
            "id": order.id,
            "status": status
        }

        orderServer.updateStatus(changeOrderStatusRequest).then(() => {
            props.refreshOrderPage()
        })
    }

    if (order != undefined) {
        return (<>
            <Card>
                <Card.Header >Order status</Card.Header>
                <Card.Body>
                    <div className="mb-2">
                        <DropdownButton
                            as={ButtonGroup}
                            id={`dropdown-button-drop-0`}
                            size="lg"
                            title={order.status}
                        >
                            <Dropdown.Item onClick={e => onClickChangeStatus(e, "Received")}>Received</Dropdown.Item>
                            <Dropdown.Item onClick={e => onClickChangeStatus(e, "Preparing")}>Preparing</Dropdown.Item>
                            <Dropdown.Item onClick={e => onClickChangeStatus(e, "Shipping")}>Shipping</Dropdown.Item>
                            <Dropdown.Item onClick={e => onClickChangeStatus(e, "Delivered")}>Delivered</Dropdown.Item>
                        </DropdownButton>
                    </div>
                </Card.Body>
            </Card>
        </>)
    }
    else
        return (<></>)

}

export default OrderStatusCombobox;