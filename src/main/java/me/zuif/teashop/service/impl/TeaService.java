package me.zuif.teashop.service.impl;

import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.model.tea.TeaType;
import me.zuif.teashop.repository.TeaRepository;
import me.zuif.teashop.service.ITeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    public Page<Tea> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Tea> list;
        List<Tea> teas = teaRepository.findAll();
        if (teas.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, teas.size());
            list = teas.subList(startItem, toIndex);
        }
        Page<Tea> bookPage
                = new PageImpl<Tea>(list, PageRequest.of(currentPage, pageSize), teas.size());

        return bookPage;
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
