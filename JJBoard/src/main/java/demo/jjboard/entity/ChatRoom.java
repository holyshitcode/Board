package demo.jjboard.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatRoom extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "chat_room_id")
    private Long id;

    @OneToMany(mappedBy = "chatRoom")
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoom")
    private List<Message> messages = new ArrayList<>();

    private int maxEnter; //최대입장 인원수

    private int currentMembers;

    private String leader;

    public ChatRoom(int maxEnter, String leader) {
        this.maxEnter = maxEnter;
        this.leader = leader;
    }

    public void addMessage(Message message) {
        messages.add(message);
        message.setChatRoom(this); // Ensure bidirectional relationship is maintained
    }

    public void addCurrentMember(Member member) {
        currentMembers++;
        members.add(member);
        member.setChatRoom(this);
    }

    public void incrementCurrentMembers() {
        currentMembers++;
    }

    public void decrementCurrentMembers(Member member) {
        currentMembers--;
        members.remove(member);
        member.setChatRoom(null);
    }

    public void settingMaxMember(int maxMember) {
        maxEnter = maxMember;
    }

    public void settingLeader(String leader) {
        this.leader = leader;
    }




}
