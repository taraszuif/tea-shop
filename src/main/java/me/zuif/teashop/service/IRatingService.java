package me.zuif.teashop.service;

import me.zuif.teashop.dto.RatingDTO;
import me.zuif.teashop.model.Rating;
import me.zuif.teashop.model.tea.Tea;

import java.util.List;
import java.util.Optional;

public interface IRatingService {
    void save(Rating rating);

    int countAllByTeaAndRate(Tea tea, int rate);

    List<Rating> findAllByTea(Tea tea);

    Optional<Rating> findById(String id);

    double getAverageRate(String teaId);


    void update(String id, Rating newRating);

    Optional<RatingDTO> getDTO(Tea tea);

    void delete(String id);
}