package com.example.mtptask.mapper;

import com.example.mtptask.model.Star;
import com.example.mtptask.model.dto.StarDTO;
import org.springframework.stereotype.Component;

@Component
public class StarMapper {

    public Star toStar(StarDTO starDTO) {
        if (starDTO == null) {
            return null;
        }
        Star star = new Star();
        star.setName(starDTO.name());
        star.setDistance(starDTO.distance());
        return star;
    }

    public StarDTO toDto(Star star) {
        if (star == null) {
            return null;
        }
        return new StarDTO(star.getName(), star.getDistance());
    }
}
