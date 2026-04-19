import "../../styles_pages/RegisterOrganizer.css";

import { useState } from "react";
import { useNavigate } from "react-router-dom";

function RegistroOrganizador() {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        id: "",
        nombre: "",
        apellidos: "",
        email: "",
        numTelefono: "",
        password: "",
    });

    const [error, setError] = useState(""); // Mensaje de error
    const [fieldErrors, setFieldErrors] = useState({}); // Errores de validación

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault(); // Evita recargar la página
        try {
            const response = await fetch("http://localhost:8080/registro/organizador", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(formData)
            });

            if (!response.ok) {
                const errorData = await response.json();
            
                // 1 Si viene un array de errores de validación
                if (Array.isArray(errorData)) {
                    setFieldErrors(errorData);
                } 
                // 2 Si viene un solo mensaje
                else if (errorData.message) {
                    setError(errorData.message);
                } 
                else {
                    setError("Error desconocido");
                }
                return;
            }

            const organizador = await response.json();
            console.log("Organizador registrado:", organizador);
            alert("Organizador registrado. Se ha enviado un correo de bienvenida.");
            navigate("/login");
        } catch (error) {
            console.error("Error al registrar usuario:", error);
            setError("Ocurrió un error durante el registro. Intenta de nuevo.");
        }
    };

    return (
        <div className="register-page">
            <h2>Registro de Organizador</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
    
            <form onSubmit={handleSubmit}>
                <input
                type="text"
                name="nombre"
                placeholder="Nombre"
                value={formData.nombre}
                onChange={handleChange}
                required
                />
                {fieldErrors.nombre && <span style={{ color: "red" }}>{fieldErrors.nombre}</span>}
    
                <input
                type="text"
                name="apellidos"
                placeholder="Apellidos"
                value={formData.apellidos}
                onChange={handleChange}
                required
                />
                {fieldErrors.apellidos && <p style={{ color: "red" }}>{fieldErrors.apellidos}</p>}
    
                <input
                type="text"
                name="numTelefono"
                placeholder="Número de teléfono"
                value={formData.numTelefono}
                onChange={handleChange}
                required
                />
                {fieldErrors.numTelefono && <p style={{ color: "red" }}>{fieldErrors.numTelefono}</p>}
                
                <input
                type="text"
                name="id"
                placeholder="DNI"
                value={formData.id}
                onChange={handleChange}
                required
                />
                {fieldErrors.id && <p style={{ color: "red" }}>{fieldErrors.id}</p>}
    
                <input
                type="email"
                name="email"
                placeholder="Correo electrónico"
                value={formData.email}
                onChange={handleChange}
                required
                />
                {fieldErrors.email && <p style={{ color: "red" }}>{fieldErrors.email}</p>}
    
                <input
                type="password"
                name="password"
                placeholder="Contraseña"
                value={formData.password}
                onChange={handleChange}
                required
                />
                {fieldErrors.password && <p style={{ color: "red" }}>{fieldErrors.password}</p>}
    
                <button type="submit">Registrarse</button>
            </form>
        </div>
    );
}
export default RegistroOrganizador;

