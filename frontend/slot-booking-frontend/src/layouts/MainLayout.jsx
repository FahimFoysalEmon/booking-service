import { Link, Outlet } from "react-router-dom";

export default function MainLayout() {
  return (
    <div>
      <nav>
        <Link to="/">Home</Link>
        <Link to="/login">Login</Link>
        <Link to="/register">Register</Link>
        <Link to="/shops">Shops</Link>
      </nav>
      <Outlet />
    </div>
  );
}