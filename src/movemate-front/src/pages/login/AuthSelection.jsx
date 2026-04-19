import { useNavigate } from "react-router-dom";
import Button from '../../components/Button';
import "../../styles_pages/AuthorSelection.css";
import logo from '../../assets/logo_sin_fondo.png'; 

const AuthSelection = () => {
    const navigate = useNavigate();

    return (
        <div className="auth-selection">
            <img src={logo} alt="Movemate Logo" className="auth-logo" />
            <Button 
                text="Unirme" 
                onClick={() => navigate("/registro")} 
                color="primary" 
            />
            <Button 
                text="Iniciar Sesión" 
                onClick={() => navigate("/login")} 
                color="primary" 
            />
        </div>
    );
};

export default AuthSelection;