package isst.isst_group21.movemate_back.repositories;

import isst.isst_group21.movemate_back.models.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    // Buscar usuario por email (útil para login, comprobación, etc.)
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByEmailAndPassword(String email, String password);
    Optional<Usuario> findByNumTelefono(String numTelefono);
    
}


