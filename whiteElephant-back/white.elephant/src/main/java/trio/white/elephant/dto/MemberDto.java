package trio.white.elephant.dto;

import lombok.Data;
import trio.white.elephant.domain.Role;

@Data
public class MemberDto {

    private Long memberId;

    private String name;

    private Role role;
}
