package trio.white.elephant.dto;

import lombok.Data;
import trio.white.elephant.domain.Member;

import java.util.List;

@Data
public class TeamUserDto {
    private List<TeamDto> teamCompletedDtos;
    private List<TeamDto> teamLeaderDtos;
    private List<TeamDto> teamMemberDtos;

    public static TeamUserDto createTeamUserDto(List<TeamDto> teamCompletedDtos, List<TeamDto> teamLeaderDtos, List<TeamDto> teamMemberDtos) {
        TeamUserDto teamUserDto = new TeamUserDto();
        teamUserDto.setTeamCompletedDtos(teamCompletedDtos);
        teamUserDto.setTeamLeaderDtos(teamLeaderDtos);
        teamUserDto.setTeamMemberDtos(teamMemberDtos);
        return teamUserDto;
    }
}
