describe('show products', () => {
  it('it visits the login page', () => {
    cy.visit('http://localhost:3000')
    cy.get('#ProductCard').should('be.visible')
  })
})