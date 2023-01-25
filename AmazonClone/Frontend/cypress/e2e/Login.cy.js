describe('Authorization', () => {
    it('login', () => {
      cy.visit('http://localhost:3000/login')
      cy.get('#formBasicEmail')
      .type('c@d.n')
      cy.get('#formBasicPassword')
      .type('123')
      cy.get('#loginButton').click()   
      cy.url().should('eq', 'http://localhost:3000/')
    })
  })