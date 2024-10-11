package demo.jjboard.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String boardTitle;

    private String boardWriter;

    @Lob
    private String boardContent;

    private String boardGeneralChat;

    private int boardHitCount; //조회수

    private int recommendation;

    private int notRecommendation;



    public Board(Member member, String boardTitle, String boardWriter, String boardContent, String boardGeneralChat) {
        this.member = member;
        this.boardTitle = boardTitle;
        this.boardWriter = boardWriter;
        this.boardContent = boardContent;
        this.boardGeneralChat = boardGeneralChat;
    }

    public void editingBoard(Long memberId, String boardTitle, String boardWriter, String boardContent) {
        if (this.member.getId().equals(memberId)) {
            this.boardTitle = boardTitle;
            this.boardWriter = boardWriter;
            this.boardContent = boardContent;
        }
    }

    public void boardHitCountUp() {
        boardHitCount++;
    }
    public void recommendationUp() {
        recommendation++;
    }
    public void notRecommendationUp() {
        notRecommendation++;
    }
}
