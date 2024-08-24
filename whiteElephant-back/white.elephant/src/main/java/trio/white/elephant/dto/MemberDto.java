package trio.white.elephant.dto;

import lombok.Data;
import trio.white.elephant.domain.Role;

@Data
public class MemberDto {

    private Long memberId;

    private Long userId;

    private String userName;

    private Role userRole;
}
