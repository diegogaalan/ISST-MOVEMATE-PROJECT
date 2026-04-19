package isst.isst_group21.movemate_back.repositories;

import isst.isst_group21.movemate_back.models.Organizador;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizadorRepository extends JpaRepository<Organizador, String> {
    Optional<Organizador> findByEmail(String email);
    Optional<Organizador> findByNumTelefono(String numTelefono);
}