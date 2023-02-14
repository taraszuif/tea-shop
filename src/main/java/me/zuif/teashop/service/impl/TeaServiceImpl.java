package me.zuif.teashop.service.impl;

import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.model.tea.TeaType;
import me.zuif.teashop.repository.TeaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeaServiceImpl implements me.zuif.teashop.service.ITeaService {
    private final TeaRepository teaRepository;

    @Autowired
    public TeaServiceImpl(TeaRepository teaRepository) {
        this.teaRepository = teaRepository;
    }

    @Override
    public void save(Tea tea) {
        teaRepository.save(tea);
    }

    @Override
    public void saveAll(List<Tea> teas) {
        teaRepository.saveAll(teas);
    }

    @Override
    public void flush() {
        teaRepository.flush();
    }


    public Page<Tea> findAll(PageRequest request) {
        return teaRepository.findAll(request);
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
            found.setTeaType(newTea.getTeaType());
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
    public Page<Tea> findAllByDescriptionLikeOrManufacturerLike(String description, String manufacturer, Pageable pageable) {
        return teaRepository.findAllByDescriptionLikeOrManufacturerLike( description, manufacturer, pageable);
    }


    @Override
    public Page<Tea> findAll(Pageable pageable) {
        return teaRepository.findAll(pageable);
    }


    @Override
    public Page<Tea> findAllByNameLike(String name, Pageable pageable) {
        return teaRepository.findAllByNameLike(name, pageable);
    }

    @Override
    public Page<Tea> findAllByTeaType(TeaType teaType, Pageable pageable) {
        return teaRepository.findAllByTeaType(teaType, pageable);
    }


    @Override
    public long count() {
        return teaRepository.count();
    }
}
