package equinton.dev.kata_horse.api.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import equinton.dev.kata_horse.api.dto.RaceDto;
import equinton.dev.kata_horse.api.dto.RunnerDto;
import equinton.dev.kata_horse.api.mapper.RaceMapper;
import equinton.dev.kata_horse.domain.model.Race;
import equinton.dev.kata_horse.domain.service.CreateRaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {RaceController.class})
class PostRaceITest {

    private static final String POST_RACE_URL = "/api/v1/races";
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CreateRaceService createRaceService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;



    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    @Test
    void should_create_create_offer() throws Exception {
        // given
        var raceName = "C2 PRIX ALHENA";
        var raceNumber = 4;
        var raceDate = "2023-10-24";
        RaceDto aRace = new RaceDto(raceName, raceNumber, raceDate);
        RunnerDto runner1 = new RunnerDto("Aubepine", 1);
        RunnerDto runner2 = new RunnerDto("Alber", 2);
        RunnerDto runner3 = new RunnerDto("Black", 3);
        aRace.addRunnersItem(runner1).addRunnersItem(runner2).addRunnersItem(runner3);

        var raceToSave = RaceMapper.INSTANCE.toModel(aRace);
        BDDMockito.given(createRaceService.create(raceToSave))
                        .willReturn(raceToSave);

        // when
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post(POST_RACE_URL)
                                .content(objectMapper.writeValueAsBytes(aRace))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.name").value(raceName))
                .andExpect(jsonPath("$.number").value(raceNumber))
                .andExpect(jsonPath("$.onDate").value(raceDate));
        ;

        // then
    }

    @Test
    void should_post_offer_return_status_400_when_body_is_null() throws Exception {
        // given

        // when
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post(POST_RACE_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        // then
    }

    @Test
    void should_post_offer_return_status_400_when_race_has_fewer_than_3_runners() throws Exception {
        // given
        RaceDto aRace = new RaceDto("C2 PRIX ALHENA", 4, "2023-10-24");
        RunnerDto runner1 = new RunnerDto("Aubepine", 1);
        RunnerDto runner2 = new RunnerDto("Alber", 2);

        aRace.addRunnersItem(runner1).addRunnersItem(runner2);

        // when
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post(POST_RACE_URL)
                                .content(objectMapper.writeValueAsBytes(aRace))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        // then
    }

    @ParameterizedTest
    @ValueSource(strings = {"24/10/2024", "24-10-2024", "10-24-2024", "2024-13-24"})
    void should_post_offer_return_status_400_when_date_race_has_no_right_pattern(String date) throws Exception {
        // given
        RaceDto aRace = new RaceDto("C2 PRIX ALHENA", 4, date);
        RunnerDto runner1 = new RunnerDto("Aubepine", 1);
        RunnerDto runner2 = new RunnerDto("Alber", 2);
        RunnerDto runner3 = new RunnerDto("Black", 3);
        aRace.addRunnersItem(runner1).addRunnersItem(runner2).addRunnersItem(runner3);

        // when
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post(POST_RACE_URL)
                                .content(objectMapper.writeValueAsBytes(aRace))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        // then
    }

    @Test
    void should_post_offer_return_status_400_when_runner_number_not_follow_each_other() throws Exception {
        // given
        RaceDto aRace = new RaceDto("C2 PRIX ALHENA", 4, "2023-10-24");
        RunnerDto runner1 = new RunnerDto("Aubepine", 1);
        RunnerDto runner2 = new RunnerDto("Alber", 4);
        RunnerDto runner3 = new RunnerDto("Black", 3);
        aRace.addRunnersItem(runner1).addRunnersItem(runner2).addRunnersItem(runner3);

        // when
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post(POST_RACE_URL)
                                .content(objectMapper.writeValueAsBytes(aRace))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        // then
    }

    @Test
    void should_post_offer_return_status_400_when_runner_number_are_duplicated() throws Exception {
        // given
        RaceDto aRace = new RaceDto("C2 PRIX ALHENA", 4, "2023-10-24");
        RunnerDto runner1 = new RunnerDto("Aubepine", 1);
        RunnerDto runner2 = new RunnerDto("Alber", 2);
        RunnerDto runner3 = new RunnerDto("Black", 2);
        aRace.addRunnersItem(runner1).addRunnersItem(runner2).addRunnersItem(runner3);

        // when
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post(POST_RACE_URL)
                                .content(objectMapper.writeValueAsBytes(aRace))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        // then
    }

    @Test
    void should_post_offer_return_status_400_runner_number_not_start_from_one() throws Exception {
        // given
        RaceDto aRace = new RaceDto("C2 PRIX ALHENA", 4, "2023-10-24");
        RunnerDto runner1 = new RunnerDto("Aubepine", 2);
        RunnerDto runner2 = new RunnerDto("Alber", 4);
        RunnerDto runner3 = new RunnerDto("Black", 3);
        aRace.addRunnersItem(runner1).addRunnersItem(runner2).addRunnersItem(runner3);

        // when
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post(POST_RACE_URL)
                                .content(objectMapper.writeValueAsBytes(aRace))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        // then
    }
}
