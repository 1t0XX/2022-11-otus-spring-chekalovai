import Container from 'react-bootstrap/Container'
import Nav from 'react-bootstrap/Nav'
import Navbar from 'react-bootstrap/Navbar'
import './Header.css'

function Header() {
  return (
    <Navbar className="LibNavbar" collapseOnSelect expand="lg">
      <Container fluid>
        <Navbar.Brand className="navbar-brand " href="/">
          <img src="img/logo.png" width="50px"/>
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link href="/book">Книги</Nav.Link>
            <Nav.Link href="/author">Авторы</Nav.Link>
            <Nav.Link href="/genre">Жанры</Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  )
}

export default Header
