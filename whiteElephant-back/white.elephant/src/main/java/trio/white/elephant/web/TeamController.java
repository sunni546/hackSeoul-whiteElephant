package trio.white.elephant.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import trio.white.elephant.dto.TeamDto;
import trio.white.elephant.service.TeamService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/{teamId}")
    public TeamDto readOne(@PathVariable("teamId") Long teamId) {

        return teamService.findById(teamId);
    }

    @PostMapping("")
    public Map<String, Long> create(@Valid @RequestBody TeamDto teamDto) {

        return teamService.create(teamDto);
    }

    @PatchMapping("/{teamId}")
    public void changeStatus(@PathVariable("teamId") Long teamId) {

        teamService.update(teamId);
    }

    @DeleteMapping("/{teamId}")
    public void delete(@PathVariable("teamId") Long teamId) {

        teamService.delete(teamId);
    }

    @GetMapping("/users/{userId}")
    public List<TeamDto> readAll(@PathVariable("userId") Long userId) {

        return teamService.findByUserId(userId);
    }
}
