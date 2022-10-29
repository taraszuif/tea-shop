package me.zuif.teashop.dto;

import lombok.Data;
import me.zuif.teashop.model.Rating;
import me.zuif.teashop.model.tea.Tea;

import java.util.List;
import java.util.Map;

@Data
public class RatingDTO {
    private Map<Integer, Integer> rateTotalCount;
    private double average;
    private Tea tea;
    private List<Rating> ratingList;
    private double ratingsCount;
}
