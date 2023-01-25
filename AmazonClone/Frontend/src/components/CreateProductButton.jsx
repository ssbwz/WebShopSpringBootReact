import React, { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import authorisation from '../server/authorisation';
import FloatingLabel from 'react-bootstrap/FloatingLabel';
import productSever from '../server/productServer';


function CreateProductButton(props) {

  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [title, setTitle] = useState();
  const [description, setDescription] = useState();
  const [price, setPrice] = useState();
  const [category, setCategory] = useState();

  const [vTitleMessage, setVTitleMessage] = useState();
  const [vDescriptionMessage, setCDescriptionMessage] = useState();
  const [vPriceMessage, setVPriceMessage] = useState();
  const [vCategoryMessage, setVCategoryMessage] = useState();



  function createProduct() {

    const createProductRequest = {
      name: title,
      description: description,
      price: price,
      category: category
    }

    if (title.trim() === '') {
      setVTitleMessage("Please enter valid title")
      return;
    }
    if (description.trim() === '') {
      setCDescriptionMessage("Please enter valid desritption")
      return;
    }
    if (isNaN(price) || price.trim() === '') {
      setVPriceMessage("Please enter a valid price")
      return;
    }
    if (category === undefined) {
      setVCategoryMessage("Please select category")
      return;
    }

    productSever.createProduct(createProductRequest)
    setShow(!show)
    props.reloadProducts(!props.reloadValue)
    alert("Product got created successfully")
  }

  if (authorisation.isOwner()) {
    return (
      <>
        <Button variant="primary" onClick={handleShow}>
          Create product
        </Button>

        <Modal
          show={show}
          onHide={handleClose}
          backdrop="static"
          keyboard={false}
        >
          <Modal.Header closeButton>
            <Modal.Title>Create product</Modal.Title>
          </Modal.Header>
          <Form.Group className="mb-3">
            <Form.Label>Title</Form.Label>
            <Form.Control type="text" placeholder="Title" onChange={e => setTitle(e.target.value)} />
            <Form.Label>{vTitleMessage}</Form.Label>
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Description</Form.Label>
            <FloatingLabel controlId="floatingTextarea2" label="Description">
              <Form.Control
                as="textarea"
                placeholder="Leave a comment here"
                style={{ height: '100px' }}
                onChange={e => setDescription(e.target.value)}
              />
            </FloatingLabel>
            <Form.Label>{vDescriptionMessage}</Form.Label>
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Price</Form.Label>
            <Form.Control type="text" placeholder="Price" onChange={e => setPrice(e.target.value)} />
            <Form.Label>{vPriceMessage}</Form.Label>
          </Form.Group>
          <Form.Group className="mb-3">
            <FloatingLabel controlId="floatingSelect" label="Category">
              <Form.Select aria-label="Floating label select example" onChange={e => setCategory(e.target.value)}>
                <option>Category</option>
                <option value="Mens">Mens</option>
                <option value="Females">Females</option>
                <option value="Unisex">Unisex</option>
              </Form.Select>
            </FloatingLabel>
            <Form.Label>{vCategoryMessage}</Form.Label>
          </Form.Group>

          <Modal.Footer>
            <Button variant="primary" onClick={createProduct}>Create</Button>
          </Modal.Footer>
        </Modal>
      </>
    );
  }
  else
    return <></>
}

export default CreateProductButton;