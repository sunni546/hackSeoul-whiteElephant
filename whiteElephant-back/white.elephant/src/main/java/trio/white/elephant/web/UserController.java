package trio.white.elephant.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import trio.white.elephant.dto.JoinDto;
import trio.white.elephant.dto.LoginDto;
import trio.white.elephant.dto.UserDto;
import trio.white.elephant.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public UserDto readOne(@PathVariable("userId") Long userId) {

        return userService.findById(userId);
    }

    @PostMapping("/join")
    public void create(@Valid @RequestBody JoinDto joinDto) {

        userService.join(joinDto);
    }

    @PostMapping("/login")
    public UserDto auth(@Valid @RequestBody LoginDto loginDto) {

        return userService.login(loginDto);
    }
}
