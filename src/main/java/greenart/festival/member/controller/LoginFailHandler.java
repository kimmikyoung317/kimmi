package greenart.festival.member.controller;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Component
@Controller
public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String errorMessage;
        if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException){
            errorMessage="아이디 또는 비밀번호가 틀렸습니다.";
        } else{

            errorMessage="알 수없는 오류입니다. 관리자에게 문의하십시오.";

        }
        HttpSession session = request.getSession();
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(request.getParameter("email"));
        loginDTO.setErrorMessage(errorMessage);

        session.setAttribute("loginDTO", loginDTO);
        setDefaultFailureUrl("/festival/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }

    // 비밀번호 찾기 : 임시비번 메일전송
    @GetMapping("myPage/findPwd")
    public String findPwd(@RequestParam("email") String email, Model model) {
        model.addText("message");
        return "login";//로그인페이지로 돌아갑니다.
    }

     // 아이디 찾기 : 첫두글자만 힌트로 보여줌
    @GetMapping("myPage/findId")
    public String findId(@RequestParam("email") String email, Model model) {
        String hint = email.substring(0, 2) + "********"; // 첫 두 글자만 보여줌
        model.addText("message");
        //("message", "아이디 힌트: " + hint + "로 전송 되었습니다.");
        return "login"; // 로그인 페이지로 돌아갑니다.
    }
}

