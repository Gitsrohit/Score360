package com.example.Crix.payloads;

import com.example.Crix.entities.Team;
import com.example.Crix.entities.TournamentParticipation;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data


public class UserDto {

    private String name;


    private String email;


    private String phone;


    private String password;

    // Teams created by the user

    private List<Team> createdTeams = new ArrayList<>();


    private List<Team> teamsPlayed = new ArrayList<>();
    private List<TournamentParticipation> tournamentsPlayed = new ArrayList<>();


    private int totalRuns = 0;

    private int totalWickets = 0;
    private int totalFours = 0;
    private int totalSixes = 0;
    private int totalMatchesPlayed = 0;
}
