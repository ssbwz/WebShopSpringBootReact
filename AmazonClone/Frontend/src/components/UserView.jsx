import { useEffect } from 'react';
import { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import userServer from '../server/userServer';

function UserView() {

  const [currentUser, setCurrentUser] = useState()

  const fetchCurrentUser = async () => {
    try {
      await userServer.getUser()
        .then((user) => {
          if (user != undefined) {
            setCurrentUser(user)
          }
        }).catch(function (error) {
          if (error.response) {
            window.location.replace("/login")
          }
        })
    }
    catch (error) {
      console.log(error);
    }
  }

  useEffect(() => {
    fetchCurrentUser()
  }, [])

  if (currentUser === undefined) {
    return (<div><h4>Loading...</h4></div>);
  }
  else {
    return (<>
      <Card key={currentUser.id} className="text-center">
        <Card.Header >Account</Card.Header>
        <Card.Body>
          <Card.Title>Email: {currentUser.email}</Card.Title>
          <Card.Text>Name: {currentUser.firstname} {currentUser.lastname}</Card.Text>
        </Card.Body>
      </Card> </>)
  }
}

export default UserView;
