package demo.jjboard.controller;

import demo.jjboard.controller.form.AdminPageForm;
import demo.jjboard.entity.Member;
import demo.jjboard.entity.session.SessionConst;
import demo.jjboard.grade.MemberGrade;
import demo.jjboard.service.AdminService;
import demo.jjboard.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final MemberService memberService;

    @GetMapping("/admin")
    public String admin(@ModelAttribute("form") AdminPageForm form, Model model,
                        @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {
        if(!loginMember.getMemberGrade().equals(MemberGrade.ADMIN)) {
            return "redirect:/";
        }
        List<Member> members = adminService.searchMember(form);
        model.addAttribute("members", members);
        return "admin/admin";
    }

    @PostMapping("/admin")
    public String adminSubmit(@ModelAttribute("form") AdminPageForm form,
                              @RequestParam String grade) {
        Member foundMember = memberService.findByUsername(form.getUsername());
        if(grade.equals(MemberGrade.ADMIN.toString())) {
           adminService.changeGradeToAdmin(foundMember);
        }
        if (grade.equals(MemberGrade.GENERAL.toString())) {
            adminService.changeGradeToGeneral(foundMember);
        }
        return "redirect:/admin";
    }


}
