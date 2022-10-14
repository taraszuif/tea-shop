package me.zuif.teashop.repository;

import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.model.tea.TeaType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeaRepository extends JpaRepository<Tea, String> {
    Optional<Tea> findById(String id);


    Page<Tea> findAll(Pageable pageable);


    Page<Tea> findAllByNameLike(String name, Pageable pageable);

    Page<Tea> findAllByTeaType(TeaType teaType, Pageable pageable);

}