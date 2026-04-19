import "../..//styles_pages/Organizer.css";
import { useNavigate } from "react-router-dom";
import Button from '../../components/Button';

const Organizer = () => {
    const navigate = useNavigate();

    return (
        <div className="auth-selection">
            <h3>¿Que tipo de cuenta quieres tener?</h3>
            <p>Quiero ser un usuario</p>
            <Button 
                text="Usuario" 
                onClick={() => navigate("/registro/usuario")} 
                color="primary" 
                />
            <p>Quiero ser un organizador</p>
            <Button 
                text="Organizador" 
                onClick={() => navigate("/registro/organizador")} 
                color="primary" 
                />
        </div>
    );
};

export default Organizer;