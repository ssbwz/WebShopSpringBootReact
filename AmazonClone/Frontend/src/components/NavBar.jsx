import { NavLink } from "react-router-dom"
import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import Button from 'react-bootstrap/Button';
import authorisation from "../server/authorisation";
import userServer from "../server/userServer";
import { useState, useEffect } from "react";

function NavBar() {


  const [currentUser, setCurrentUser] = useState()

  const fetchUser = async () => {
    try {
      await userServer.getUser()
        .then((response) => {
          if (response != undefined) {
            setCurrentUser(response)
          }
        })
    }
    catch (error) {
      console.log(error);
    }
  }

  useEffect(() => {
    fetchUser()
  }, [])


  function logOut(e) {
    e.preventDefault();
    authorisation.logout()
  }

  const links = [
    {
      id: 1,
      path: "/",
      text: "Home"
    },
    {
      id: 2,
      path: "/products",
      text: "Products"
    }
  ]


  if (currentUser != undefined) {
    const cartPage = currentUser.userType === "Customer" ?
      <Navbar.Brand id="CartPagebutton" href={"/cart"}>
        <i className="fa-solid fa-cart-shopping"></i>
      </Navbar.Brand> : <></>;

    const ordersPage = authorisation.GetUserRoleFromToken() === "Owner" ?
      <Navbar.Brand href={"/orders"}>
        Orders
      </Navbar.Brand> : <></>

    return (
      <Container>
        <Navbar bg="light" variant="light">
          <Container>
            <Nav className="me-auto">
              {links.map(link => {
                return (
                  <Navbar.Brand key={link.id} href={link.path}>
                    {link.text}
                  </Navbar.Brand >
                )
              })}
              {ordersPage}
            </Nav>
            <Navbar.Collapse className="justify-content-end">
              <Navbar.Brand >
                <Navbar.Text>
                  Welcome:  {currentUser.firstname + " " + currentUser.lastname}
                </Navbar.Text>
              </Navbar.Brand >
              {cartPage}
              <Navbar.Toggle />
              <Button variant="primary" onClick={e => logOut(e)}>Logout</Button>{' '}
            </Navbar.Collapse>
          </Container>
        </Navbar>
      </Container>
    );
  }
  else {
    return (
      <Container>
        <Navbar bg="light" variant="light">
          <Container>
            <Nav className="me-auto">
              {links.map(link => {
                return (
                  <div >
                    <Navbar.Brand key={link.id} href={link.path}>
                      {link.text}
                    </Navbar.Brand >
                    <Navbar.Toggle />
                  </div>
                )
              })}
            </Nav>

            <Navbar.Collapse className="justify-content-end">
              <div>
                <Nav.Link href="/login">
                  <Button variant="primary">log in</Button>
                </Nav.Link >
                <Navbar.Toggle />
              </div>
            </Navbar.Collapse>

          </Container>
        </Navbar>
      </Container>
    );
  }
}


export default NavBar;