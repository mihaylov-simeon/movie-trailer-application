package com.moveiapp.final_exam_projectmovieapplication;

import com.moveiapp.final_exam_projectmovieapplication.controllers.MovieController;
import com.moveiapp.final_exam_projectmovieapplication.model.entities.Movie;
import com.moveiapp.final_exam_projectmovieapplication.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    @Test
    void getMovies() throws Exception {
        // Arrange
        List<Movie> movies = Collections.singletonList(new Movie());
        when(movieService.findAllMovies()).thenReturn(movies);

        // Act & Assert
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getSingleMovie() throws Exception {
        // Arrange
        String imdbId = "exampleImdbId";
        Movie movie = new Movie();
        when(movieService.findMovieByImdbId(imdbId)).thenReturn(Optional.of(movie));

        // Act & Assert
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/{imdbId}", imdbId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getSingleMovie_NotFound() throws Exception {
        // Arrange
        String imdbId = "nonExistingImdbId";
        when(movieService.findMovieByImdbId(imdbId)).thenReturn(Optional.empty());

        // Act & Assert
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/{imdbId}", imdbId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
