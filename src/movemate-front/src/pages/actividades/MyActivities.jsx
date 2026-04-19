import "../../styles_pages/MyReservation.css";
import logo from '../../assets/logo_sin_fondo.png';
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";


const MyActivities = () => {
    const [activities, setActivities] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchActivities = async () => {
            try {
                const idOrg = localStorage.getItem("id");
                const token = localStorage.getItem("token");

                const res = await fetch(`http://localhost:8080/organizadores/${idOrg}/actividades`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });

                if (!res.ok) {
                    throw new Error("Error al obtener actividades");
                }

                const data = await res.json();
                setActivities(data);
            } catch (error) {
                console.error("Error al obtener actividades:", error);
            }
        };


        fetchActivities();
    }, []);

    const cancelarActividad = async (idActividad) => {
        const idOrg = localStorage.getItem("id");
        const token = localStorage.getItem("token");

        try {
            const res = await fetch(`http://localhost:8080/organizadores/${idOrg}/${idActividad}/eliminarActividad`, {
                method: "DELETE",
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            if (!res.ok) throw new Error("Error al eliminar la actividad");

            setActivities(prev => prev.filter(act => act.idActividad !== idActividad));
        } catch (err) {
            console.error("Error al cancelar la actividad:", err);
        }
    };

    return (
        <div className="reservations-page">
            <h1 className="title">Mis actividades</h1>
            <p className="subtitle">Revisa tus actividades vigentes y pasadas</p>
            <div className="bottom-button-container">
                <button className="reserve-button" onClick={() => navigate("/add-activity")}>
                    Añadir nueva actividad
                </button>
            </div>
            <div style={{ marginBottom: "20px" }}></div> {/* Espacio entre los dos div */}
            <div className="reservations-list">
                {activities.map((act, index) => (
                    <div className="reservation-card" key={index} onClick={() => navigate(`/actividad/${act.idActividad}`)} style={{ cursor: "pointer", marginBottom: "20px" }}>
                        <img
                            src={act.foto ? `data:image/jpeg;base64,${act.foto}` : logo}
                            alt={act.nombre}
                            className="reservation-img"/>
                        <div className="reservation-info">
                            <h2>{act.nombre}</h2>
                            <p>📍 {act.ubicacion}</p>
                            <p>📅 {new Date(act.fechaHora).toLocaleString()}</p>
                            <p>👥 {act.numReservas} / {act.aforoMaximo} plazas ocupadas</p>
                            <p className={`status ${act.confirmada ? "aprobada" : "pendiente"}`}>
                            {act.confirmada ? "Aprobada" : "Pendiente"}
                            </p>
                            <button className="delete-button"
                                onClick={(e) => {
                                    e.stopPropagation();
                                    cancelarActividad(act.idActividad);
                                }}
                            >
                                Cancelar actividad
                            </button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default MyActivities;
