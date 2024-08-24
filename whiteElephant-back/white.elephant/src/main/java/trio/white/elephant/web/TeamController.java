package trio.white.elephant.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import trio.white.elephant.dto.TeamDetailsDto;
import trio.white.elephant.dto.TeamDto;
import trio.white.elephant.service.TeamService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/teams")
public class TeamController {

    private final TeamService teamService;

    @GetMapping("")
    public List<TeamDto> readAll(@PathVariable("userId") Long userId) {

        return teamService.findByUserId(userId);
    }

    @PostMapping("")
    public Map<String, Long> create(@Valid @RequestBody TeamDto teamDto, @PathVariable("userId") Long userId) {

        return teamService.createTeam(teamDto, userId);
    }

    @GetMapping("/{teamId}")
    public TeamDto readOne(@PathVariable("teamId") Long teamId, @PathVariable("userId") Long userId) {

        return teamService.findById(teamId);
    }

    @GetMapping("/{teamId}/details")
    public TeamDetailsDto readOneDetail(@PathVariable("teamId") Long teamId, @PathVariable("userId") Long userId) {

        return teamService.findDetailById(teamId, userId);
    }

    @PostMapping("/{teamId}")
    public void join(@PathVariable("teamId") Long teamId, @PathVariable("userId") Long userId) {

        teamService.createMember(teamId, userId);
    }

    @PatchMapping("/{teamId}")
    public void changeStatus(@PathVariable("teamId") Long teamId, @PathVariable("userId") Long userId) {

        teamService.update(teamId);
    }

    @DeleteMapping("/{teamId}")
    public void delete(@PathVariable("teamId") Long teamId, @PathVariable("userId") Long userId) {

        teamService.delete(teamId);
    }

    @DeleteMapping("/{teamId}/members/{memberId}")
    public void delete(@PathVariable("teamId") Long teamId, @PathVariable("memberId") Long memberId, @PathVariable("userId") Long userId) {

        teamService.deleteMember(teamId, memberId, userId);
    }
}
