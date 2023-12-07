package ie.tcd.cdm.backendforfrontend.controller;

import ie.tcd.cdm.auth.model.UserDTO;
import ie.tcd.cdm.auth.model.LoginDTO;
import ie.tcd.cdm.auth.model.CreateUserDTO;
import ie.tcd.cdm.auth.invoker.ApiException;
import ie.tcd.cdm.auth.model.LoggedInSuccessfullyDTO;
import ie.tcd.cdm.backendforfrontend.services.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:8081/", "http://localhost:3000/, http://web-ui:3000/"})
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/users")
    public UserDTO createUser(@RequestBody CreateUserDTO createUserDTO) throws ApiException {
        return authService.createUserUsersPost(createUserDTO);
    }

    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable Integer id) throws ApiException {
        return authService.readUserUsersUserIdGet(id);
    }

    @PostMapping("/login")
    public LoggedInSuccessfullyDTO login(@RequestBody LoginDTO loginDTO) throws ApiException {
        return authService.loginLoginPost(loginDTO);
    }
}
