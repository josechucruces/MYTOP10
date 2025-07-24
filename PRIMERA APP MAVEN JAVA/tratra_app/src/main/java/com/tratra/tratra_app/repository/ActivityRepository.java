package com.tratra.tratra_app.repository;

import com.tratra.tratra_app.entity.Activity;
import com.tratra.tratra_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    // Ya existente
    List<Activity> findByUserUsername(String username);

    // NUEVO método necesario para el ActivityService
    List<Activity> findAllByUser(User user);
}
