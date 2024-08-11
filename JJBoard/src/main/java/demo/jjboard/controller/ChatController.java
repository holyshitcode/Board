package demo.jjboard.controller;


import demo.jjboard.entity.ChatRoom;
import demo.jjboard.entity.Member;
import demo.jjboard.entity.session.SessionConst;
import demo.jjboard.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;


    @GetMapping("/chat")
    public String getChatRoom(@ModelAttribute("chatRoom") ChatRoom chatRoom) {
        return "chat/chat";
    }

    @PostMapping("/chat")
    public String makeChatRoom(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                               @ModelAttribute("chatRoom") ChatRoom chatRoom, BindingResult bindingResult,
                               @RequestParam("max") int max) {
        chatService.makeRoom(member, max);
        return "redirect:/chat/list";
    }

    @GetMapping("/chat/list")
    public String getChatList(Model model) {
        List<ChatRoom> allChatRooms = chatService.findAllChatRooms();
        model.addAttribute("chatRooms", allChatRooms);
        return "chat/list";
    }

    @GetMapping("/chat/{chatId}")
    public String getChatRoomId(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                              @PathVariable("chatId") Long chatId, Model model) {
        ChatRoom chatRoom = chatService.findChatRoomById(chatId);
        if(chatRoom.getMembers().contains(member)){
            return "chat/chatRoom";
        }else{
            chatService.enterRoom(chatRoom.getId(), member);
        }
        model.addAttribute("chatRoom", chatRoom);
        model.addAttribute("member", member);
        return "chat/chatRoom";
    }
    @PostMapping("/chat/{chatId}/exit")
    public String exitRoom(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                           @PathVariable("chatId") Long chatId){
        ChatRoom foundRoom = chatService.findChatRoomById(chatId);
        chatService.leaveRoom(foundRoom.getId(),member);
        return "redirect:/chat/list";
    }

    @PostMapping("/chat/{chatId}")
    public String sendMessage(@PathVariable("chatId") Long chatId,
                              @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                              @RequestParam String message) {
        ChatRoom foundRoom = chatService.findChatRoomById(chatId);
        chatService.sendMessage(foundRoom,member,message);
        return "redirect:/chat/{chatId}/chatting";
    }

    @GetMapping("/chat/{chatId}/chatting")
    public String getChatRoomChat(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                @PathVariable("chatId") Long chatId, Model model) {
        ChatRoom chatRoom = chatService.findChatRoomById(chatId);
        model.addAttribute("chatRoom", chatRoom);
        model.addAttribute("member", member);
        return "chat/chatRoom";
    }

    @PostMapping("/chat/{chatId}/delete")
    public String deleteRoom(@PathVariable("chatId") Long chatId,
                             @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member){
        if(chatService.findChatRoomById(chatId).getLeader().equals(member.getUsername())) {
            chatService.deleteRoom(chatService.findChatRoomById(chatId));
        }else{
            return "redirect:/chat/{chatId}";
        }
        return "redirect:/chat/list";
    }


}
