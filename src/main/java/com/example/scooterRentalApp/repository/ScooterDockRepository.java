package com.example.scooterRentalApp.repository;

import com.example.scooterRentalApp.model.ScooterDock;
import com.example.scooterRentalApp.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScooterDockRepository extends CrudRepository<ScooterDock, Long> {
    List<ScooterDock> findByDockName(String dockName);
}
