package trio.white.elephant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trio.white.elephant.domain.*;
import trio.white.elephant.dto.MemberDto;
import trio.white.elephant.dto.TeamDetailsDto;
import trio.white.elephant.dto.TeamDto;
import trio.white.elephant.dto.UserDto;
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
        List<Member> members = memberRepository.findByUserId(userId);

        for (Member member : members) {
            Team team = member.getTeam();

            TeamDto teamDto = new TeamDto();
            teamDto.setTeamId(team.getId());
            teamDto.setName(team.getName());
            teamDto.setMinPrice(team.getMinPrice());
            teamDto.setMaxPrice(team.getMaxPrice());
            teamDto.setMemberNumber(team.getMemberNumber());
            teamDto.setStatus(team.getStatus());
            teamDto.setUserRole(member.getRole());

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
                member
        );

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
        teamDto.setTeamId(team.getId());
        teamDto.setName(team.getName());
        teamDto.setMinPrice(team.getMinPrice());
        teamDto.setMaxPrice(team.getMaxPrice());
        teamDto.setMemberNumber(team.getMemberNumber());
        teamDto.setStatus(team.getStatus());

        return teamDto;
    }

    public TeamDetailsDto findDetailById(Long teamId, Long userId) {

        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team Not Found"));
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not Found"));

        Role userRole = memberRepository.findByTeamIdAndUserId(teamId, userId).get(0).getRole();
        List<Member> members = memberRepository.findByTeamId(teamId);

        TeamDto teamDto = new TeamDto();
        teamDto.setTeamId(team.getId());
        teamDto.setName(team.getName());
        teamDto.setMinPrice(team.getMinPrice());
        teamDto.setMaxPrice(team.getMaxPrice());
        teamDto.setMemberNumber(team.getMemberNumber());
        teamDto.setStatus(team.getStatus());
        teamDto.setUserRole(userRole);

        List<MemberDto> memberDtos = new ArrayList<>();
        for (Member member : members) {
            MemberDto memberDto = new MemberDto();
            memberDto.setMemberId(member.getUser().getId());
            memberDto.setName(member.getUser().getName());
            memberDto.setRole(member.getRole());

            memberDtos.add(memberDto);
        }

        return TeamDetailsDto.createTeamDetailsDto(teamDto, memberDtos);
    }

    @Transactional
    public void createMember(Long teamId, Long userId) {

        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team Not Found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not Found"));

        validateDuplicateMember(teamId, userId);

        team.setMemberNumber(team.getMemberNumber() + 1);
        Member member = Member.joinMember(team, user);
        memberRepository.save(member);
    }

    private void validateDuplicateMember(Long teamId, Long userId) {

        List<Member> members = memberRepository.findByTeamIdAndUserId(teamId, userId);
        if (!members.isEmpty()) {
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

    @Transactional
    public void deleteMember(Long teamId, Long memberId, Long userId) {

        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team Not Found"));
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        userRepository.findById(memberId).orElseThrow(() -> new UserNotFoundException("User Not Found"));

        Member member = validateMember(teamId, userId, memberId);
        memberRepository.delete(member);

        team.setMemberNumber(team.getMemberNumber() - 1);
        teamRepository.save(team);
    }

    private Member validateMember(Long teamId, Long userId, Long memberId) {
        List<Member> users = memberRepository.findByTeamIdAndUserId(teamId, userId);
        if (users.isEmpty() || users.get(0).getRole() != Role.LEADER) {
            throw new MemberNotFoundException("사용자가 팀장이 아닙니다.");
        }

        List<Member> members = memberRepository.findByTeamIdAndUserId(teamId, memberId);
        if (members.isEmpty() || members.get(0).getRole() == Role.LEADER) {
            throw new MemberNotFoundException("멤버가 팀원이 아닙니다.");
        }

        return members.get(0);
    }
}
