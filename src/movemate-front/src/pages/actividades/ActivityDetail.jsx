import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { GoogleMap, LoadScript, Marker } from '@react-google-maps/api';
import '../../styles_pages/ActivityDetail.css'; 
import logo from '../../assets/logo_sin_fondo.png';

const ActivityDetail = () => {
    const { idActividad } = useParams();
    const [numReservas, setNumReservas] = useState(null);
    const [actividad, setActividad] = useState(null);
    const navigate = useNavigate();
    const token = localStorage.getItem('token');
    const id = localStorage.getItem('id');
    const role = localStorage.getItem('role');

    const googleMapsApiKey = "AIzaSyDtU0p-YmcxsPpfPK-lDd-l5nDrq3ufQgE"; 

    useEffect(() => {
        fetch(`http://localhost:8080/actividad/${idActividad}/`, {
            headers: {
            Authorization: `Bearer ${token}`
            }
        })
        .then(async res => {
            const text = await res.text();
            try {
            const json = JSON.parse(text);
            setActividad(json);

            fetch(`http://localhost:8080/actividad/${idActividad}/reservas`, {
                headers: {
                Authorization: `Bearer ${token}`
                }
            })
            .then(res => res.json())
            .then(data => setNumReservas(data))
            .catch(err => console.error("Error al obtener reservas:", err));
        } catch (error) {
            console.error("Respuesta no es JSON válido:", text);
            alert("La actividad no existe o hubo un error al procesarla.");
        }
        })
        .catch(err => {
            console.error("Error de red o servidor:", err);
            alert("Error de red o servidor");
        });
    }, [idActividad, token]);

    const handleReservar = () => {
        fetch(`http://localhost:8080/usuarios/${id}/${idActividad}/reservar`, {
        method: 'POST',
        headers: {
            Authorization: `Bearer ${token}`
        }
        })
        .then(res => {
            if (!res.ok) throw new Error('Error al reservar');
            return res.json();
        })
        .then(message => {
            alert(message);
            navigate('/my-reservations');
        })
        .catch(error => {
            console.error('Error al reservar:', error);
            alert('No se pudo realizar la reserva.');
        });
    };

    if (!actividad) return <p className="actividad-detalle-page">Cargando actividad...</p>;
    console.log("Actividad recibida:", actividad); 
    return (
        <div className="actividad-detalle-page">
            <h1 className="actividad-titulo">Detalles de la Actividad</h1>
            <h1 className="actividad-titulo">{actividad.nombre}</h1>

            <div className="actividad-imagen">
                <img
                    className="actividad-img"
                    src={`data:image/jpeg;base64,${actividad.fotoBase64}`}
                    alt="Imagen de la actividad"
                />
            </div>
            <div className="actividad-info">
                <p><strong>Descripción:</strong> {actividad.descripcion}</p>
                <p><strong>Fecha:</strong> {new Date(actividad.fechaHora).toLocaleString()}</p>
                <p><strong>Categoría:</strong> {actividad.categoria}</p>
                <p><strong>Precio:</strong> {actividad.precio} €</p>
                <p><strong>Aforo:</strong> {numReservas !== null ? `${numReservas} / ${actividad.aforoMaximo}` : 'Cargando...'}</p>
                <p><strong>Ubicación:</strong> {actividad.ubicacion}</p>
                <p><strong>Coordenadas:</strong> {actividad.lat && actividad.lon ? `${actividad.lat}, ${actividad.lon}` : 'No disponible'}</p>
                {role === 'usuario' && (
                    <button className="reservar-btn" onClick={handleReservar}>Reservar</button>
                )}
            </div>
            <div className="mapa-actividad-container">
                <LoadScript googleMapsApiKey={googleMapsApiKey}>
                    <GoogleMap
                        center={{ lat: actividad.lat || 40.4168, lng: actividad.lon || -3.7038 }}
                        zoom={15}
                        mapContainerClassName="mapa-actividad"
                    >
                        <Marker position={{ lat: actividad.lat, lng: actividad.lon }} title={actividad.nombre} />
                    </GoogleMap>
                </LoadScript>
            </div>
        </div>
    );
};

export default ActivityDetail;
