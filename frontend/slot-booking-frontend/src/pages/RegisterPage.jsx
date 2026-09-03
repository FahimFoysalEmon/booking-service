import { useState } from "react"; //to remember the state of the form
import { useNavigate } from "react-router-dom"; //to navigate to the home page
import api from "../lib/api";
import { saveToken } from "../lib/token"; //to save the token to the local storage
import { Container, Row, Col, Card, Button, Form, Alert, Spinner, InputGroup, FormControl, FormLabel, Toast } from "react-bootstrap";


export default function RegisterPage() {

  const navigate = useNavigate();
  const [fullName, setFullName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [role, setRole] = useState("CUSTOMER");
  const [error, setError] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");
    if (password !== confirmPassword) {
      setError("Passwords do not match");
      return;
    }
    try {
      await api.post("/api/v1/auth/register", {
        fullName,
        email,
        phone,
        password,
        confirmPassword,
        role,
      });
      navigate("/login");
      console.log("Registration Successful");
    } catch (error) {
      setTimeout(() => setError(""), 3000);
      setError(error.response?.data?.message || "Registration failed");
    }
  }


  return (
    <Container fluid className="min-vh-100">

      <Row className="min-vh-100">


        {/* FIRST COLUMN */}
        <Col md={6} className="d-flex align-items-center justify-content-center p-4">

<div style={{width : "100%", maxWidth : "420px"}}>

          <h1 >Register</h1>


          <Card className="border-0 shadow p-4">
          <form onSubmit={handleSubmit}>
            <Form.Group className="mb-3">
              <Form.Label>Full Name</Form.Label>
              <Form.Control
                type="text"
                value={fullName}
                onChange={(e) => setFullName(e.target.value)}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Phone</Form.Label>
              <Form.Control
                type="text"
                value={phone}
                onChange={(e) => setPhone(e.target.value)}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Password</Form.Label>
              <InputGroup>
              <Form.Control
                type={showPassword ? "text" : "password"}
                value={password}
                onChange={(e) => setPassword(e.target.value)}
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
            <Form.Group className="mb-3">
              <Form.Label>Confirm Password</Form.Label>
              <InputGroup>
              <Form.Control
                type={showConfirmPassword ? "text" : "password"}
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
              />
              <Button
                variant="outline-secondary"
                type="button"
                onClick={() => setShowConfirmPassword(!showConfirmPassword)}
              >
                {showConfirmPassword ? "Hide" : "Show"}
              </Button>
              </InputGroup>
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Role</Form.Label>
              <Form.Select value={role} onChange={(e) => setRole(e.target.value)}>
                <option value="CUSTOMER">Customer</option>
                <option value="SHOP_OWNER">Shop Owner</option>
              </Form.Select>
            </Form.Group>
            <Button className="bg-success" type="submit">Register</Button>
          </form>
          </Card>

          <Toast autohide delay={3000} show={!!error} bg="danger" className="position-fixed top-0 end-0 m-3 text-white" onClose={() => setError("")}>
            <Toast.Body>
              {error}
            </Toast.Body>
          </Toast>
          </div>
          


        </Col>


        {/* SECOND COLUMN */}

        <Col md={6} className="d-flex bg-success text-white align-items-center p-5">
          <h1>Join BarBook!</h1>

        </Col>

      </Row>



    </Container>
  );




}