package com.example.mtptask.service;

import com.example.mtptask.exception.ListIsNullOrEmptyException;
import com.example.mtptask.exception.StarNotFoundException;
import com.example.mtptask.mapper.StarMapper;
import com.example.mtptask.model.Star;
import com.example.mtptask.model.dto.StarDTO;
import com.example.mtptask.repository.StarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StarService {

    private final StarRepository starRepository;
    private final StarMapper mapper;

    public StarDTO addStar(StarDTO starDTO) {
        Star star = mapper.toStar(starDTO);
        starRepository.save(star);
        return mapper.toDto(star);
    }

    public StarDTO getStarById(int id) {
        Star star = starRepository.findById(id)
                .orElseThrow(
                        () -> new StarNotFoundException("Star with given id could not be found")
                );
        return mapper.toDto(star);
    }

    public StarDTO updateStar(StarDTO starDTO, int id) {
        Star star = starRepository.findById(id)
                .orElseThrow(
                        () -> new StarNotFoundException("Star with given id could not be found")
                );
        star.setName(starDTO.name());
        star.setDistance(starDTO.distance());
        starRepository.save(star);
        return mapper.toDto(star);
    }

    public void deleteStarById(int id) {
        Star star = starRepository.findById(id)
                .orElseThrow(
                        () -> new StarNotFoundException("Star with given id could not be found")
                );
        starRepository.delete(star);
    }

    public List<StarDTO> findClosestStars(List<StarDTO> stars, int size) {
        if (stars.isEmpty()) {
            throw new ListIsNullOrEmptyException("List cannot be empty!");
        }
        return stars.stream()
                .sorted(Comparator.comparingLong(StarDTO::distance))
                .limit(size)
                .collect(Collectors.toList());
    }

    public Map<Long, Integer> getNumberOfStarsByDistances(List<StarDTO> stars) {
        if (stars.isEmpty()) {
            throw new ListIsNullOrEmptyException("List cannot be empty!");
        }

        return stars.stream()
                .collect(Collectors.groupingBy(
                        StarDTO::distance,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public Collection<StarDTO> getUniqueStars(Collection<StarDTO> stars) {
        if (stars.isEmpty()) {
            throw new ListIsNullOrEmptyException("List cannot be empty!");
        }

        Set<String> uniqueNames = new HashSet<>();
        return stars.stream()
                .filter(s -> uniqueNames.add(s.name()))
                .collect(Collectors.toList());
    }
}
