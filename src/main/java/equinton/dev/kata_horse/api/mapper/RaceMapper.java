package equinton.dev.kata_horse.api.mapper;

import equinton.dev.kata_horse.api.dto.RaceDto;

import equinton.dev.kata_horse.api.dto.RunnerDto;
import equinton.dev.kata_horse.domain.model.Race;
import equinton.dev.kata_horse.domain.model.Runner;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RaceMapper {

    RaceMapper INSTANCE = Mappers.getMapper(RaceMapper.class);

    RaceDto toDto(Race race);

    Race toModel(RaceDto raceDto);


    RunnerDto toRunnerDto(Runner market);







}
