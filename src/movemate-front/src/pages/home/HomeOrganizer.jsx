import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import "../../styles_pages/HomeOrganizer.css";
import { Search, MapPin } from "lucide-react";

const HomeOrganizer = () => {
    const [nombre, setNombre] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem("token");
        const role = localStorage.getItem("role");
        const id = localStorage.getItem("id");

        if (!token || !role || !id) {
            alert("No has iniciado sesión");
            navigate("/login");
            return;
        }

        const url = role === "usuario"
            ? `http://localhost:8080/usuarios/profile/${id}`
            : `http://localhost:8080/organizadores/profile/${id}`;

        fetch(url, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
            .then(res => res.json())
            .then(data => {
                setNombre(data.nombre);
            })
            .catch(err => {
                console.error("Error al obtener el nombre:", err);
            });
    }, []);

    return (
        <div className="home-page">
            <div className="header-section">
                <h1>👋 Hola {nombre}!</h1>
                <p className="subtext">Encuentra todas las actividades que quieras para cualquier ocasión</p>
            </div>
            <div className="search-container">
                <Search size={20} className="search-icon" />
                <input
                    type="text"
                    placeholder="Buscar actividad de otro organizador"
                    className="search-input"
                />
            </div>

            <div className="section">
                <h2 className="section-title">📌 Categorías de tus actividades</h2>
                <div className="categorias">
                    <span>⚽ Fútbol</span>
                    <span>🎾 Pádel</span>
                    <span>🥊 Boxeo</span>
                    <span>🏋️‍♀️ Alt...</span>
                </div>
            </div>

            <div className="section">
                <h2 className="section-title">🌍 Explora otras actividades</h2>
                <Link to="/search" className="map-card">
                    <div className="icon-container">
                        <MapPin size={28} color="#ff2f75" />
                    </div>
                    <div className="map-text">
                        <strong>Cerca a mí</strong>
                        <p>Explora las actividades de otros organizadores cerca a tu ubicación</p>
                    </div>
                </Link>
            </div>
        </div>
    );
};

export default HomeOrganizer;
