import { useEffect, useState } from "react";
import api from "../lib/api";
import { Container, Alert, Card, Spinner } from "react-bootstrap";

export default function MyBookingsPage() {


  const [bookings, setBookings] = useState([]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  async function loadBookings() {
    try {
      const response = await api.get("/api/v1/private/booking/me");
      setBookings(response.data);
    } catch {
      setError("Failed to load bookings");
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    loadBookings();
  }, []);

  if (loading) {
    return <Spinner animation="border" className="m-4" />;
  }

  return (
    <Container className="py-4">
      <h1>My Bookings</h1>

      {error && <Alert variant="danger">{error}</Alert>}

      {bookings.length === 0 && !error && (
        <Alert variant="info">No bookings found</Alert>
      )}

      {bookings.map((booking) => (
        <Card key={booking.id} className="mb-3">
          <Card.Body>
            <Card.Title>{booking.serviceName}</Card.Title>
            <Card.Text>
              {booking.startTime} — {booking.endTime}
            </Card.Text>
            <Card.Text>Status: {booking.status}</Card.Text>
          </Card.Body>
        </Card>
      ))}
    </Container>
  );

}