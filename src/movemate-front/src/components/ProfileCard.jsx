import "../styles_components/ProfileCard.css"; 

const ProfileCard = ({ name, email, image }) => {
    return (
        <div className="profile-card">
            <img src={image} alt={name} className="profile-img" />
            <h3 className="profile-name">{name}</h3>
            <p className="profile-email">{email}</p>
        </div>
    );
};


export default ProfileCard;