package demo.jjboard.service;


import demo.jjboard.entity.Member;
import demo.jjboard.grade.MemberGrade;
import demo.jjboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(Member member) {
        memberRepository.save(member);
    }

    public void delete(Member member) {
        memberRepository.delete(member);
    }

    public Optional<Member> findMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }






}
