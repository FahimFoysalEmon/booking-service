import { Link, Outlet, useNavigate } from "react-router-dom";
import { getToken, removeToken } from "../lib/token";

export default function MainLayout() {

  const navigate = useNavigate();
  const loggedIn = Boolean(getToken())

  function handleLogout() {
    removeToken();
    navigate("/login");
  }

  return (
    <div>
      <nav>
        <Link to="/">Home</Link>
        {!loggedIn && (
          <>
            <Link to="/login">Login</Link>
            <Link to="/register">Register</Link>
          </>
        )}

        <Link to="/shops">Shops</Link>

        {loggedIn && (
          <>
            <Link to="/my-bookings">My Bookings</Link>
            <button type="button" onClick={handleLogout}>
              Logout
            </button>
          </>
        )}
      </nav>
      <Outlet />
    </div>
  );
}