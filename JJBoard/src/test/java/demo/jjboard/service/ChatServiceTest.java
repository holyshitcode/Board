package demo.jjboard.service;

import demo.jjboard.entity.ChatRoom;
import demo.jjboard.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class ChatServiceTest {

    @Autowired
    private ChatService chatService;


    @Test
    @DisplayName("채팅방 만들고 참여하기")
    void makeAndEnter() {
        Member member = new Member("makingTest");
        Member member2 = new Member("enteringTest");

        Long chatRoomId = chatService.makeRoom(member, 3);

        ChatRoom foundChatRoom = chatService.findChatRoomById(chatRoomId);

        chatService.enterRoom(foundChatRoom.getId(), member2);

        assertThat(foundChatRoom.getCurrentMembers()).isEqualTo(2);
        assertThat(foundChatRoom.getMembers()).extracting("username")
                .containsExactly("makingTest", "enteringTest");

    }

    @Test
    @DisplayName("채팅방 만들고 참여한후 메세지보내기")
    void makeAndSendMessage() {
        Member member = new Member("makingTest");
        Member member2 = new Member("enteringTest");

        Long chatRoomId = chatService.makeRoom(member, 3);

        ChatRoom foundChatRoom = chatService.findChatRoomById(chatRoomId);

        chatService.enterRoom(foundChatRoom.getId(), member2);

        String testMessage1 = "testMessage1";
        String testMessage2 = "testMessage2";
        chatService.sendMessage(foundChatRoom, member, testMessage1);
        chatService.sendMessage(foundChatRoom, member2, testMessage2);

        assertThat(foundChatRoom.getMessages()).extracting("messageContent").containsExactly(testMessage1, testMessage2);
        assertThat(foundChatRoom.getMessages().getFirst().getMessageContent()).isEqualTo(testMessage1);
        assertThat(foundChatRoom.getMessages().size()).isEqualTo(2);
        assertThat(foundChatRoom.getMessages().getFirst().getMember()).isEqualTo(member);

    }

    @Test
    @DisplayName("최대 인원수초과시 입장불가")
    void makeAndExit() {
        Member member = new Member("makingTest");
        Member member2 = new Member("enteringTest");

        Long chatRoomId = chatService.makeRoom(member, 1);

        ChatRoom foundChatRoom = chatService.findChatRoomById(chatRoomId);

        chatService.enterRoom(foundChatRoom.getId(), member2);

        assertThat(foundChatRoom.getCurrentMembers()).isEqualTo(1);
        assertThat(foundChatRoom.getMembers()).extracting("username").containsExactly("makingTest");
    }

    @Test
    @DisplayName("방삭제하면 입장불가")
    void cannotEnter() {
        Member member = new Member("makingTest");
        Member member2 = new Member("enteringTest");

        Long chatRoomId = chatService.makeRoom(member, 3);

        ChatRoom foundChatRoom = chatService.findChatRoomById(chatRoomId);

        chatService.deleteRoom(foundChatRoom);

        ChatRoom chatRoomById = chatService.findChatRoomById(foundChatRoom.getId());
        assertThat(chatRoomById).isNull();


    }

    @Test
    @DisplayName("방나갈시 현재인원감소")
    void decrement() {
        Member member = new Member("makingTest");
        Member member2 = new Member("enteringTest");

        Long chatRoomId = chatService.makeRoom(member, 3);

        ChatRoom foundChatRoom = chatService.findChatRoomById(chatRoomId);
        System.out.println("foundChatRoom.getMembers() = " + foundChatRoom.getMembers());
        chatService.enterRoom(foundChatRoom.getId(), member2);
        assertThat(foundChatRoom.getCurrentMembers()).isEqualTo(2);
        System.out.println("foundChatRoom.getMembers() = " + foundChatRoom.getMembers());

        chatService.leaveRoom(foundChatRoom.getId(),member2);
        System.out.println("foundChatRoom.getMembers() = " + foundChatRoom.getMembers());
        assertThat(foundChatRoom.getCurrentMembers()).isEqualTo(1);
        assertThat(foundChatRoom.getMembers().size()).isEqualTo(1);
    }
}