package com.example.mtptask.controller;

import com.example.mtptask.model.dto.StarDTO;
import com.example.mtptask.service.StarService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stars")
@AllArgsConstructor
public class StarController {

    private final StarService starService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public StarDTO addStar(@RequestBody StarDTO starDTO) {
        return starService.addStar(starDTO);
    }

    @GetMapping("/{id}")
    public StarDTO getStarById(@PathVariable int id) {
        return starService.getStarById(id);
    }

    @PutMapping("/{id}")
    public StarDTO updateStar(@RequestBody StarDTO starDTO, @PathVariable int id) {
        return starService.updateStar(starDTO, id);
    }

    @DeleteMapping("/{id}")
    public void deleteStarById(@PathVariable int id) {
        starService.deleteStarById(id);
    }

    @GetMapping("/closest-stars")
    public List<StarDTO> findClosestStars(@RequestBody List<StarDTO> stars, @RequestParam int size) {
        return starService.findClosestStars(stars, size);
    }

    @GetMapping("/stars-by-distances")
    public Map<Long, Integer> getNumberOfStarsByDistances(@RequestBody List<StarDTO> stars) {
        return starService.getNumberOfStarsByDistances(stars);
    }

    @GetMapping("/unique-stars")
    public Collection<StarDTO> getUniqueStars(@RequestBody Collection<StarDTO> stars) {
        return starService.getUniqueStars(stars);
    }

}
