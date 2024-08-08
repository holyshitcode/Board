package demo.jjboard.service;

import demo.jjboard.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class LoginServiceTest {

    @Autowired
    private LoginService loginService;
    @Autowired
    private MemberService memberService;


    @Test
    @DisplayName("로그인 확인")
    void login() {
        Member member = new Member("tester","testId","testPasswd");
        memberService.save(member);


        Member loginedMember = loginService.login("testId", "testPasswd");

        assertThat(loginedMember).isEqualTo(member);
    }
}