package trio.white.elephant.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserDto {

    private Long userId;

    private String name;

    @Email
    private String email;

    private String phone;
}
