package me.zuif.teashop.repository;

import me.zuif.teashop.model.tea.Tea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeaRepository extends JpaRepository<Tea, String> {
}