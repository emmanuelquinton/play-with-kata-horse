package equinton.dev.kata_horse.domain.service;

import equinton.dev.kata_horse.domain.model.Race;
import equinton.dev.kata_horse.persistence.repository.RaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public final class CreateRaceService {


    private final RaceRepository raceRepository;
    private final RaceEventRepository raceEventRepository;


    public static void checkRunnerNumbers(List<Integer> runnerNumbers) {
        var isRunnerNumbersNotStartFrom1 = runnerNumbers.get(0) != 1;
        if (isRunnerNumbersNotStartFrom1) {
            throw new IllegalArgumentException("Runner numbers do not start from 1");
        }
        for (int i = 1; i < runnerNumbers.size(); i++) {
            if (runnerNumbers.get(i) != runnerNumbers.get(i - 1) + 1) {
                throw new IllegalArgumentException("Runner numbers are not consecutive");
            }
        }
    }

    public Race create(Race race) {
        Race newRace = raceRepository.save(race);
        raceEventRepository.addRNewRaceCreatedEnvent(newRace);
        return newRace;
    }
}
