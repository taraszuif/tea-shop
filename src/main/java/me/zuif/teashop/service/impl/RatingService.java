package me.zuif.teashop.service.impl;

import me.zuif.teashop.dto.RatingDTO;
import me.zuif.teashop.model.Rating;
import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.repository.RatingRepository;
import me.zuif.teashop.service.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RatingService implements IRatingService {
    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public void save(Rating rating) {
        ratingRepository.save(rating);
    }

    @Override
    public int countAllByTeaAndRate(Tea tea, int rate) {
        return ratingRepository.countAllByTeaAndRate(tea, rate);
    }

    @Override
    public List<Rating> findAllByTea(Tea tea) {
        return ratingRepository.findAllByTea(tea);
    }

    @Override
    public Optional<Rating> findById(String id) {
        return ratingRepository.findById(id);
    }

    @Override
    public double getAverageRate(String teaId) {
        return ratingRepository.getAverageRate(teaId);
    }

    @Override
    public void update(String id, Rating newRating) {
        Optional<Rating> foundOptional = ratingRepository.findById(id);
        if (foundOptional.isPresent()) {
            Rating found = foundOptional.get();
            found.setRate(newRating.getRate());
            found.setComment(newRating.getComment());
            found.setTitle(newRating.getTitle());
        }
        save(newRating);
    }

    @Override
    public Optional<RatingDTO> getDTO(Tea tea) {
        if (!ratingRepository.existsByTea(tea)) return Optional.empty();
        RatingDTO result = new RatingDTO();
        result.setTea(tea);
        List<Rating> ratings = findAllByTea(tea);
        result.setRatingList(ratings);
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            map.put(i, countAllByTeaAndRate(tea, i));
        }
        result.setAverage(getAverageRate(tea.getId()));
        result.setRateTotalCount(map);
        result.setRatingsCount(ratings.size());
        return Optional.of(result);
    }

    @Override
    public void delete(String id) {
        if (ratingRepository.existsById(id)) {
            ratingRepository.delete(ratingRepository.findById(id).get());
        }
    }

}
