import "../../styles_pages/AddActivity.css";
import { useState } from "react";


const AddActivity = () => {
    const [form, setForm] = useState({
      nombre: "",
      descripcion: "",
      fechaHora: "",
      precio: "",
      ubicacion: "",
      categoria: "",
      aforoMaximo: "",
      foto: null
    });

    const [direccion, setDireccion] = useState("");
    const [lat, setLatitud] = useState(null);
    const [lon, setLongitud] = useState(null); 

    const googleMapsApiKey = "AIzaSyDtU0p-YmcxsPpfPK-lDd-l5nDrq3ufQgE";

    const geocodificarDireccion = async () => { 
        try {
          const response = await fetch(`https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(direccion)}&key=${googleMapsApiKey}`);
          if (!response.ok) {
            throw new Error("Error al geocodificar dirección");
          }
          const data = await response.json();
          const location = data.results[0].geometry.location;
          console.log("🧭 Coordenadas encontradas:", location);
          setLatitud(location.lat);
          setLongitud(location.lng);
            
          } catch (error) {
          console.error("Error al geocodificar dirección", error);
          alert("No se pudo obtener coordenadas.");
        }
      };
  
    const handleChange = (e) => {
      const { name, value } = e.target;
      setForm(prev => ({ ...prev, [name]: value }));
    };
  
    const handleFileChange = (e) => {
      setForm(prev => ({ ...prev, foto: e.target.files[0] }));
    };
  
    const handleSubmit = async (e) => {
      e.preventDefault();
    
      const idOrg = localStorage.getItem("id"); 
      const token = localStorage.getItem("token");
    
      let latitud = null;
      let longitud = null;
    
      try {
        const response = await fetch(`https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(direccion)}&key=${googleMapsApiKey}`);
        if (!response.ok) throw new Error("Error al geocodificar dirección");
    
        const data = await response.json();
        const location = data.results[0].geometry.location;
        latitud = location.lat;
        longitud = location.lng;
        console.log("📍 Coordenadas encontradas:", latitud, longitud);
      } catch (error) {
        console.error("Error al geocodificar dirección", error);
        alert("No se pudo obtener coordenadas.");
        return; // Salir si hay error en geocodificación
      }
    
      const formData = new FormData();
      for (const key in form) {
        if (form[key] !== null) {
          formData.append(key, form[key]);
        }
      }
      if (latitud !== null && longitud !== null) {
        formData.append("lat", latitud.toString());
        formData.append("lon", longitud.toString());
      }
    
      try {
        console.log("📦 Enviando datos con coordenadas:", latitud, longitud);
        const response = await fetch(`http://localhost:8080/organizadores/${idOrg}/misActividades/crearActividad`, {
          method: "POST",
          headers: {
            Authorization: `Bearer ${token}`
          },
          body: formData
        });
        
        console.log("FormData preview:");
        for (let pair of formData.entries()) {
            console.log(`${pair[0]}:`, pair[1]);
        }

        if (!response.ok) throw new Error("Error al crear actividad");
    
        const data = await response.json();
        alert("✅ Actividad creada con éxito");
        console.log(data);
      } catch (error) {
        console.error("Error:", error);
        alert("❌ No se pudo crear la actividad");
      }
    };
    
    return (
      <div className="add-activity">
        <h1>Añadir nueva actividad</h1>
        <form onSubmit={handleSubmit}>
          <input name="nombre" type="text" placeholder="Nombre de la actividad" onChange={handleChange} required />
          <input name="categoria" type="text" placeholder="Categoría" onChange={handleChange} required />
          <input name="precio" type="number" placeholder="Precio (€)" onChange={handleChange} required />
          <textarea name="descripcion" placeholder="Descripción" onChange={handleChange} required />
          <input name="fechaHora" type="datetime-local" onChange={handleChange} required />
          <input name="aforoMaximo" type="number" placeholder="Máximo de personas" onChange={handleChange} required/>
          <input name="ubicacion" type="text" placeholder="Ubicación" onChange={(e) => { handleChange(e); setDireccion(e.target.value); }} required />
          <button type="submit">Añadir</button>
        </form>
      </div>
    );
  };
  
  export default AddActivity;
