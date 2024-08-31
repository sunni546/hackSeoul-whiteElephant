package trio.white.elephant.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeamDetailsDto {

    private TeamDto teamDto;

    private List<MemberDto> memberDtos;

    public static TeamDetailsDto createTeamDetailsDto(TeamDto teamDto, List<MemberDto> memberDtos) {

        TeamDetailsDto teamDetailsDto = new TeamDetailsDto();
        teamDetailsDto.setTeamDto(teamDto);
        teamDetailsDto.setMemberDtos(memberDtos);

        return teamDetailsDto;
    }
}
