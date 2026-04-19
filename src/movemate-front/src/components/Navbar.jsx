import { Link, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import "../styles_components/Navbar.css";
import avatarIcon from "../assets/Avatar.icono.png";
import logo from "../assets/logo_sin_fondo.png";

// Iconos
import { ArrowLeft, MapPin, ClipboardList, Home } from "lucide-react";

const Navbar = () => {
    const [dropdownOpen, setDropdownOpen] = useState(false);
    const [animateOut, setAnimateOut] = useState(false);
    const [role, setRole] = useState(null);
    const [id, setId] = useState(null);
    const navigate = useNavigate();

    const homePath = role === "usuario" ? "/usuario/home" : "/organizador/home";

    useEffect(() => {
        const storedRole = localStorage.getItem("role");
        const storedId = localStorage.getItem("id");
        setRole(storedRole);
        setId(storedId);
    }, []);


    const toggleDropdown = () => {
        if (dropdownOpen) {
            setAnimateOut(true);
            setTimeout(() => {
                setDropdownOpen(false);
                setAnimateOut(false);
            }, 200); 
        } else {
            setDropdownOpen(true);
        }
    };

    const handleLogout = () => {
        localStorage.removeItem("token"); // Limpiar el token
        localStorage.removeItem("role"); // Limpiar el rol
        localStorage.clear(); // Limpiar el localStorage
        navigate("/login");

    }

    const closeDropdown = (callback) => {
        setAnimateOut(true);
        setTimeout(() => {
            setDropdownOpen(false);
            setAnimateOut(false);
            if (callback) callback();
        }, 200); // debe coincidir con la duración de la animación
    };

    return (
        <nav className="navbar">
            <div className="navbar-section left">
                <button className="back-button" onClick={() => navigate(-1)}>
                    <ArrowLeft size={24} />
                </button>
            </div>

            <ul className="navbar-section center">
                <li>
                    <Link to={role === "usuario" ? "/my-reservations" : "/my-activities"}>
                        <ClipboardList size={18} style={{ marginRight: "4px" }} />
                        {role === "usuario" ? "Mis Reservas" : "Mis Actividades"}
                    </Link>
                </li>
                <li>
                    <Link to={homePath}>
                        <Home size={18} style={{ marginRight: "4px" }} />
                        Inicio
                    </Link>
                </li>
                <li>
                    <Link to="/search">
                        <MapPin size={18} style={{ marginRight: "4px" }} />
                        Mapa
                    </Link>
                </li>
            </ul>

            <div className="navbar-section right">
                <div className="avatar" onClick={toggleDropdown}>
                    <img src={logo} alt="Avatar" />
                </div>
                {dropdownOpen && (
                    <div className={`dropdown ${animateOut ? "fade-out" : "fade-in"}`}>
                    <Link to="#" onClick={() => closeDropdown(() => navigate(`/profile/${id}`))}>Mi perfil</Link>
                    <button onClick={() => closeDropdown(handleLogout)}>Cerrar sesión</button>
                    </div>
                )}
            </div>
        </nav>
    );
}



export default Navbar;
