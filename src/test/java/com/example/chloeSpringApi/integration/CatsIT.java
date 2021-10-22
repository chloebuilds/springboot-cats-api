package com.example.chloeSpringApi.integration;

import com.example.chloeSpringApi.models.Cat;
import com.example.chloeSpringApi.repos.CatRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
// tells SB which profile to use
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
// Needed to get everything configured correctly
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// THis will tell hibernate to remove your database between EACH test.
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test") // This means our test is using the Test profile


public class CatsIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CatRepo catRepo;

    @Autowired
    private ObjectMapper objectMapper;
    // a serializer for converting java objects to json and vice versa

    @BeforeEach
    public void setup() {
        Cat ragdoll = new Cat();
        ragdoll.setBreed("Ragdoll");
        ragdoll.setDescription("Ragdolls love their people, greeting them at the door, following them around the house, and leaping into a lap or snuggling in bed whenever given the chance. They are the epitome of a lap cat, enjoy being carried and collapsing into the arms of anyone who holds them.");
        ragdoll.setPersonality("Affectionate, Friendly, Gentle, Quiet, Easygoing");
        ragdoll.setLifeSpan("12-17 years");

        Cat russianBlue = new Cat();
        russianBlue.setBreed("Russian Blue");
        russianBlue.setDescription("Russian Blues are very loving and reserved. They do not like noisy households but they do like to play and can be quite active when outdoors. They bond very closely with their owner and are known to be compatible with other pets.");
        russianBlue.setPersonality("Active, Dependent, Easy Going, Gentle, Intelligent, Loyal, Playful, Quiet");
        russianBlue.setLifeSpan("10-16 years");

        Cat britishShorthair = new Cat();
        britishShorthair.setBreed("British Shorthair");
        britishShorthair.setDescription("The British Shorthair is a very pleasant cat to have as a companion, ans is easy going and placid. The British is a fiercely loyal, loving cat and will attach herself to every one of her family members. While loving to play, she doesn't need hourly attention. If she is in the mood to play, she will find someone and bring a toy to that person. The British also plays well by herself, and thus is a good companion for single people.");
        britishShorthair.setPersonality("Affectionate, Easy Going, Gentle, Loyal, Patient, calm");
        britishShorthair.setLifeSpan("12-17 years");

        Cat birman = new Cat();
        birman.setBreed("Birman");
        birman.setDescription("The Birman is a docile, quiet cat who loves people and will follow them from room to room. Expect the Birman to want to be involved in what you’re doing. He communicates in a soft voice, mainly to remind you that perhaps it’s time for dinner or maybe for a nice cuddle on the sofa. He enjoys being held and will relax in your arms like a furry baby.");
        birman.setPersonality("Affectionate, Active, Gentle, Social");
        birman.setLifeSpan("14-15 years");

        catRepo.saveAll(List.of(ragdoll, russianBlue, britishShorthair, birman));
    }

    @Test
    public void helloWorld_ReturnString_Success() throws Exception {
        // ! when (given some conditions / we have a particular request)
        // Request builder lets you build requests.
        RequestBuilder request = MockMvcRequestBuilders.get("/hello");

        // ! given (we take some action / make that request)
        ResultActions resultActions = mockMvc.perform(request);

        // ! then (what's the result)
        // The result variable contains our content (Hello World!)
        MvcResult result = resultActions
                .andExpect(status().isOk()) // Check is the response ok (a 200)
                    .andReturn();

        String actual = result.getResponse().getContentAsString();
        String expected = "Hello World!";
        assertEquals(expected, actual);
    }
   @Test
    public void getCats_ReturnsCats_Success() throws Exception {
        // ! when (given some conditions / we have a particular request)
        // Request builder lets you build requests.
        RequestBuilder request = MockMvcRequestBuilders.get("/cats");

        // ! given (we take some action / make that request)
        ResultActions resultActions = mockMvc.perform(request);

        // ! then (what's the result)
        MvcResult result = resultActions
                .andExpect(status().isOk()) // Check is the response ok (a 200)
                // jsonPath lets you query json data
                // the $ starts from the top of our json response
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].breed").value("Ragdoll"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void postCats_ReturnsCats_Success() throws Exception {
        // given
        Cat burmese = new Cat();
        burmese.setBreed("Burmese");
        burmese.setDescription("Burmese love being with people, playing with them, and keeping them entertained. They crave close physical contact and abhor an empty lap. They will follow their humans from room to room, and sleep in bed with them, preferably under the covers, cuddled as close as possible. At play, they will turn around to see if their human is watching and being entertained by their crazy antics.");
        burmese.setPersonality("Active, Dependent, Easy Going, Gentle, Intelligent, Loyal, Playful, Quiet");
        burmese.setLifeSpan("15-16 years");

        RequestBuilder request = MockMvcRequestBuilders
                .post("/cats")
                // Say its json data:
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(burmese));
        // when
        ResultActions resultActions = mockMvc.perform(request);

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.breed").value("Russian Blue"));

        // We could also check the repo too.
        List<Cat> cats = (List<Cat>) catRepo.findAll();
        assertEquals(5, cats.size());
    }




    @Test
    public void getCat_ReturnsCat_ReturnsException_NotFound() throws Exception {
        // checks that an exception is actually thrown if there is an error
        //given
        RequestBuilder request = MockMvcRequestBuilders
                .get("/cats/6");
        //when
        ResultActions resultActions = mockMvc.perform(request);

        //then
        resultActions.andExpect(status().isNotFound());

        // ? We will use this when it comes to unit testing
//        assertThrows(
//                // First argument: exception class
//                ResponseStatusException.class,
//                // Second argument: lambda to run some code
//                () -> {
//                    ResultActions resultActions = mockMvc.perform(request);
//                }
//        );

    }
}
