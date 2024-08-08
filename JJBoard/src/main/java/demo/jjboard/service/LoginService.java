package demo.jjboard.service;


import demo.jjboard.entity.Member;
import demo.jjboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final MemberRepository memberRepository;


    public Member login(String loginId, String password) {
        Member member = memberRepository.findByLoginId(loginId);
        if (member == null) {
            return null;
        }
        if (!member.getPassword().equals(password)) {
            return null;
        }
        return member;
    }
}
