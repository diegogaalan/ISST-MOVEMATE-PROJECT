package isst.isst_group21.movemate_back.repositories;

import isst.isst_group21.movemate_back.models.Administrador;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, String> {
    // Buscar usuario por email (útil para login, comprobación, etc.)
    Optional<Administrador> findByEmail(String email);
    
}