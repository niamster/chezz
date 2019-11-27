package api;

import java.util.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserManager userManager;
    private final UserTokenStore userTokenStore;
    private final UserTokenGenerator userTokenGenerator;

    public UserController(UserManager userManager, UserTokenStore userTokenStore, UserTokenGenerator userTokenGenerator) {
        this.userManager = userManager;
        this.userTokenStore = userTokenStore;
        this.userTokenGenerator = userTokenGenerator;
    }

    class Status {

        public String status;
        public String token;

        public Status(String status) {
            this.status = status;
        }

        public Status(String status, String token) {
            this.status = status;
            this.token = token;
        }
    }

    static class SignUpRequest {

        public String username;
        public String email;
        public String password;
    }

    @PostMapping(value = APISecurity.PUBLIC_EP_PREFIX + "/signup", consumes = "application/json")
    public Status signup(@RequestBody SignUpRequest req) {
        if (userManager.addUser(req.username, req.email, req.password)) {
            String token = userTokenGenerator.generateToken(req.username);
            userTokenStore.setUserToken(req.username, token);
            return new Status("ok", token);
        }
        return new Status("fail");
    }

    static class SignInRequest {

        public String username;
        public String password;
    }

    @PostMapping(value = APISecurity.PUBLIC_EP_PREFIX + "/signin", consumes = "application/json")
    public Status signin(@RequestBody SignInRequest req) {
        if (userManager.verifyUser(req.username, req.password)) {
            String token = userTokenGenerator.generateToken(req.username);
            userTokenStore.setUserToken(req.username, token);
            return new Status("ok", token);
        }
        return new Status("fail");
    }

    @GetMapping(APISecurity.PROTECTED_EP_PREFIX + "/signout")
    public void signout(@RequestParam(value = APISecurity.USER_TOKEN_PARAM) String userToken) {
        userTokenStore.removeToken(userToken);
    }

    @GetMapping(APISecurity.PUBLIC_EP_PREFIX + "/users")
    public List<String> users() {
        return userManager.getAllUsers();
    }

    @GetMapping(APISecurity.PROTECTED_EP_PREFIX + "/user/info")
    public String users(@RequestParam(value = APISecurity.USER_TOKEN_PARAM) String userToken) {
        String username = userTokenStore.getUser(userToken);
        return userManager.getUserInfo(username).email;
    }
}