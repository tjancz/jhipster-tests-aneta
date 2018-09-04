package pl.pio.crm.repository;

import pl.pio.crm.domain.Sprawa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Sprawa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SprawaRepository extends JpaRepository<Sprawa, Long> {

}
