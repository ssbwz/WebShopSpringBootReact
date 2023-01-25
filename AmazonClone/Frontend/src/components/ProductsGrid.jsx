import './ProductCard.Sytle.css'
import '../Styles/ProductsGrid.Style.css'
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import ProductCard from './ProductCard';

function ProductsGrid(props) {
  return (
    <div>
    <Container>
    <section className="section-products">
    <div className="container">
    <div className="row justify-content-center text-center">
						<div className="col-md-8 col-lg-6">
								<div className="header">
										<h2>{props.productsType} Products</h2>
								</div>
						</div>
				</div>
    <Row xs={1} md={3} className="g-4">
      {props.products.map(product => (
        <Col id="ProductCard"key={product.id}>		
        <ProductCard product={product}/>
        </Col>
      ))}
    </Row>
    </div>
    </section>
    </Container>

    </div>
  );
}

export default ProductsGrid;