describe('Authorization', () => {
    it('login', () => {
      cy.visit('http://localhost:3000/login')
      cy.get('#formBasicEmail')
      .type('c@d.n')
      cy.get('#formBasicPassword')
      .type('123s')
      cy.get('#loginButton').click()   
      cy.contains('Please check your credentials')
    })
  })