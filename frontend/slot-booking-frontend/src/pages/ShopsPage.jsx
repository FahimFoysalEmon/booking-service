import { useEffect, useState } from "react";
import api from "../lib/api";

export default function ShopsPage() {

    const [shops, setShops] = useState([]);
    const [error, setError] = useState("");

    useEffect(() => {
        async function loadShops() {
            try {
                const response = await api.get("/api/v1/public/shops");
                setShops(response.data);
            } catch {
                setError("Failed to load shops");
            }
        }

        loadShops();
    }, []);

    return (
        <div>
            <h1>Shops</h1>

            {error && <p>{error}</p>}

            {shops.length === 0 && !error && <p>No shops found</p>}

            <ul>
                {shops.map((shop) => (
                    <li key={shop.id}>
                        <strong>{shop.name}</strong> — {shop.address}
                    </li>
                ))}
            </ul>
        </div>
    );
}