import "../../styles_pages/MyReservation.css";
import logo from '../../assets/logo_sin_fondo.png';
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";


const MyReservations = () => {

    const [reservations, setReservations] = useState([]);
const navigate = useNavigate();
const token = localStorage.getItem("token");
const idUsuario = localStorage.getItem("id");

const cancelarReserva = async (idReserva) => {
    try {
        console.log(`Llamando a cancelarReserva para ${idReserva}`);
        const res = await fetch(`http://localhost:8080/usuarios/${idUsuario}/${idReserva}/cancelarReserva`, {
            method: "DELETE",
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        console.log("Respuesta cancelar:", res.status);
        if (!res.ok) {
            const text = await res.text();
            console.error("Respuesta del backend:", res.status, text);
            throw new Error("Error al cancelar la reserva");
        }

        setReservations(prev => prev.filter(r => r.idReserva !== idReserva));
    } catch (error) {
        console.error("Error al cancelar:", error);
    }
};

useEffect(() => {
    const fetchReservas = async () => {
      try {
        const response = await fetch(`http://localhost:8080/usuarios/${idUsuario}/reservas`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        if (!response.ok) throw new Error("Error al cargar reservas");
        const data = await response.json();
        setReservations(data);
      } catch (error) {
        console.error("Error:", error);
      }
    };
  
    fetchReservas();
  }, []);


    return (
        <div className="reservations-page">
            <h1 className="title">Mis reservas</h1>
            <p className="subtitle">Revisa tus reservas vigentes y pasadas</p>
            <div className="reservations-list">
                {reservations.map((res, index) => (
                    <div className="reservation-card" key={index}>
                        <img src={res.fotoBase64 || logo} alt={res.actividadNombre} className="reservation-img" />
                        <div className="reservation-info">
                            <h2>{res.actividadNombre}</h2>
                            <p>👥 {res.numPersonas} &nbsp; 📅 {res.fechaActividad}</p>
                            <p className={`status ${res.estado.toLowerCase()}`}>{res.estado}</p>
                            <button className="cancel-button" onClick={() => cancelarReserva(res.idReserva)}>Cancelar</button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default MyReservations;

