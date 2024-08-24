package trio.white.elephant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trio.white.elephant.domain.*;
import trio.white.elephant.dto.TeamDto;
import trio.white.elephant.exception.MemberNotFoundException;
import trio.white.elephant.exception.TeamNameAlreadyExistException;
import trio.white.elephant.exception.TeamNotFoundException;
import trio.white.elephant.exception.UserNotFoundException;
import trio.white.elephant.repository.MemberRepository;
import trio.white.elephant.repository.TeamRepository;
import trio.white.elephant.repository.UserRepository;

import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;

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

    @Transactional
    public Map<String, Long> createTeam(TeamDto teamDto, Long userId) {

        validateDuplicateTeam(teamDto);

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not Found"));

        Member member = Member.createMember(user);

        Team team = Team.createTeam(
                teamDto.getName(),
                teamDto.getPassword(),
                teamDto.getMinPrice(),
                teamDto.getMaxPrice(),
                member);

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
    public void createMember(Long teamId, Long userId) {

        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team Not Found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not Found"));

        validateDuplicateMember(teamId, userId);

        Member member = Member.joinMember(team, user);
        memberRepository.save(member);
    }

    private void validateDuplicateMember(Long teamId, Long userId) {

        List<Member> member = memberRepository.findByTeamIdAndUserId(teamId, userId);
        if (!member.isEmpty()) {
            throw new MemberNotFoundException("이미 가입되어 있는 팀입니다.");
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
}
