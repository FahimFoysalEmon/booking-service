import { Link, Outlet, useNavigate } from "react-router-dom";
import { getToken, removeToken } from "../lib/token";
import { Container, Nav, Navbar } from "react-bootstrap";

export default function MainLayout() {

  const navigate = useNavigate();
  const loggedIn = Boolean(getToken())

  function handleLogout() {
    removeToken();
    navigate("/login");
  }

  return (
    <div>
      <Navbar bg="dark" variant="dark" expand="lg">
        <Container>
        <Navbar.Brand as={Link} to="/">BarBook</Navbar.Brand>
          <Nav>
            <Nav.Link as={Link} to="/">Home</Nav.Link>
            {!loggedIn && (
              <>
                <Nav.Link as={Link} to="/login">Login</Nav.Link>
                <Nav.Link as={Link} to="/register">Register</Nav.Link>
              </>
            )}
            <Nav.Link as={Link} to="/shops">Shops</Nav.Link>
            {loggedIn && (
              <>
                <Nav.Link as={Link} to="/my-bookings">My Bookings</Nav.Link>
                <Nav.Link as="button" onClick={handleLogout}>
                  Logout
                </Nav.Link>
              </>
            )}
          </Nav>
        </Container>
      </Navbar>
      <Outlet />
    </div>
  );
}