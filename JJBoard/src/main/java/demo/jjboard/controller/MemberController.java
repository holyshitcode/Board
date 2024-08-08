package demo.jjboard.controller;


import demo.jjboard.entity.Member;
import demo.jjboard.grade.MemberGrade;
import demo.jjboard.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/member")
    public String member(@ModelAttribute("member") Member member) {
        return "member/add";
    }

    @PostMapping("/member")
    public String addMember(@ModelAttribute("member") Member member, BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()) {
            return "member/add";
        }
        member.setMemberGrade(MemberGrade.GENERAL);
        memberService.save(member);


        return "redirect:/member/success";
    }

    @GetMapping("/member/success")
    public String success(Model model) {


        return "member/success";
    }



}
