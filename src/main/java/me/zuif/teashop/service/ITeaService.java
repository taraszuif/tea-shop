package me.zuif.teashop.service;

import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.model.tea.TeaType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ITeaService {
    void save(Tea tea);

    void saveAll(List<Tea> tea);

    void flush();

    void update(String id, Tea newTea);

    void delete(String id);

    Page<Tea> findAll(PageRequest request);

    Tea findById(String id);

    List<Tea> findAllByOrderByIdAsc();

    List<Tea> findAllByTeaType(TeaType teaType);

    long count();
}
