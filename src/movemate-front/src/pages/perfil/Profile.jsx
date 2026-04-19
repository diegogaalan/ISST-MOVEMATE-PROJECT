import "../../styles_pages/Profile.css";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const Profile = () => {
    const navigate = useNavigate();
    const [nombre, setNombre] = useState("");
    const [apellidos, setApellidos] = useState("");
    const [email, setEmail] = useState("");
    const [numTelefono, setNumTelefono] = useState("");
    const [role, setRole] = useState("");
    const [id, setId] = useState("");
    const [errores, setErrores] = useState([]);

    useEffect(() => {
        const token = localStorage.getItem("token");
        const role = localStorage.getItem("role");
        const id = localStorage.getItem("id");
        
        if (!token || !role || !id) {
            alert("No has iniciado sesión");
            navigate("/login");
            return;
        }

        setRole(role);
        setId(id);

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
                setApellidos(data.apellidos);
                setEmail(data.email);
                setNumTelefono(data.numTelefono);
            })
            .catch(err => {
                console.error("Error al cargar perfil:", err);
                alert("Error al cargar el perfil");
            });
    }, []);

    const handleActualizar = async (e) => {
        e.preventDefault();

        
    // Validación de campos vacíos
    if (!nombre.trim() || !apellidos.trim() || !email.trim() || !numTelefono.trim()) {
        alert("Por favor, completa todos los campos antes de actualizar.");
        return;
    }

        const url = role === "usuario"
            ? `http://localhost:8080/usuarios/profile/${id}`
            : `http://localhost:8080/organizadores/profile/${id}`;

        const payload = {
            id: id,
            nombre: nombre,
            apellidos: apellidos,
            email: email,
            numTelefono: numTelefono,
            password: "12345678" // Requerido por backend
        };

        try {
            const response = await fetch(url, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${localStorage.getItem("token")}`
                },
                body: JSON.stringify(payload)
            });
    
            const data = await response.json();
    
            if (!response.ok) {
                if (Array.isArray(data)) {
                    setErrores(data); // Errores tipo lista
                } else if (data.errors) {
                    const erroresArray = Object.entries(data.errors).map(
                        ([campo, mensaje]) => `${campo}: ${mensaje}`
                    );
                    setErrores(erroresArray);
                } else if (data.message) {
                    setErrores([data.message]);
                } else {
                    setErrores(["Error desconocido al actualizar el perfil."]);
                }
                return;
            }
    
            setErrores([]); // Limpiar errores
            alert("Perfil actualizado correctamente");
        } catch (err) {
            console.error(err);
            setErrores(["Error al conectar con el servidor."]);
        }
    };

    const handleLogout = () => {
        localStorage.clear();
        navigate("/login");
    };

    const handleDeleteAccount = () => {
        const confirmed = window.confirm("¿Estás seguro de que deseas eliminar tu cuenta? Esta acción no se puede deshacer.");
        if (!confirmed) return;

        const url = role === "usuario"
        ? `http://localhost:8080/usuarios/profile/${id}`
        : `http://localhost:8080/organizadores/profile/${id}`;

        fetch(url, {
            method: "DELETE",
            headers: {
                "Authorization": `Bearer ${localStorage.getItem("token")}`
            }
        })
            .then(res => {
                if (!res.ok) throw new Error("Error al borrar la cuenta");
                alert("Cuenta eliminada correctamente");
                localStorage.clear();
                navigate("/login");
            })
            .catch(err => {
                console.error("Error eliminando cuenta:", err);
                alert("No se pudo eliminar la cuenta");
            });
    };

    return (
        <div className="profile-page">
            <div className="header">
                <h1>Mi perfil</h1>
                <p>Actualiza la información de tu perfil</p>
            </div>
            <div className="profile-info">
                <img
                    src="https://www.svgrepo.com/show/384674/account-avatar-profile-user-11.svg"
                    alt="avatar"
                    className="profile-avatar"
                />
                <p className="profile-role">{role === "usuario" ? "Cliente" : "Organizador"}</p>
                <p className="profile-email">{email}</p>
                <p className="profile-username">{id}</p>
            </div>
            {errores.length > 0 && (
                <div className="error-box">
                    <ul>
                        {errores.map((err, idx) => (
                        <li key={idx}>{err}</li>
                        ))}
                    </ul>
                </div>
            )}
            <form className="profile-form" onSubmit={handleActualizar}>
                <input type="text" placeholder="Nuevo Nombre" value={nombre} onChange={(e) => setNombre(e.target.value)}/>
                <input type="text" placeholder="Apellidos" value={apellidos} onChange={(e) => setApellidos(e.target.value)}/>
                <input type="email" placeholder="Correo electrónico" value={email} onChange={(e) => setEmail(e.target.value)}/>
                <input type="text" placeholder="Teléfono" value={numTelefono} onChange={(e) => setNumTelefono(e.target.value)}/>
                <div className="btn-update-container">
                    <button type="submit" className="btn-update">Actualizar</button>
                </div>
            </form>
            
            <button className="btn-logout" onClick={handleLogout}>Cerrar Sesión</button>
            <button className="btn-delete" onClick={handleDeleteAccount}>🗑️ Borrar cuenta</button>
        </div>
    );
};

export default Profile;
