import { useState } from "react";
import "../styles_components/SearchBar.css"; 

const SearchBar = ({ onSearch }) => {
    const [query, setQuery] = useState("");

    const handleSearch = () => {
        onSearch(query);
    };

    return (
        <div className="search-bar">
            <input 
                type="text" 
                placeholder="Buscar..." 
                value={query} 
                onChange={(e) => setQuery(e.target.value)} 
                className="search-input"
            />
            <button onClick={handleSearch} className="search-button">
                Buscar
            </button>
        </div>
    );
};


export default SearchBar;