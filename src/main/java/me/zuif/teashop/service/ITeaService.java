package me.zuif.teashop.service;

import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.model.tea.TeaType;

import java.util.List;

public interface ITeaService {
    void save(Tea tea);

    void update(String id, Tea newTea);

    void delete(String id);

    Tea findById(String id);

    List<Tea> findAllByOrderByIdAsc();

    List<Tea> findAllByTeaType(TeaType teaType);

    long count();
}
