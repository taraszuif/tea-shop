package me.zuif.teashop.repository;

import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.model.tea.TeaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeaRepository extends JpaRepository<Tea, String> {
    Optional<Tea> findById(String id);

    Tea findByName(String name);

    List<Tea> findAllByOrderByIdAsc();

    List<Tea> findAllByTeaType(TeaType teaType);
}