package trio.white.elephant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trio.white.elephant.domain.*;
import trio.white.elephant.dto.MemberDto;
import trio.white.elephant.dto.TeamDetailsDto;
import trio.white.elephant.dto.TeamDto;
import trio.white.elephant.dto.TeamUserDto;
import trio.white.elephant.exception.*;
import trio.white.elephant.repository.MemberRepository;
import trio.white.elephant.repository.TeamRepository;
import trio.white.elephant.repository.UserRepository;

import java.util.*;

import static trio.white.elephant.dto.TeamUserDto.createTeamUserDto;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;

    public List<TeamDto> findAll(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not Found"));

        List<Team> teams = teamRepository.findByStatus(TeamStatus.ACTIVE);

        List<TeamDto> teamDtos = new ArrayList<>();
        for (Team team : teams) {
            TeamDto teamDto = new TeamDto();
            teamDto.setTeamId(team.getId());
            teamDto.setName(team.getName());
            teamDto.setMinPrice(team.getMinPrice());
            teamDto.setMaxPrice(team.getMaxPrice());
            teamDto.setMemberNumber(team.getMemberNumber());
            teamDto.setStatus(team.getStatus());

            List<Member> members = memberRepository.findByTeamIdAndUserId(team.getId(), userId);
            if (!members.isEmpty()) {
                teamDto.setUserRole(members.get(0).getRole());
            }

            teamDtos.add(teamDto);
        }

        return teamDtos;
    }

    public TeamUserDto findByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not Found"));

        List<Member> members = memberRepository.findByUserId(userId);

        List<TeamDto> teamCompletedDtos = new ArrayList<>();
        List<TeamDto> teamLeaderDtos = new ArrayList<>();
        List<TeamDto> teamMemberDtos = new ArrayList<>();

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

            if (team.getStatus() == TeamStatus.COMPLETED) {
                teamCompletedDtos.add(teamDto);
            } else if (member.getRole() == Role.LEADER) {
                teamLeaderDtos.add(teamDto);
            } else if (member.getRole() == Role.MEMBER) {
                teamMemberDtos.add(teamDto);
            }
        }

        return createTeamUserDto(teamCompletedDtos, teamLeaderDtos, teamMemberDtos);
    }

    @Transactional
    public Map<String, Object> createTeam(TeamDto teamDto, Long userId) {

        validateDuplicateTeam(teamDto);

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not Found"));

        Member member = Member.createMember(user);

        Team team = Team.createTeam(
                userId,
                teamDto.getName(),
                teamDto.getPassword(),
                teamDto.getMinPrice(),
                teamDto.getMaxPrice(),
                member
        );

        teamRepository.save(team);

        Map<String, Object> result = new HashMap<>();
        result.put("teamId", team.getId());
        result.put("userRole", String.valueOf(Role.LEADER));

        return result;
    }

    private void validateDuplicateTeam(TeamDto teamDto) {

        Optional<Team> team = teamRepository.findByName(teamDto.getName());
        if (team.isPresent()) {
            throw new TeamNameAlreadyExistException("이미 존재하는 팀의 이름입니다. 새로운 이름을 입력해주세요.");
        }
    }

    @Transactional
    public Map<String, String> createMember(TeamDto teamDto, Long userId) {

        Team team = teamRepository.findById(teamDto.getTeamId()).orElseThrow(() -> new TeamNotFoundException("Team Not Found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not Found"));

        validatePassword(team, teamDto.getPassword());
        validateDuplicateMember(team.getId(), userId);

        team.setMemberNumber(team.getMemberNumber() + 1);
        Member member = Member.joinMember(team, user);
        memberRepository.save(member);

        Map<String, String> result = new HashMap<>();
        result.put("userRole", String.valueOf(Role.MEMBER));

        return result;
    }

    private void validatePassword(Team team, String password) {
        if (!password.equals(team.getPassword())) {
            throw new TeamPasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
    }

    private void validateDuplicateMember(Long teamId, Long userId) {

        List<Member> members = memberRepository.findByTeamIdAndUserId(teamId, userId);
        if (!members.isEmpty()) {
            throw new MemberNotFoundException("이미 가입되어 있는 팀입니다.");
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
            memberDto.setMemberId(member.getId());
            memberDto.setUserId(member.getUser().getId());
            memberDto.setUserName(member.getUser().getName());
            memberDto.setUserRole(member.getRole());

            memberDtos.add(memberDto);
        }

        return TeamDetailsDto.createTeamDetailsDto(teamDto, memberDtos);
    }

    public MemberDto findReceiverById(Long teamId, Long userId) {

        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not Found"));

        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team Not Found"));
        if (team.getStatus() != TeamStatus.COMPLETED) {
            throw new TeamNotCompletedException("Team Not COMPLETED");
        }

        List<Member> members = memberRepository.findByTeamIdAndUserId(teamId, userId);
        if (members.isEmpty()) {
            throw new MemberNotFoundException("Member Not Found");
        }

        MemberDto memberDto = new MemberDto();
        memberDto.setMemberId(members.get(0).getId());

        User receiver = userRepository.findById(members.get(0).getReceiverId()).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        memberDto.setUserId(receiver.getId());
        memberDto.setUserName(receiver.getName());

        return memberDto;
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
