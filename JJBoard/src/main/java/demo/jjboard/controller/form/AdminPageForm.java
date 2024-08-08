package demo.jjboard.controller.form;

import demo.jjboard.grade.MemberGrade;
import lombok.Data;

@Data
public class AdminPageForm {

    private String username;
    private MemberGrade memberGrade;

}
