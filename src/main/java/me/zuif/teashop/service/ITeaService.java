package me.zuif.teashop.service;

import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.model.tea.TeaType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITeaService {
    void save(Tea tea);

    void saveAll(List<Tea> tea);

    void flush();

    void update(String id, Tea newTea);

    void delete(String id);

    Page<Tea> findAll(PageRequest request);

    Page<Tea> findAllByNameLike(String name, Pageable pageable);

    Tea findById(String id);

    List<Tea> findAllByOrderByIdAsc();

    Page<Tea> findAllByTeaType(TeaType teaType, Pageable pageable);

    long count();
}
