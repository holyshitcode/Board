package demo.jjboard.service;


import demo.jjboard.entity.Friend;
import demo.jjboard.entity.Member;
import demo.jjboard.repository.FriendRepository;
import demo.jjboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    public Long save(Friend friend) {
        friendRepository.save(friend);
        return friend.getId();
    }


    public void makeFriend(Long memberId, Long friendId) {
        Member member = memberRepository.findById(memberId).orElseGet(null);
        Member addMember = memberRepository.findById(friendId).orElseGet(null);

        member.getFriend().getFriends().add(addMember);
    }

    public List<Member> getFriendsList(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseGet(null);
        return member.getFriend().getFriends();
    }

}
