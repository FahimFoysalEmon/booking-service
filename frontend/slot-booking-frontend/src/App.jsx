import { Routes, Route } from "react-router-dom";
import MainLayout from "./layouts/MainLayout";
import HomePage from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import NotFoundPage from "./pages/NotFoundPage";
import ShopsPage from "./pages/ShopsPage";
import ShopServicesPage from "./pages/ShopServicesPage";


export default function App() {
  return (
    <Routes>

      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />


      <Route element={<MainLayout />} >
        <Route path="/" element={<HomePage />} />
        <Route path="/shops" element={<ShopsPage />} />
        <Route path="/shops/:shopId/services" element={<ShopServicesPage />} />
      </Route>


      <Route path="*" element={<NotFoundPage />} />
    </Routes>
  );
}
