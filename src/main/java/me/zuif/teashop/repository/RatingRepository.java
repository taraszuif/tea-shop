package me.zuif.teashop.repository;

import me.zuif.teashop.model.Rating;
import me.zuif.teashop.model.User;
import me.zuif.teashop.model.tea.Tea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, String> {
    List<Rating> findAllByUser(User user);

    List<Rating> findAllByTea(Tea tea);

    Optional<Rating> findById(String id);

    @Query("select avg(r.rate)from Rating r where r.tea.id=:teaId")
    double getAverageRate(String teaId);

    boolean existsByTea(Tea tea);

    int countAllByTeaAndRate(Tea tea, int rate);
}