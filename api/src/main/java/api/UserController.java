package api;

import java.util.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserManager userManager;

    public UserController(UserStore store, KeyWhitener keyWhitener) {
        this.userManager = new UserManager(store, keyWhitener);
    }

    class Status {

        public String status;

        public Status(String status) {
            this.status = status;
        }
    }

    static class SignUpRequest {

        public String username;
        public String email;
        public String password;
    }

    @PostMapping(value = "/signup", consumes = "application/json")
    public Status signup(@RequestBody SignUpRequest req) {
        if (userManager.addUser(req.username, req.email, req.password)) {
            return new Status("ok");
        }
        return new Status("fail");
    }

    static class SignInRequest {

        public String username;
        public String password;
    }

    @PostMapping(value = "/signin", consumes = "application/json")
    public Status signin(@RequestBody SignInRequest req) {
        if (userManager.verifyUser(req.username, req.password)) {
            return new Status("ok");
        }
        return new Status("fail");
    }

    @GetMapping("/users")
    public List<String> users() {
        return userManager.getAllUsers();
    }
}