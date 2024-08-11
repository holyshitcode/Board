package demo.jjboard.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
@Setter
public class Message extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "message_id")
    private Long id;

    private String messageContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    public Message(String messageContent, Member member, ChatRoom chatRoom) {
        this.messageContent = messageContent;
        this.member = member;
        this.chatRoom = chatRoom;
    }
}
