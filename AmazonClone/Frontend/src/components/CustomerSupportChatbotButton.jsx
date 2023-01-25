import React, { useState, useEffect } from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import Button from 'react-bootstrap/Button';
import Offcanvas from 'react-bootstrap/Offcanvas';
import "../Styles/Chat.Style.css"
import {
    MDBContainer,
    MDBRow,
    MDBCol,
    MDBCard,
    MDBCardHeader,
    MDBCardBody,
    MDBCardFooter,
    MDBIcon,
} from "mdb-react-ui-kit";
import authorisation from "../server/authorisation";


// Set the backend location
const ENDPOINT = "http://localhost:8080/ws";


function CustomerSupportChatbotButton() {

    function ChatBox({ name, ...props }) {
        const [show, setShow] = useState(false);

        const handleClose = () => setShow(false);
        const handleShow = () => setShow(true);


        const [stompClient, setStompClient] = useState();
        const [MSGToSend, setMSGToSend] = useState();
        const [messageElementsReceived, setMessageElementsReceived] = useState([]);
        useEffect(() => {
            // use SockJS as the websocket client
            const socket = SockJS(ENDPOINT);
            // Set stomp to use websockets
            const stompClient = Stomp.over(socket);
            // connect to the backend
            stompClient.connect({}, () => {
                // subscribe to the backend
                stompClient.subscribe('/chat', (data) => {
                    console.log(data);
                    onMessageReceived(JSON.parse(data.body));
                });
            });
            // maintain the client for sending and receiving
            setStompClient(stompClient);
        }, []);

        // send the data using Stomp
        const sendMessage = () => {
            const request = {
                senderId: authorisation.GetUserIdFromToken(),
                question: MSGToSend
            }
            stompClient.send(`/channel/chatMessages`, {}, JSON.stringify(request));

            const message = {
                id: 1,
                content: MSGToSend,
                dateTime: `${new Date().getHours()}:${new Date().getMinutes()}`,
                userType: "Customer"
            }

            onMessageReceived(message);

        };

        // display the received data
        const onMessageReceived = (message) => {
            const isSender = message.userType != "Owner";
            const side = isSender ? "d-flex flex-row justify-content-start" : "d-flex flex-row justify-content-end mb-4 pt-1";
            const pStyle = isSender ? "small p-2 ms-3 mb-1 rounded-3" : "small p-2 me-3 mb-1 text-white rounded-3 bg-primary";
            const timeStyle = isSender ? "small ms-3 mb-3 rounded-3 text-muted" : "small me-3 mb-3 rounded-3 text-muted d-flex justify-content-end"


            const messageElement = <div className={side}>
                <div>
                    <p
                        className={pStyle}
                        style={{ backgroundColor: "#f5f6f7" }}
                    >
                        {message.content}
                    </p>
                    <p className={timeStyle}>
                        {message.dateTime}
                    </p>
                </div>
            </div>

            setMessageElementsReceived(messageElementsReceived => [...messageElementsReceived, messageElement]);
        };

        return (
            <>
                <Button variant="primary" onClick={handleShow} className="me-2">
                    Customer support
                </Button>
                <Offcanvas show={show} onHide={handleClose} {...props}>
                    <Offcanvas.Header closeButton>
                        <Offcanvas.Title>Chat</Offcanvas.Title>
                    </Offcanvas.Header>
                    <Offcanvas.Body>
                        <MDBContainer fluid className="py-5" style={{ backgroundColor: "#eee" }}>
                            <MDBRow className="d-flex justify-content-center">
                                <MDBCol md="100" lg="800" xl="600">
                                    <MDBCard id="chat2" style={{ borderRadius: "15px" }}>
                                        <MDBCardHeader className="d-flex justify-content-between align-items-center p-3">
                                        </MDBCardHeader>
                                        <MDBCardBody>
                                            <p>Hey you can ask this: How to contact us?</p>
                                            {messageElementsReceived.map(messageElement => (messageElement))}
                                        </MDBCardBody>
                                        <MDBCardFooter className="text-muted d-flex justify-content-start align-items-center p-3">
                                            <input
                                                type="text"
                                                class="form-control form-control-lg"
                                                id="exampleFormControlInput1"
                                                placeholder="Type message"
                                                onChange={e => setMSGToSend(e.target.value)}
                                            ></input>
                                            <a className="ms-3" onClick={sendMessage}>
                                                <MDBIcon fas icon="paper-plane" />
                                            </a>
                                        </MDBCardFooter>
                                    </MDBCard>
                                </MDBCol>
                            </MDBRow>
                        </MDBContainer>


                    </Offcanvas.Body>
                </Offcanvas>
            </>
        );
    }

    if (authorisation.isAuthorizied()) {
        return (<>
            <ChatBox placement='end' name='end' />
        </>
        );
    }
}
export default CustomerSupportChatbotButton;



