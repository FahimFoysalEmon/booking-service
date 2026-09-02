import { useState } from "react"; //to remember the state of the form
import { Link, useNavigate } from "react-router-dom"; //to navigate to the home page
import api from "../lib/api";
import { saveToken } from "../lib/token"; //to save the token to the local storage
import { Container, Row, Col, Form, Button, Alert, InputGroup, Card } from "react-bootstrap";

export default function LoginPage() {

  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState("");


  async function handleSubmit(e) {
    e.preventDefault();
    setError("");
    try {
      const response = await api.post("/api/v1/auth/login", {
        email,
        password,
      });
      saveToken(response.data.accessToken);
      navigate("/");
      console.log("Login Succesful");

    } catch (error) {
      const message = error.response?.data?.message || "Login failed";
      setError(message);

      setTimeout(() => {
        setError("");
      }, 3000);
    }
  }

  return (
    <Container fluid className="min-vh-100">
      <Row className="min-vh-100">

        <Col md={6} className="d-flex align-items-center justify-content-center p-4">
          <div style={{ width: "100%", maxWidth: "420px" }}>
            <h1 className="mb-1">BarBook</h1>
            <p className="text-muted mb-4">Sign in to book your next visit!</p>

            {error && <Alert variant="danger">{error}</Alert>}

            <Card className="border-0 shadow p-4">

              <Form onSubmit={handleSubmit}>

                <Form.Group className="mb-3" controlId="loginEmail">
                  <Form.Label>Email</Form.Label>
                  <Form.Control
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                    placeholder="Enter email"
                  />
                </Form.Group>

                <Form.Group className="mb-3" controlId="loginPassword">
                  <Form.Label>Password</Form.Label>
                  <InputGroup>
                    <Form.Control
                      type={showPassword ? "text" : "password"}
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                      required
                      placeholder="Enter password"
                    />
                    <Button
                      variant="outline-secondary"
                      type="button"
                      onClick={() => setShowPassword(!showPassword)}
                    >
                      {showPassword ? "Hide" : "Show"}
                    </Button>
                  </InputGroup>
                </Form.Group>

                <Button variant="primary" type="submit" className="bg-success w-100">
                  Login
                </Button>
              </Form>

            </Card>



            <p className="mt-3 mb-0">
              New here? <Link to="/register">Create account</Link>
            </p>
          </div>
        </Col>

        <Col md={6} className="bg-success text-white d-flex align-items-center p-5">
          <div>
            <h1 className="text-white mb-4">What you can do here!</h1>
            <p>Book a shop visit in a few clicks.</p>
            <p>1. Choose a shop</p>
            <p>2. Pick a service and time</p>
            <p>3. Confirm your booking</p>
          </div>
        </Col>
      </Row>




    </Container>
  )
}