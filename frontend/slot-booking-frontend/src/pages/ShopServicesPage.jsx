import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { Spinner } from "react-bootstrap";
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

        <div>
            <h1>Services</h1>
            <p>Shop ID: {shopId}</p>
        </div>

    )
}