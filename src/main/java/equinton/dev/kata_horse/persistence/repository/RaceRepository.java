package equinton.dev.kata_horse.persistence.repository;

import equinton.dev.kata_horse.domain.model.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RaceRepository extends JpaRepository<Race, UUID> {}
