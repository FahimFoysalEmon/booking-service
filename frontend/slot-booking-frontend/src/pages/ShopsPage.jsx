import { useEffect, useState } from "react";
import { Card, Spinner, Alert, Container } from "react-bootstrap";
import api from "../lib/api";

export default function ShopsPage() {

    const [shops, setShops] = useState([]);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        loadShops();
    }, []);


    async function loadShops() {
        try {
            const response = await api.get("/api/v1/public/shops");
            setShops(response.data);
        } catch {
            setError("Failed to load shops");
        } finally {
            setLoading(false);   // ← add this here
        }
    }

    if (loading) {
        return <Spinner animation="border" className="m-4" />;
    }

    return (
        <Container className="py-4">

            <h1>Shops</h1>

            {error && <Alert variant="danger">{error}</Alert>}
            {shops.length === 0 && !error && !loading && (
                <Alert variant="info">No shops found</Alert>
            )}

            {shops.map((shop) => (
                <Card key={shop.id} className="mb-3">
                    <Card.Body>
                        <Card.Title>{shop.name}</Card.Title>
                        <Card.Text>{shop.address}</Card.Text>
                    </Card.Body>
                </Card>
            ))}
        </Container>
    );
}