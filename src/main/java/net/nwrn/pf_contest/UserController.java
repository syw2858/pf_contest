package net.nwrn.pf_contest;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nwrn.pf_contest.exception.ApiException;
import net.nwrn.pf_contest.exception.ExceptionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ExceptionService exceptionService;

    @GetMapping("/join")
    public String joinPage() {
        return "Join";
    }

    @PostMapping("/join")
    public String join(@RequestParam(required = false, name = "userId") String userId,
                       @RequestParam(required = false, name = "password") String password) {
        if (userId == null || userId.isEmpty()) {
            throw new ApiException("Join", "userId is null");
        }
        if (password == null || password.isEmpty()) {
            throw new ApiException("Join", "password is null");
        }

        try {
            userService.join(userId, password);
            return "redirect:/";
        }
        catch(ApiException e) {
            throw e;
        }
        catch(Exception e) {
            log.error(exceptionService.generateMessage(), e);
            throw new ApiException("Join", "백엔드에서 알 수 없는 에러가 발생했습니다." );
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "Login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(required = false, name = "userId") String userId,
                        @RequestParam(required = false, name = "password") String password, HttpServletResponse response) {
        if(userId == null || userId.isEmpty()) {
            throw new ApiException("Login", "userId is null");
        }
        if (password == null || password.isEmpty()) {
            throw new ApiException("Login", "password is null");
        }
        try {
            userService.login(userId, password, response);
            return "redirect:/";
        } catch(ApiException e) {
            throw e;
        } catch(Exception e) {
            log.error(exceptionService.generateMessage(), e);
            throw new ApiException("Login", "백엔드에서 알 수 없는 에러가 발생했습니다.");
        }

    }

}
