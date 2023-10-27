package equinton.dev.kata_horse.persistence.repository;

import equinton.dev.kata_horse.domain.model.Race;
import java.util.UUID;

import equinton.dev.kata_horse.domain.model.Runner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RunnerRepository extends JpaRepository<Runner, UUID> {}
