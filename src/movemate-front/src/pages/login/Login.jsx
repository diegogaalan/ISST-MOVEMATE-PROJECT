import "../../styles_pages/Login.css";
import { useState } from "react";
import { useNavigate } from "react-router-dom";


function Login() {
    // Guardamos en el estado los valores de los inputs
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate(); // Hook para navegar entre rutas

    // Funcion de login y conexion con el backend
    const handleLogin = async () => {
        try {
            const response = await fetch("http://localhost:8080/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ 
                    email,
                    password,
                    esOrganizador: false }) 
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText);
            }

            const data = await response.json(); // Respuesta del backend
            console.log("Respuesta completa:", data);
            const token = data.token;
            const role = data.role.trim().toLowerCase();
            const id = data.id; // Obtenemos el ID del usuario desde la respuesta

            
            localStorage.setItem("token", token);
            localStorage.setItem("role", role);
            localStorage.setItem("id", id); // Guardamos el ID en el localStorage
            console.log("Token:", data.token);
            console.log("Role:", role);
            console.log("ID:", id);
            console.log("Role recibido del backend:", role);
            console.log("Contenido de localStorage:", localStorage.getItem("role"));

            // Redirección según rol
            
            window.location.href = role === "usuario" ? "/usuario/home" : "/organizador/home";
        } catch (error) {
            console.error("Error al iniciar sesión:", error);
            alert("Login incorrecto");
        }
    };

    return (
        <div className="login-page">
            <h1>Iniciar sesión</h1>
            <div className="login-form">
                <input
                    type="email"
                    placeholder="Correo"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                /><br />
                <input
                    type="password"
                    placeholder="Contraseña"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                /><br />
                <button onClick={handleLogin}>Entrar</button>
                <br />
            </div>
            <button onClick= {() => navigate("/registro")}>Registrarse</button>
        </div>
    );
}
export default Login;



