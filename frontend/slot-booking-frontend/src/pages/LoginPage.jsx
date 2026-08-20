import { useState } from "react"; //to remember the state of the form
import { useNavigate } from "react-router-dom"; //to navigate to the home page
import api from "../lib/api";
import saveToken from "../lib/token"; //to save the token to the local storage

export default function LoginPage() {

  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");
    try {
      const response = await api.post("api/v1/auth/login", {
        email,
        password,
       });
      saveToken(response.data.token);
      navigate("/");
    } catch (error) {
      setError(error.response.data.message);
    }
  }
}