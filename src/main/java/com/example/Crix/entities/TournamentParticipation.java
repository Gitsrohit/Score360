package com.example.Crix.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Data
public class TournamentParticipation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int matchesPlayed;
    private int matchesWon;
    private int matchesLost;
    private int runsScored;
    private int wicketsTaken;

    @Column(nullable = false)
    private LocalDate participationDate;
}
