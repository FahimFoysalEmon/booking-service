import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import api from "../lib/api";


export default function ShopServicesPage() {

    const { shopId } = useParams();
    const [ services , setServices ] = useState([]);
    const [ error , setError ] = useState("");
    const [ loading, setLoading ] = useState(true);

    return (

        <div>
            <h1>Services</h1>
            <p>Shop ID: {shopId}</p>
        </div>

    )
}