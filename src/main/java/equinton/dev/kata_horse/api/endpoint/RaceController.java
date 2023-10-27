package equinton.dev.kata_horse.api.endpoint;


import equinton.dev.kata_horse.api.dto.RaceDto;
import equinton.dev.kata_horse.api.dto.RunnerDto;
import equinton.dev.kata_horse.api.mapper.RaceMapper;
import equinton.dev.kata_horse.domain.model.Race;
import equinton.dev.kata_horse.domain.service.CreateRaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RaceController implements RacesApi{

    private final CreateRaceService createRaceService;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<RaceDto> createRace(RaceDto raceDto) {
        var runnerNumbers = raceDto.getRunners().stream().map(RunnerDto::getNumber).sorted().toList();


        CreateRaceService.checkRunnerNumbers(runnerNumbers);


        //
        var aRace = RaceMapper.INSTANCE.toModel(raceDto);
        var savedRace = createRaceService.create(aRace);
        return ResponseEntity.status(HttpStatus.CREATED).body(RaceMapper.INSTANCE.toDto(savedRace));
    }


    //...
    @ExceptionHandler({IllegalArgumentException.class })
    public ResponseEntity<Object> handleException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
