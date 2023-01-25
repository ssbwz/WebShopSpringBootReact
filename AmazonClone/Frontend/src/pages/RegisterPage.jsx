import React, { useState } from 'react';
import {
    MDBBtn,
    MDBContainer,
    MDBRow,
    MDBCol,
    MDBCard,
    MDBCardBody,
    MDBCardImage,
    MDBInput,
    MDBIcon
}
    from 'mdb-react-ui-kit';
import Form from 'react-bootstrap/Form';
import userServer from '../server/userServer';
import { Button } from 'react-bootstrap';
import Container from 'react-bootstrap/Container';

function RegisterPage() {

    const [email, setEmail] = useState();
    const [password, setPassword] = useState();
    const [firstname, setFirstname] = useState();
    const [lastname, setLastname] = useState();
    const [vEmail, setVEmail] = useState();
    const [vPassword, setVPassword] = useState();
    const [vFirstname, setVFirstname] = useState();
    const [VLastname, setVLastname] = useState();


    const register = () => {
        try {

            const reigsterUser = {
                "password": password,
                  "email":email,
                  "firstname": firstname,
                  "lastname": lastname
            }

            if (!firstname?.trim()) {
                setVFirstname("Please fill first name.")
                return
            } else
                setVFirstname(undefined)

            if (!lastname?.trim()) {
                setVLastname("Please fill last name.")
                return
            }
            else
                setVLastname(undefined)

            if (!validateEmail(email)) {
                setVEmail("Please enter valid email.")
                return;
            } else
                setVEmail(undefined)

            if (!validatePassword(password)) {
                setVPassword("Password should contain numbers and letters and at least 6 characters.")
                return;
            }
            else
                setVPassword(undefined)
            debugger;
            userServer.register(reigsterUser)
                .then(() => {
                    debugger;
                    window.location.replace("/login")
                }).catch(function (error) {
                    if (error.response) {
                        if (error.response.status == 500) {
                            setVEmail("This email is already been used");
                        }
                        else
                        setVEmail(undefined)
                    }
                })
        }
        catch (error) {
            console.log(error);
        }
    }

    function validateEmail(email) {
        var re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    }
    function validatePassword(password) {
        var re = /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$/;
        return re.test(password);
    }


    return (
        <Container>
        <MDBContainer fluid>

            <MDBCard className='text-black m-5' style={{ borderRadius: '25px' }}>
                <MDBCardBody>
                    <MDBRow>
                        <MDBCol md='10' lg='6' className='order-2 order-lg-1 d-flex flex-column align-items-center'>

                            <p classNAme="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Sign up</p>

                            {vFirstname}
                            <div className="d-flex flex-row align-items-center mb-4 ">
                                <MDBIcon fas icon="user me-3" size='lg' />
                                <MDBInput label='First name' id='form1' type='text' onChange={e => setFirstname(e.target.value)} className='w-100' />
                            </div>

                            {VLastname}
                            <div className="d-flex flex-row align-items-center mb-4 ">
                                <MDBIcon fas icon="user me-3" size='lg' />
                                <MDBInput label='last Name' id='form1' type='text' onChange={e => setLastname(e.target.value)} className='w-100' />
                            </div>

                            {vEmail}
                            <div className="d-flex flex-row align-items-center mb-4">
                                <MDBIcon fas icon="envelope me-3" size='lg' />
                                <MDBInput label='Your Email' id='form2' onChange={e => setEmail(e.target.value)} type='email' />
                            </div>

                            {vPassword}
                            <div className="d-flex flex-row align-items-center mb-4">
                                <MDBIcon fas icon="lock me-3" size='lg' />
                                <MDBInput label='Password' id='form3' onChange={e => setPassword(e.target.value)} type='password' />
                            </div>

                            <Button className='mb-4' onClick={register} size='lg'>Register</Button>

                        </MDBCol>

                        <MDBCol md='10' lg='6' className='order-1 order-lg-2 d-flex align-items-center'>
                            <MDBCardImage src='https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/draw1.webp' fluid />
                        </MDBCol>

                    </MDBRow>
                </MDBCardBody>
            </MDBCard>

        </MDBContainer>
        </Container>
    );
}

export default RegisterPage;
