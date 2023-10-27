package equinton.dev.kata_horse.domain.service;


import equinton.dev.kata_horse.api.dto.RaceDto;
import equinton.dev.kata_horse.api.dto.RunnerDto;
import equinton.dev.kata_horse.domain.model.Race;
import equinton.dev.kata_horse.domain.model.Runner;
import equinton.dev.kata_horse.persistence.repository.RaceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

class CreateRaceServiceTest {


  @Test
  void should_create_race() {
    // given
    var raceEventRepository = Mockito.mock(RaceEventRepository.class);
    var raceRepository = Mockito.mock(RaceRepository.class);
    var createRaceService = new CreateRaceService(raceRepository, raceEventRepository);
    var aRacetoSave = createRace(null);
    UUID raceId = UUID.randomUUID();
    var theRaceSaved = createRace(raceId);

    BDDMockito.given(raceRepository.save(aRacetoSave)).willReturn(theRaceSaved);
    BDDMockito.doNothing().when(raceEventRepository).addRNewRaceCreatedEnvent(theRaceSaved);

    // when
    var actualRace = createRaceService.create(aRacetoSave);

    // then
    Assertions.assertThat(actualRace)
            .as("Check that the race has been saved")
            .isNotNull()
            .isEqualTo(theRaceSaved);

    BDDMockito.then(raceRepository).should(BDDMockito.times(1)).save(aRacetoSave);
    BDDMockito.then(raceEventRepository).should((BDDMockito.times(1))).addRNewRaceCreatedEnvent(theRaceSaved);

  }

  private static Race createRace(UUID id) {
    Runner runner1 = Runner.builder().name("Aubepine").number(1).build();
    Runner runner2 = Runner.builder().name("Alber").number(2).build();
    Runner runner3 = Runner.builder().name("Black").number(3).build();
    Set<Runner> runners = Set.of(runner1, runner2, runner3);

    Race aRace = new Race(id ,"C2 PRIX ALHENA", 4, LocalDate.parse("2023-10-24"),runners);
    return aRace;
  }
}
