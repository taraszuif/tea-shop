package me.zuif.teashop.service;

import me.zuif.teashop.model.rating.Rating;

public interface IRatingService {
    void save(Rating rating);

    void delete(String id);
}
