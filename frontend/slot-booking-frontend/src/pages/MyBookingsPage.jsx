import { useEffect, useState } from "react";
import api from "../lib/api";
import { Spinner } from "react-bootstrap";

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
    return <h1>My Bookings</h1>;
  }

}