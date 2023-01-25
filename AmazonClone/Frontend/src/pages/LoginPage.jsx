import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import React, { useState } from 'react';
import userServer from '../server/userServer';
import authorisation from '../server/authorisation';
import Container from 'react-bootstrap/Container';
import {
    MDBContainer,
    MDBInput,
}
    from 'mdb-react-ui-kit';

function LoginPage() {
    const [email, setEmail] = useState();
    const [password, setPassword] = useState();
    const [vEmail, setVEmail] = useState();
    const [errorMessage, setErrorMessage] = useState();

    const login = () => {
        try {

            const credentials = {
                "email": email,
                "password": password
            }

            if (!validateEmail(email)) {
                setVEmail("Please enter valid email")
                return;
            }

            userServer.login(credentials).catch(function (error) {
                if (error.response) {
                    if (error.response.status == 400) {
                        setErrorMessage("Please check your credentials");
                        return
                    }

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

    if (authorisation.validator(LoginPage)) {
        return (
            <>
                <Container>
                    <MDBContainer className="p-3 my-5 d-flex flex-column w-50">
                        <Form.Label>{vEmail}</Form.Label>
                        <MDBInput id="formBasicEmail" wrapperClass='mb-4' label='Email address' onChange={e => setEmail(e.target.value)} type='email' />
                        <Form.Label>{errorMessage}</Form.Label>
                        <MDBInput id="formBasicPassword" wrapperClass='mb-4' label='Password' onChange={e => setPassword(e.target.value)} type='password' />
                        <Button id="loginButton" className="mb-4" onClick={login} >Sign in</Button>
                        <div className="text-center">
                            <p>Not a customer? <a href="/Register">Register</a></p>
                        </div>
                    </MDBContainer>
                </Container>
            </>
        )
    }
}

export default LoginPage;
