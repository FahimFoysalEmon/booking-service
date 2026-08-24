import { useState } from "react"; //to remember the state of the form
import { Link, useNavigate } from "react-router-dom"; //to navigate to the home page
import api from "../lib/api";
import { saveToken } from "../lib/token"; //to save the token to the local storage
import "./../styles/auth.css";


export default function LoginPage() {

  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");
    try {
      const response = await api.post("api/v1/auth/login", {
        email,
        password,
      });
      saveToken(response.data.accessToken);
      navigate("/");
      console.log("Login Succesful");

    } catch (error) {
      setError(error.response?.data?.message || "Login failed");
    }
  }

  return (
    <div className="auth-page">


      <div className="auth-shell">

        {/* LEFT */}
        <div className="auth-left">
          <div className="auth-card">
            <h1 className="auth-brand">BarBook</h1>
            <p className="auth-subtitle">Sign in to book your next visit!</p>


            <form className="auth-form" onSubmit={handleSubmit}>
              <div className="auth-field">
                <label>Email</label>
                <input
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                />
              </div>
              <div className="auth-field">
                <label>Password</label>
                <div className="auth-password-wrap">
                  <input
                    type={showPassword ? "text" : "password"}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                  />
                  <button className="auth-eye" type="button"
                    onClick={() => setShowPassword(!showPassword)}
                  >
                    {showPassword ? "Hide" : "Show"}
                  </button>

                </div>

              </div>
              <button className="auth-submit" type="submit">Login</button>
            </form>




            {/* Footer Section */}
            <p className="auth-footer">
              New here? <Link to="/register">Create account</Link>
            </p>


          </div>
        </div>



        {/* RIGHT */}
        <div className="auth-right">
          <h2>Welcome to BarBook</h2>
          <p>Book barbers, salons, and services with real-time slots.</p>
          <ul>
            <li>Browse nearby shops</li>
            <li>Pick service & time</li>
            <li>Manage your bookings</li>
          </ul>
        </div>



      </div>



    </div>
  )
}