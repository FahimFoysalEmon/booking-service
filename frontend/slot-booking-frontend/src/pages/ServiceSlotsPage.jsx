import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import api from "../lib/api";



export default function ServiceSlotsPage() {

    const { shopId, serviceId } = useParams();
    const [slots, setSlots] = useState([]);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);

    

    return (
        <div>
            <h1>Slots</h1>
            <h2>Shop ID : {shopId}</h2>
            <h2>Service ID : {serviceId}</h2>
        </div>
    );
}