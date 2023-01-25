import './ProductCard.Sytle.css'
import '../Styles/CategoriesGrid.Style.css'
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import { useNavigate } from 'react-router-dom';

import Card from 'react-bootstrap/Card';

function CategoryGrid() {

  const navigate = useNavigate();

  const directToCategory = (event, category) => {
    event.preventDefault();
    navigate("/products/" + category);
  };

  return (
      <Container>
        <Row xs={1} md={3} className="g-4">
          <div className="btn pointer-events button" onClick={event => directToCategory(event, 'Mens')}>
            <Col>
              <Card className="fillContainer">
                <Card.Img variant="top" src="https://media.istockphoto.com/photos/beautiful-emotional-woman-picture-id1333081908?k=20&m=1333081908&s=612x612&w=0&h=eKb___GmbgaN_Djtj7kV5Xs1OWp6P2oOhezzvwq5OBw=" />
                  <Card.Title>Mens</Card.Title>
              </Card>
            </Col>
          </div>
          <div  className="btn pointer-events button" onClick={event => directToCategory(event, 'Females')}>
            <Col>
              <Card className="fillContainer">
                <Card.Img variant="top" src="https://media.istockphoto.com/photos/beautiful-emotional-woman-picture-id1333081908?k=20&m=1333081908&s=612x612&w=0&h=eKb___GmbgaN_Djtj7kV5Xs1OWp6P2oOhezzvwq5OBw=" />
                  <Card.Title>Females</Card.Title>
              </Card>
            </Col>
          </div>
          <div className="btn button pointer-events" onClick={event => directToCategory(event, 'Unisex')}>
            <Col>
              <Card className="fillContainer">
                <Card.Img variant="top" src="https://media.istockphoto.com/photos/beautiful-emotional-woman-picture-id1333081908?k=20&m=1333081908&s=612x612&w=0&h=eKb___GmbgaN_Djtj7kV5Xs1OWp6P2oOhezzvwq5OBw=" />
                  <Card.Title>Unisex</Card.Title>
              </Card>
            </Col>
          </div>
        </Row>
      </Container>
  );
}

export default CategoryGrid;