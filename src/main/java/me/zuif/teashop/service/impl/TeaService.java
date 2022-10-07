package me.zuif.teashop.service.impl;

import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.model.tea.TeaType;
import me.zuif.teashop.repository.TeaRepository;
import me.zuif.teashop.service.ITeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeaService implements ITeaService {
    private final TeaRepository teaRepository;

    @Autowired
    public TeaService(TeaRepository teaRepository) {
        this.teaRepository = teaRepository;
    }

    @Override
    public void save(Tea tea) {
        teaRepository.save(tea);
    }

    @Override
    public void update(String id, Tea newTea) {
        Optional<Tea> foundOptional = teaRepository.findById(id);
        if (foundOptional.isPresent()) {
            Tea found = foundOptional.get();
            found.setName(newTea.getName());
            found.setImageUrl(newTea.getImageUrl());
            found.setDescription(newTea.getDescription());
            found.setPrice(newTea.getPrice());
            found.setCount(newTea.getCount());
            found.setManufacturer(newTea.getManufacturer());
            found.setType(newTea.getType());
        }
        save(newTea);
    }

    @Override
    public void delete(String id) {
        teaRepository.delete(findById(id));
    }

    @Override
    public Tea findById(String id) {
        return teaRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<Tea> findAllByOrderByIdAsc() {
        return teaRepository.findAllByOrderByIdAsc();
    }

    @Override
    public List<Tea> findAllByTeaType(TeaType teaType) {
        return teaRepository.findAllByTeaType(teaType);
    }

    @Override
    public long count() {
        return teaRepository.count();
    }
}
