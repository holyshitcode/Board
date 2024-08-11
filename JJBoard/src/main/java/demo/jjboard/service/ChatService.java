package demo.jjboard.service;


import demo.jjboard.entity.ChatRoom;
import demo.jjboard.entity.Member;
import demo.jjboard.entity.Message;
import demo.jjboard.repository.ChatRoomRepository;
import demo.jjboard.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;


    public ChatRoom findChatRoomById(Long id) {
        Optional<ChatRoom> foundChatRoom = chatRoomRepository.findById(id);
        return foundChatRoom.orElse(null);
    }

    public Long makeRoom(Member member, int maxEnter) {
        ChatRoom chatRoom = new ChatRoom(maxEnter,member.getUsername());
        chatRoom.incrementCurrentMembers();
        chatRoom.getMembers().add(member);
        chatRoomRepository.save(chatRoom);
        return chatRoom.getId();
    }

    public void deleteRoom(ChatRoom chatRoom) {
        if(chatRoomRepository.existsById(chatRoom.getId())) {
            chatRoomRepository.delete(chatRoom);
        }
    }

    public void enterRoom(Long chatRoomId, Member member) {
        Optional<ChatRoom> foundChatRoom = chatRoomRepository.findById(chatRoomId);
        if(foundChatRoom.isPresent()) {
            ChatRoom chatRoom = foundChatRoom.get();
            if (chatRoom.getCurrentMembers() < chatRoom.getMaxEnter() && chatRoomRepository.existsById(chatRoom.getId())) {
                if (!chatRoom.getMembers().contains(member)) {
                    chatRoom.addCurrentMember(member);
                }
            }
        }
    }

    public void leaveRoom(Long chatRoomId, Member member) {
        Optional<ChatRoom> byId = chatRoomRepository.findById(chatRoomId);
        if(byId.isPresent()) {
            ChatRoom chatRoom = byId.get();
            chatRoom.decrementCurrentMembers(member);
        }
    }

    public void sendMessage(ChatRoom chatRoom,Member member,String message) {
        // 새로운 메시지 생성
        Message newMessage = new Message(message, member,chatRoom);
        // 메시지 저장
        messageRepository.save(newMessage);
        chatRoom.addMessage(newMessage);


    }

    public List<ChatRoom> findAllChatRooms() {
        return chatRoomRepository.findAll();
    }
}
