package com.example.Crix.services;




import com.example.Crix.entities.Team;
import com.example.Crix.entities.User;
import com.example.Crix.payloads.CreateTeamRequest;
import com.example.Crix.repository.TeamRepo;
import com.example.Crix.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    private TeamRepo teamRepository;

    @Autowired
    private UserRepo userRepository;

    public Team createTeam(Long userId, CreateTeamRequest request) {
        // Check if the team name already exists
        if (teamRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Team name already exists");
        }

        // Find the user who is creating the team
        User creator = userRepository.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Create and save the team
        Team team = new Team();
        team.setName(request.getName());
        team.setCreator(creator);

        return teamRepository.save(team);
    }
}

