package equinton.dev.kata_horse.domain.service;

import equinton.dev.kata_horse.domain.model.Race;

public interface RaceEventRepository {

    void addRNewRaceCreatedEnvent(Race newRaceCreated);
}
