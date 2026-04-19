import "../../styles_pages/SearchActivities.css";
import React from "react";
import MapaActividades from "../../components/MapaActividades";
import { Link } from "react-router-dom";

const SearchActivities = () => {
    return (
        <div className="search-page">
            <h2>Buscar Actividades Cercanas</h2>



            <div className="map-container">
                <MapaActividades />
            </div>
        </div>

    );
};

export default SearchActivities;
