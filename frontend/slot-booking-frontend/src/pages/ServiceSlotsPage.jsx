import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import api from "../lib/api";
import { Container, Alert, Card, Spinner, Button } from "react-bootstrap";



export default function ServiceSlotsPage() {

    const { shopId, serviceId } = useParams();
    const [slots, setSlots] = useState([]);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);

    async function loadSlots() {
        try {
            const response = await api.get(`/api/v1/public/shops/${shopId}/services/${serviceId}/slots`);
            setSlots(response.data.slots)
        } catch {
            setError("Failed to load slots")
        } finally {
            setLoading(false)
        }
    }

    useEffect(() => {
        loadSlots();
    }, [shopId, serviceId])

    if (loading) {
        return <Spinner animation="border" className="m-4" />;
    }

    async function bookSlot(startTime) {
        setError("");
        try {
          await api.post("/api/v1/private/booking/create", {
            shopId: Number(shopId),
            serviceId: Number(serviceId),
            startTime,
          });
          await loadSlots();
        } catch (err) {
          setError(err.response?.data?.message || "Booking failed");
        }
      }

    return (
        <Container className="py-4">
            <h1>Slots</h1>

            {error && <Alert variant="danger">{error}</Alert>}

            {slots.length === 0 && !error && (
                <Alert variant="info">No slots found</Alert>
            )}

            {slots.map((slot) => (
                <Card key={slot.startTime} className="mb-3">
                    <Card.Body>
                        <Card.Text>
                            {slot.startTime} — {slot.endTime}
                        </Card.Text>
                        <Button variant="success" onClick={() => bookSlot(slot.startTime)}>Book</Button>
                    </Card.Body>
                </Card>
            ))}
        </Container>
    );
}