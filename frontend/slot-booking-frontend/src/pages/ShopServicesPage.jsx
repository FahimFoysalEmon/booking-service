import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { Container, Alert, Spinner, Card } from "react-bootstrap";
import api from "../lib/api";


export default function ShopServicesPage() {

    const { shopId } = useParams();
    const [services, setServices] = useState([]);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);

    async function loadServices() {
        try {
            const response = await api.get(`/api/v1/public/shops/${shopId}/services`);
            setServices(response.data)
        } catch {
            setError("Failed to load services")
        } finally {
            setLoading(false)
        }
    }

    useEffect(() => {
        loadServices();
    }, [shopId])

    if (loading) {
        return <Spinner animation="border" className="m-4" />;
    }


    return (

        <Container className="py-4">
            <h1>Services</h1>
            {error && <Alert variant="danger">{error}</Alert>}

            {services.length === 0 && !error && (
                <Alert variant="info">No services found</Alert>
            )}

            {services.map((service) => (
                <Card key={service.id} className="mb-3">
                    <Card.Body>
                        <Card.Title>{service.name}</Card.Title>
                        <Card.Text>
                            Price: {service.price} - {service.durationMinutes} minutes 
                        </Card.Text>
                    </Card.Body>
                </Card>

            ))}

        </Container>

    )
}