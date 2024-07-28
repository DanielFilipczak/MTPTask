package com.example.mtptask.service;

import com.example.mtptask.exception.ListIsNullOrEmptyException;
import com.example.mtptask.exception.StarNotFoundException;
import com.example.mtptask.mapper.StarMapper;
import com.example.mtptask.model.Star;
import com.example.mtptask.model.dto.StarDTO;
import com.example.mtptask.repository.StarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class StarServiceTest {

    @Mock
    private StarRepository starRepository;

    @Mock
    private StarMapper mapper;

    @InjectMocks
    private StarService starService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addStar_ShouldSaveStar() {
        StarDTO starDTO = new StarDTO("Sirius", 42400L);
        Star star = new Star();
        star.setName("Sirius");
        star.setDistance(42400L);
        when(mapper.toStar(starDTO)).thenReturn(star);
        when(starRepository.save(star)).thenReturn(star);
        when(mapper.toDto(star)).thenReturn(starDTO);

        StarDTO result = starService.addStar(starDTO);

        assertEquals(starDTO, result);
        verify(starRepository, times(1)).save(star);
    }

    @Test
    void getStarById_ShouldReturnStar_WhenStarExists() {
        int id = 1;
        Star star = new Star();
        star.setName("Sirius");
        star.setDistance(8500L);
        StarDTO starDTO = new StarDTO("Sirius", 8500L);
        when(starRepository.findById(id)).thenReturn(Optional.of(star));
        when(mapper.toDto(star)).thenReturn(starDTO);

        StarDTO result = starService.getStarById(id);

        assertEquals(starDTO, result);
    }

    @Test
    void getStarById_ShouldThrowStarNotFoundException_WhenStarDoesNotExist() {
        int id = 1;
        when(starRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(StarNotFoundException.class, () -> starService.getStarById(id));
    }

    @Test
    void updateStar_ShouldUpdateStar_WhenStarExists() {
        int id = 1;
        Star star = new Star();
        star.setName("Sirius");
        star.setDistance(86700L);
        StarDTO updatedStarDTO = new StarDTO("Alpha Centauri", 43000L);
        when(starRepository.findById(id)).thenReturn(Optional.of(star));
        when(starRepository.save(star)).thenReturn(star);
        when(mapper.toDto(star)).thenReturn(updatedStarDTO);

        StarDTO result = starService.updateStar(updatedStarDTO, id);

        assertEquals(updatedStarDTO, result);
        verify(starRepository, times(1)).save(star);
    }

    @Test
    void updateStar_ShouldThrowStarNotFoundException_WhenStarDoesNotExist() {
        int id = 1;
        StarDTO starDTO = new StarDTO("Sirius", 43000L);
        when(starRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(StarNotFoundException.class, () -> starService.updateStar(starDTO, id));
    }

    @Test
    void deleteStarById_ShouldDeleteStar_WhenStarExists() {
        int id = 1;
        Star star = new Star();
        star.setName("Sirius");
        star.setDistance(76500L);
        when(starRepository.findById(id)).thenReturn(Optional.of(star));

        starService.deleteStarById(id);

        verify(starRepository, times(1)).delete(star);
    }

    @Test
    void deleteStarById_ShouldThrowStarNotFoundException_WhenStarDoesNotExist() {
        int id = 1;
        when(starRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(StarNotFoundException.class, () -> starService.deleteStarById(id));
    }

    @Test
    void findClosestStars_ShouldReturnClosestStars() {
        List<StarDTO> stars = List.of(
                new StarDTO("Proxima Centauri", 42400L),
                new StarDTO("Alpha Centauri", 43000L),
                new StarDTO("Sirius", 8500L)
        );

        List<StarDTO> expected = List.of(
                new StarDTO("Sirius", 8500L),
                new StarDTO("Proxima Centauri", 42400L)
        );

        List<StarDTO> result = starService.findClosestStars(stars, 2);

        assertEquals(expected, result);
    }

    @Test
    void findClosestStars_ShouldThrowListIsNullOrEmptyException_WhenListIsEmpty() {
        List<StarDTO> stars = Collections.emptyList();

        assertThrows(ListIsNullOrEmptyException.class, () -> starService.findClosestStars(stars, 2));
    }

    @Test
    void getNumberOfStarsByDistances_ShouldReturnMapOfDistances() {
        List<StarDTO> stars = List.of(
                new StarDTO("Proxima Centauri", 42400L),
                new StarDTO("Alpha Centauri", 42400L),
                new StarDTO("Sirius", 8500L)
        );

        Map<Long, Integer> expected = Map.of(
                42400L, 2,
                8500L, 1
        );

        Map<Long, Integer> result = starService.getNumberOfStarsByDistances(stars);

        assertEquals(expected, result);
    }

    @Test
    void getNumberOfStarsByDistances_ShouldThrowListIsNullOrEmptyException_WhenListIsEmpty() {
        List<StarDTO> stars = Collections.emptyList();

        assertThrows(ListIsNullOrEmptyException.class, () -> starService.getNumberOfStarsByDistances(stars));
    }

    @Test
    void getUniqueStars_ShouldReturnUniqueStars() {
        List<StarDTO> stars = List.of(
                new StarDTO("Proxima Centauri", 42400L),
                new StarDTO("Alpha Centauri", 43000L),
                new StarDTO("Alpha Centauri", 43000L)
        );

        Collection<StarDTO> expected = List.of(
                new StarDTO("Proxima Centauri", 42400L),
                new StarDTO("Alpha Centauri", 43000L)
        );

        Collection<StarDTO> result = starService.getUniqueStars(stars);

        assertEquals(expected, result);
    }

    @Test
    void getUniqueStars_ShouldThrowListIsNullOrEmptyException_WhenListIsEmpty() {
        List<StarDTO> stars = Collections.emptyList();

        assertThrows(ListIsNullOrEmptyException.class, () -> starService.getUniqueStars(stars));
    }
}
