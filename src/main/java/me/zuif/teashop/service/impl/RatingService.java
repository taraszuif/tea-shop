package me.zuif.teashop.service.impl;

import me.zuif.teashop.model.rating.Rating;
import me.zuif.teashop.repository.RatingRepository;
import me.zuif.teashop.service.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void delete(String id) {
        if (ratingRepository.existsById(id)) {
            ratingRepository.delete(ratingRepository.findById(id).get());
        }
    }
}
