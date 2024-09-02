package demo.jjboard.service;

import demo.jjboard.entity.Friend;
import demo.jjboard.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FriendServiceTest {

    @Autowired
    private FriendService friendService;
    @Autowired
    private MemberService memberService;

    @Test
    void friendAdd() {
        Member member = new Member("tester");
        Member forFriend = new Member("friendAdd");
        memberService.save(member);
        memberService.save(forFriend);

        Friend friend = new Friend();
        friendService.save(friend);

        member.setFriend(friend);

        friendService.makeFriend(member.getId(), forFriend.getId());
        assertThat(member.getFriend().getFriends().size()).isEqualTo(1);
        assertThat(member.getFriend().getFriends().contains(forFriend)).isTrue();

    }

    @Test
    void friendAdd2() {
        Member member = new Member("tester");
        Member forFriend = new Member("friendAdd");
        Member forFriend2 = new Member("friendAdd2");
        Member forFriend3 = new Member("friendAdd3");
        memberService.save(member);
        memberService.save(forFriend);
        memberService.save(forFriend2);
        memberService.save(forFriend3);

        Friend friend = new Friend();
        friendService.save(friend);

        member.setFriend(friend);

        friendService.makeFriend(member.getId(), forFriend.getId());
        friendService.makeFriend(member.getId(), forFriend2.getId());
        friendService.makeFriend(member.getId(), forFriend3.getId());
        assertThat(member.getFriend().getFriends().size()).isEqualTo(3);
        assertThat(member.getFriend().getFriends().contains(forFriend)).isTrue();
        assertThat(member.getFriend().getFriends()).extracting("username")
                .containsExactly("friendAdd", "friendAdd2", "friendAdd3");

    }

}