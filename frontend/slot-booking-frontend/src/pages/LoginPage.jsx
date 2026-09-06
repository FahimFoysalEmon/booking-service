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

        {/* LEFT */}
        <Col md={6} className="d-flex align-items-center justify-content-center p-4">
          <div style={{ width: "100%", maxWidth: "420px" }}>
            <h1 className="mb-1">BarBook</h1>
            <p className="text-muted mb-4">Sign in to book your next visit!</p>

            {error && <Alert variant="danger">{error}</Alert>}

            <Card className="border-0 shadow p-4">
              <Form onSubmit={handleSubmit}>
              </Form>
            </Card>
          </div>
        </Col>
      </Row>
    </Container>
  )
}