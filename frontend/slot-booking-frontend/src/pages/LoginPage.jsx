import { useState } from "react"; //to remember the state of the form
import { Link, useNavigate } from "react-router-dom"; //to navigate to the home page
import api from "../lib/api";
import { saveToken } from "../lib/token"; //to save the token to the local storage
import { Container, Row, Col, Form, Button, Alert, InputGroup, Card } from "react-bootstrap";
import loginBy from "../assets/barbook-login-bg.png";

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
    <Container fluid className="min-vh-100" style={{ backgroundImage: `url(${loginBy})`, backgroundSize: "cover", backgroundPosition: "center" }}>
      <Row className="min-vh-100">

      
          {/* LEFT */}
          <Col md={6}>
          </Col>

          {/* RIGHT */}
          <Col md={6} className="d-flex align-items-center p-4 justify-content-center">
            <div style={{ width: "100%", maxWidth: "420px" }}>


              {error && <Alert variant="danger">{error}</Alert>}

              <Card className="border-0 shadow p-4">
                <h1 className="mb-1">BarBook</h1>
                <p className="text-muted mb-4">Sign in to book your next visit!</p>

                <Form onSubmit={handleSubmit}>
                  <Form.Group className="mb-3">
                    <Form.Label className="fw-bold">Email</Form.Label>
                    <Form.Control
                      type="email"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                      placeholder="Type your email here">
                    </Form.Control>
                  </Form.Group>

                  <Form.Group className="mb-3">
                    <Form.Label className="fw-bold">Password</Form.Label>
                    <InputGroup>
                      <Form.Control
                        type={showPassword ? "text" : "password"}
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        placeholder="Type your password here">
                      </Form.Control>
                      <Button
                        variant="outline-secondary"
                        type="button"
                        onClick={() => setShowPassword(!showPassword)}
                      >
                        {showPassword ? "Hide" : "Show"}
                      </Button>
                    </InputGroup>
                  </Form.Group>

                  <Button type="submit" className="bg-success w-100">
                    Login
                  </Button>

                </Form>
                <p className="mt-3 mb-0">New here? <Link to="/register">Create account</Link></p>

              </Card>

            </div>
          </Col>

      </Row>
    </Container>
  )
}