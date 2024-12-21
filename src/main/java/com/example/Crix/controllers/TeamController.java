package com.example.Crix.controllers;




import com.example.Crix.entities.Team;
import com.example.Crix.payloads.CreateTeamRequest;
import com.example.Crix.response.BaseApiResponse;
import com.example.Crix.services.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<BaseApiResponse<Team>> createTeam(
            @PathVariable Long userId,
            @Valid @RequestBody CreateTeamRequest request) {
        Team team = teamService.createTeam(userId, request);
        return ResponseEntity.ok(
                BaseApiResponse.<Team>builder()
                        .success(true)
                        .message("Team Created Successfully!! You can now add players to the Team"+" "+request.getName())
                        .data(team)
                        .build()
        );
    }
}

