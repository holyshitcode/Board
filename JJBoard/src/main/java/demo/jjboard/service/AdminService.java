package demo.jjboard.service;


import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.jjboard.controller.form.AdminPageForm;
import demo.jjboard.entity.Member;
import demo.jjboard.entity.QMember;
import demo.jjboard.grade.MemberGrade;
import demo.jjboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static demo.jjboard.entity.QMember.*;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final JPAQueryFactory queryFactory;


    public void changeGradeToAdmin(Member member) {
        member.setMemberGrade(MemberGrade.ADMIN);
    }

    public void changeGradeToGeneral(Member member) {
        member.setMemberGrade(MemberGrade.GENERAL);
    }

    public List<Member> searchMember(AdminPageForm form) {
        return queryFactory
                .select(member)
                .from(member)
                .where(memberFind(form.getUsername()))
                .fetch();
    }

    private BooleanExpression memberFind(String name) {
        return name != null ? member.username.like(name) : null;
    }

}
