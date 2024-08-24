package trio.white.elephant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trio.white.elephant.domain.Team;
import trio.white.elephant.domain.TeamStatus;
import trio.white.elephant.dto.TeamDto;
import trio.white.elephant.exception.TeamNameAlreadyExistException;
import trio.white.elephant.exception.TeamNotFoundException;
import trio.white.elephant.repository.TeamRepository;

import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamDto findById(Long teamId) {

        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team Not Found"));

        TeamDto teamDto = new TeamDto();
        teamDto.setId(team.getId());
        teamDto.setName(team.getName());
        teamDto.setMinPrice(team.getMinPrice());
        teamDto.setMaxPrice(team.getMaxPrice());
        teamDto.setMemberNumber(team.getMemberNumber());
        teamDto.setStatus(team.getStatus());

        return teamDto;
    }

    @Transactional
    public Map<String, Long> create(TeamDto teamDto) {

        validateDuplicateTeam(teamDto);

        Team team = new Team();
        team.setName(teamDto.getName());
        team.setPassword(teamDto.getPassword());
        team.setMinPrice(teamDto.getMinPrice());
        team.setMaxPrice(teamDto.getMaxPrice());

        teamRepository.save(team);

        Map<String, Long> result = new HashMap<>();
        result.put("teamId", team.getId());

        return result;
    }

    private void validateDuplicateTeam(TeamDto teamDto) {

        Optional<Team> team = teamRepository.findByName(teamDto.getName());
        if (team.isPresent()) {
            throw new TeamNameAlreadyExistException("이미 존재하는 팀의 이름입니다. 새로운 이름을 입력해주세요.");
        }
    }

    @Transactional
    public void update(Long teamId) {

        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team Not Found"));

        if (team.getStatus() == TeamStatus.COMPLETED) {
            throw new TeamNotFoundException("이미 완성된 팀은 수정할 수 없습니다.");
        }

        team.setStatus(TeamStatus.COMPLETED);
        teamRepository.save(team);
    }

    @Transactional
    public void delete(Long teamId) {

        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team Not Found"));
        teamRepository.delete(team);
    }

    public List<TeamDto> findByUserId(Long userId) {

        List<TeamDto> teamDtos = new ArrayList<>();

        List<Team> teams = teamRepository.findAll();

        for (Team team : teams) {
            TeamDto teamDto = new TeamDto();
            teamDto.setId(team.getId());
            teamDto.setName(team.getName());
            teamDto.setMinPrice(team.getMinPrice());
            teamDto.setMaxPrice(team.getMaxPrice());
            teamDto.setMemberNumber(team.getMemberNumber());
            teamDto.setStatus(team.getStatus());

            teamDtos.add(teamDto);
        }

        return teamDtos;
    }
}
