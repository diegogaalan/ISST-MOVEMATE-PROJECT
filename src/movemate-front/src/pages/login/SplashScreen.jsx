import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import logo from "../../assets/logo_sin_fondo.png"; 
import "../../styles_pages/SplashScreen.css";


const SplashScreen = () => {
    const navigate = useNavigate();

    useEffect(() => {
        setTimeout(() => {
            navigate("/auth");
        }, 3000); // Redirige tras 3 segundos
    }, [navigate]);

    return (
        <div className="splash-screen">
            <img src={logo} alt="Movemate Logo" className="logo" />
        </div>
    );
};

export default SplashScreen;
