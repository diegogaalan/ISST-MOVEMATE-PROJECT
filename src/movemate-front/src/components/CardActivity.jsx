import "../styles_components/CardActivity.css";

const CardActivity = ({ title, description, image, onReserve, numReservas, aforoMaximo }) => {
    return (
        <div className="card">
            <img src={image} alt={title} className="card-img" />
            <h3 className="card-title">{title}</h3>
            <p className="card-description">{description}</p>
            <p className="card-aforo">👥 {numReservas} / {aforoMaximo} plazas ocupadas</p>
            <button className="card-button" onClick={onReserve}>
                Reservar
            </button>
        </div>
    );
};


export default CardActivity;
