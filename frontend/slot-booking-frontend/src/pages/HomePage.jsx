import { Container, Button } from "react-bootstrap";
import { Link } from "react-router-dom";

export default function HomePage() {
  return (
    <Container className="py-5">
      <h1>Welcome to BarBook</h1>
      <p className="lead">Book a barber in a few clicks.</p>

      <Button as={Link} to="/shops" variant="success">
        Browse Shops
      </Button>
    </Container>
  );
}