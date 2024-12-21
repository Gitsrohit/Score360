package com.example.Crix.repository;


import com.example.Crix.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepo extends JpaRepository<Team, Long> {
    boolean existsByName(String name);
}
