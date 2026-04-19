import { useEffect, useState, useRef } from "react";
import { useNavigate, Link } from "react-router-dom";
import "../../styles_pages/HomeUser.css";
import { Search, MapPin, Menu } from "lucide-react";

const HomeUser = () => {
    const [nombre, setNombre] = useState("");
    const [categorias, setCategorias] = useState([]);
    const [mostrarCategorias, setMostrarCategorias] = useState(false);
    const [actividadesFiltradas, setActividadesFiltradas] = useState([]);
    const [categoriaSeleccionada, setCategoriaSeleccionada] = useState(null);
    const [busquedaRealizada, setBusquedaRealizada] = useState(false);
    const navigate = useNavigate();
    const dropdownRef = useRef(); // 👉 Referencia para el desplegable

    const handleReservar = async (idActividad) => {
        const token = localStorage.getItem("token"); 
        const id = localStorage.getItem("id"); 
        try {
          const response = await fetch(`http://localhost:8080/usuarios/${id}/${idActividad}/reservar`, {
            method: "POST",
            headers: {
              Authorization: `Bearer ${token}`,
            },
          });
          if (!response.ok) {
            throw new Error("Error al reservar");
          }
          const message = await response.text(); 
          alert(message); 
          navigate("/my-reservations");
          
        } catch (error) {
          console.error("Error en la reserva:", error);
        }
      };
    const handleFiltrarPorCategoria = (categoria) => {
        setCategoriaSeleccionada(categoria);
        setBusquedaRealizada(true);
        setMostrarCategorias(false); 
        const token = localStorage.getItem("token");

        fetch(`http://localhost:8080/actividad/filtrar-categoria?categoria=${encodeURIComponent(categoria)}`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
            .then(res => res.text())
            .then(text => {
                try {
                    const data = JSON.parse(text);
                    setActividadesFiltradas(data);
                } catch (error) {
                    console.error("❌ No se pudo parsear JSON:", error.message);
                }
            })
            .catch(err => {
                console.error("❌ Error en la petición fetch:", err);
            });
    };

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

        fetch("http://localhost:8080/actividad/categorias", {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
            .then(res => res.json())
            .then(data => {
                setCategorias(data);
            })
            .catch(err => {
                console.error("Error al obtener categorías:", err);
            });
    }, []);

    useEffect(() => {
        const handleClickOutside = (event) => {
            if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
                setMostrarCategorias(false);
            }
        };

        if (mostrarCategorias) {
            document.addEventListener("mousedown", handleClickOutside);
        } else {
            document.removeEventListener("mousedown", handleClickOutside);
        }

        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, [mostrarCategorias]);

    return (
        <div className="home-page">
            <div className="header-section">
                <h1>👋 Hola {nombre}!</h1>
                <p className="subtext">Encuentra todas las actividades que quieras para cualquier ocasión</p>
            </div>

            <div className="boton-categorias-container" ref={dropdownRef}>
                <button className="boton-categorias" onClick={() => setMostrarCategorias(!mostrarCategorias)}>
                    <Menu size={28} color="#FFD700" />
                </button>
                {mostrarCategorias && (
                    <ul className="lista-categorias">
                        {categorias.map((cat, index) => (
                            <li key={index} onClick={() => handleFiltrarPorCategoria(cat)}>{cat}</li>
                        ))}
                    </ul>
                )}
            </div>

            <div className="search-container">
                <Search size={20} className="search-icon" />
                <input
                    type="text"
                    placeholder="Buscar actividad"
                    className="search-input"
                />
            </div>

            <div className="section">
                <h2 className="section-title">📌 Categorías</h2>
                <div className="categorias">
                    <span>⚽ Fútbol</span>
                    <span>🎾 Pádel</span>
                    <span>🥊 Boxeo</span>
                    <span>🏋️‍♀️ Alt...</span>
                </div>
            </div>

            {busquedaRealizada && (
                <div className="section">
                    <h2 className="section-title">
                        🎯 Actividades de {categoriaSeleccionada}
                    </h2>
                    {actividadesFiltradas.length > 0 ? (
                        <div className="activities-grid">
                            {actividadesFiltradas.map((act) => (
                                <div className="card" key={act.idActividad}>
                                    {act.fotoBase64 && act.fotoBase64.length > 100 && (
                                        <img
                                            src={`data:image/jpeg;base64,${act.fotoBase64}`}
                                            alt={act.nombre}
                                            className="card-img"
                                        />
                                    )}
                                    <h3 className="card-title">{act.nombre}</h3>
                                    <p className="card-description">{act.descripcion}</p>
                                    <button className="card-button" onClick={() => handleReservar(act.idActividad)}>Reservar</button>
                                </div>
                            ))}
                        </div>
                    ) : (
                        <p style={{ color: "#fdd9a0", fontSize: "1.2rem" }}>
                            No hay actividades disponibles para esta categoría.
                        </p>
                    )}
                </div>
            )}

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

export default HomeUser;