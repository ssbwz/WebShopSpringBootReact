describe('Create order', () => {
    it('login', () => {
        //Login in 
      cy.visit('http://localhost:3000/login')
      cy.get('#formBasicEmail')
      .type('c@d.n')
      cy.get('#formBasicPassword')
      .type('123')
      cy.get('#loginButton').click()
      //Adding product
      cy.get('#addProductButton').click()
      //Submiting order
      cy.get('#CartPagebutton').click()
      cy.get('#StreetField')
      .type('TestStraat')
      cy.get('#HouseNumberField')
      .type('11')
      cy.get('#PostcodeField')
      .type('5555 JR')
      cy.get('#CreateOrderButton').click()
      cy.contains('This cart is empty')
    })
  })