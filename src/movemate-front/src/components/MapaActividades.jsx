import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import { GoogleMap, LoadScript, Marker, InfoWindow } from '@react-google-maps/api';

const MapaActividades = () => {
  const [actividades, setActividades] = useState([]);
  const [posicionUsuario, setPosicionUsuario] = useState({ lat: 40.4168, lng: -3.7038 }); // Madrid por defecto
  const [direccion, setDireccion] = useState("");
  const role = localStorage.getItem("role");
  const token = localStorage.getItem("token");
  const id = localStorage.getItem("id");
  const [selectedActividad, setSelectedActividad] = useState(null);

  const googleMapsApiKey = "AIzaSyDtU0p-YmcxsPpfPK-lDd-l5nDrq3ufQgE"; 

  const navigate = useNavigate();
  const handleReservar = async (idActividad) => {
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



  const buscarActividadesCercanas = async () => {
    try {
      const response = await fetch(`http://localhost:8080/actividad/cercanas?lat=${posicionUsuario.lat}&lon=${posicionUsuario.lng}`, {
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
          }
    });
      if (!response.ok) {
        throw new Error("Error al obtener actividades cercanas");
      }
      const data = await response.json();
      console.log("Actividades recibidas:", data);
      setActividades(data);
    } catch (error) {
      console.error("Error al cargar actividades cercanas", error);
    }
  };
  const geocodificarDireccion = async () => {
    console.log("Buscando dirección:", direccion); 
    try {
      const response = await fetch(`https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(direccion)}&key=${googleMapsApiKey}`);
      if (!response.ok) {
        throw new Error("Error al geocodificar dirección");
      }
      const data = await response.json();
      const location = data.results[0].geometry.location;
      setPosicionUsuario({ lat: location.lat, lng: location.lng });
    } catch (error) {
      console.error("Error al geocodificar dirección", error);
    }
  };

  useEffect(() => {
    buscarActividadesCercanas();
  }, [posicionUsuario]);

  const handleMapaClick = (e) => {
    setPosicionUsuario({ lat: e.latLng.lat(), lng: e.latLng.lng() });
  };

  return (
    <div>
      <div style={{ marginBottom: "10px" }}>
        <input
          type="text"
          value={direccion}
          onChange={(e) => setDireccion(e.target.value)}
          placeholder="Introduce una dirección"
          style={{ width: "300px", marginRight: "10px" }}
        />
        <button onClick={geocodificarDireccion}>Buscar</button>
      </div>

      <LoadScript googleMapsApiKey={googleMapsApiKey}>
        <GoogleMap
          center={posicionUsuario}
          zoom={13}
          mapContainerStyle={{ height: "600px", width: "100%" }}
          onClick={handleMapaClick}
        >
          <Marker position={posicionUsuario} icon="http://maps.google.com/mapfiles/ms/icons/blue-dot.png" label="Tú" />
          {actividades.map((a, idx) => (
          <Marker
          key={idx}
          position={{ lat: a.lat, lng: a.lon }} label= {a.nombre}
          onClick={() => {
            console.log("Actividad seleccionada:", a); 
            setSelectedActividad(a);
          }}
          
          />))}

{selectedActividad && (
  <InfoWindow
    position={{ lat: selectedActividad.lat, lng: selectedActividad.lon }}
    onCloseClick={() => setSelectedActividad(null)}
  >
    <div>
    <h3><Link to={`/actividad/${selectedActividad.idActividad}`}>{selectedActividad.nombre}</Link></h3>
      <p><strong>Aforo:</strong> {selectedActividad.numReservas} / {selectedActividad.aforoMaximo}</p>
      <p><strong>Categoría:</strong> {selectedActividad.categoria}</p>
      <p><strong>Fecha:</strong> {selectedActividad.fechaHora}</p>
      <p><strong>Precio:</strong> {selectedActividad.precio}€</p>
      <p><strong>Ubicación:</strong> {selectedActividad.ubicacion}</p>
      {role === 'usuario' && (
      <button onClick={() => handleReservar(selectedActividad.idActividad)}>RESERVAR</button>
      )}
    </div>
  </InfoWindow>
)}
        </GoogleMap>
      </LoadScript>
    </div>
  );
};

export default MapaActividades;