describe('Adding product to a cart', () => {
    it('login', () => {
      cy.visit('http://localhost:3000/login')
      cy.get('#formBasicEmail')
      .type('c@d.n')
      cy.get('#formBasicPassword')
      .type('123')
      cy.get('#loginButton').click()
      cy.get('#addProductButton').click()
      cy.get('#overlay-example').should('be.visible')
    })
  })