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
      const response = await api.post("/api/v1/auth/login", {
        email,
        password,
      });
      saveToken(response.data.accessToken);
      navigate("/");
      console.log("Login Succesful");

    } catch (error) {
      const message = error.response?.data?.message || "Login failed";
      setError(message);

      setTimeout(() => {
        setError("");
      }, 3000);
    }
  }

  return (
    <div className="auth-page">

      {error && <div className="auth-toast">{error}</div>}

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
          <h1>What you can do here!</h1>
          <p>Book a shop visit in a few clicks.</p>
          <p>1. Choose a shop</p>
          <p>2. Pick a service and time</p>
          <p>3. Confirm your booking</p>
        </div>



      </div>



    </div>
  )
}