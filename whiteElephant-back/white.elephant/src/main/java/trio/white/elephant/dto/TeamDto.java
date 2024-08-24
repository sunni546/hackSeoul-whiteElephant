package trio.white.elephant.dto;

import lombok.Data;
import trio.white.elephant.domain.Role;
import trio.white.elephant.domain.TeamStatus;

@Data
public class TeamDto {

    private Long teamId;

    private String name;
    private String password;

    private Integer minPrice;
    private Integer maxPrice;

    private int memberNumber;
    private TeamStatus status;
    private String leaderName;

    private Role userRole;
}
