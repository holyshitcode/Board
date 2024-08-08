package demo.jjboard.controller;

import demo.jjboard.entity.Member;
import demo.jjboard.entity.session.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class MainScreenController {

    @GetMapping("/")
    public String sessionValidate(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model){
        //세션에 데이터가없으면 홈으로
        if(loginMember==null){
            return "login/login";
        }
        //
        model.addAttribute("member", loginMember);
        return "login/loginHome";

    }
    @GetMapping("/main")
    public String TrueSessionValidated(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,Model model){
        model.addAttribute("member", loginMember);

        return "login/loginHome";
    }
}
