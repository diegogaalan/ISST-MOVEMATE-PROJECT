import { BrowserRouter as Router, Routes, Route, useLocation } from "react-router-dom";
import { useEffect, useState } from "react";

// Importa tus componentes y páginas aquí
import SplashScreen from "./pages/login/SplashScreen";
import AuthSelection from "./pages/login/AuthSelection";
import Login from "./pages/login/Login";
import RegisterUser from "./pages/registro/RegisterUser";
import RegisterOrganizer from "./pages/registro/RegisterOrganizer";
import HomeUser from "./pages/home/HomeUser";
import HomeOrganizer from "./pages/home/HomeOrganizer";
import SearchActivities from "./pages/actividades/SearchActivities";
import ActivityDetail from "./pages/actividades/ActivityDetail";
import Profile from "./pages/perfil/Profile";
import MyReservations from "./pages/reservas/MyReservations";
import MyActivities from "./pages/actividades/MyActivities";
import AddActivity from "./pages/actividades/AddActivity";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import Organizer from "./pages/registro/Organizer";
import MapaActividades from './components/MapaActividades'; 


function AppContent() {
    const location = useLocation();
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    // Verifica si el usuario está autenticado
    useEffect(() => {
        const token = localStorage.getItem("token");
        setIsLoggedIn(!!token);
    }, []);

    // Define las rutas en las que NO se debe mostrar el Navbar
    const noNavbarRoutes = ["/", "/auth", "/login", "/registro", "/registro/usuario", "/registro/organizador"];

    // Determina si se debe mostrar el Navbar
    const shouldShowNavbar = isLoggedIn && !noNavbarRoutes.includes(location.pathname);

    return (
    <>
        {shouldShowNavbar && <Navbar />}
        <div style={{ paddingTop: "60px" }}>
        <Routes>
            <Route path="/" element={<SplashScreen />} />
            <Route path="/auth" element={<AuthSelection />} />
            <Route path="/registro" element={<Organizer />} />
            <Route path="/login" element={<Login />} />
            <Route path="/registro/usuario" element={<RegisterUser />} />
            <Route path="/registro/organizador" element={<RegisterOrganizer />} />
            <Route path="/usuario/home" element={<HomeUser />} />
            <Route path="/organizador/home" element={<HomeOrganizer />} />
            <Route path="/search" element={<SearchActivities />} />
            <Route path="/actividad/:idActividad" element={<ActivityDetail />} />
            <Route path="/profile/:id" element={<Profile />} />
            <Route path="/my-reservations" element={<MyReservations />} />
            <Route path="/my-activities" element={<MyActivities />} />
            <Route path="/add-activity" element={<AddActivity />} />
        </Routes>
        </div>
        <Footer />
    </>
    );
}
function App() {
  // El componente Router envuelve la aplicación para habilitar el enrutamiento
  // y el componente AppContent contiene la lógica de las rutas y el estado de autenticación

    return (
        <Router>
            <AppContent />
        </Router>
    );
}

export default App;
