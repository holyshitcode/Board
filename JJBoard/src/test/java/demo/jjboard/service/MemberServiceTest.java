package demo.jjboard.service;

import demo.jjboard.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.Name;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;



@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;


    @Test
    @DisplayName("멤버 저장 및 찾기")
    void member() {
        Member member = new Member("tester");
        Member member1 = new Member("tester2");
        memberService.save(member1);
        memberService.save(member);

        Optional<Member> foundMember = memberService.findMemberById(member.getId());
        List<Member> memberList = memberService.findAllMembers();

        assertThat(member).isEqualTo(foundMember.get());
        assertThat(memberList).hasSize(2);


    }
}