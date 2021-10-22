package com.example.chloeSpringApi.integration;

import com.example.chloeSpringApi.models.Cat;
import com.example.chloeSpringApi.models.Comment;
import com.example.chloeSpringApi.repos.CatRepo;
import com.example.chloeSpringApi.repos.CommentRepo;
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

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //forces hibernate to drop the tables before the test runs
@ActiveProfiles("test")

public class CommentIT {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private CommentRepo commentRepo;

  @Autowired
  private CatRepo catRepo;

  private Cat savedCat;

  @BeforeEach
  public void setup() {
      Cat newCat = new Cat();
      newCat.setBreed("Cat");
      newCat.setDescription("mew mew");
      newCat.setPersonality("meow");
      // ! saved cat will be a real cat, with an ID.
      savedCat = catRepo.save(newCat);

      // ! I'm now adding my one cat to my comments.
      Comment comment = new Comment();
      comment.setBody("Mew mew.");
      comment.setCat(savedCat);
      Comment comment2 = new Comment();
      comment2.setBody("I love cats.");
      comment2.setCat(savedCat);

      commentRepo.saveAll(List.of(comment, comment2));
  }

  @Test
  public void postComment_ReturnsComment_Success() throws Exception {
    // given
    String expected = "TDD is great!";
    Comment comment = new Comment();
    comment.setBody(expected);
    comment.setCat(savedCat);
    RequestBuilder request =
        MockMvcRequestBuilders.post("/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(comment));

    // when
    ResultActions resultActions = mockMvc.perform(request);

    // then
    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(3))
        .andExpect(jsonPath("$.body").value(expected));
  }

  @Test
  public void postComment_ReturnsException_BadRequest() throws Exception {
    Comment comment = new Comment();

    RequestBuilder request =
        MockMvcRequestBuilders.post("/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(comment));

    mockMvc.perform(request)
            .andExpect(status()
                    .isBadRequest());
  }

   @Test
    public void getComments_ReturnsComments_Success() throws Exception {
        // given
        RequestBuilder request = MockMvcRequestBuilders
        .get("/comments");

        // when
        ResultActions resultActions = mockMvc.perform(request);

       // then
       MvcResult mvcResult = resultActions
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(1))
               .andReturn();
       // .andExpect(jsonPath("$[1].id").value(2))
       // .andExpect(jsonPath("$[0].body").value("The cake is a lie."))
       // .andExpect(jsonPath("$[1].body").value("I love to test."));

       System.out.println(mvcResult.getResponse().getContentAsString());

    }
}

