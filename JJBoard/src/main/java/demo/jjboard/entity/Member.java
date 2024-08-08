package demo.jjboard.entity;


import demo.jjboard.grade.MemberGrade;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String loginId;

    private String password;

    @Enumerated(EnumType.STRING)
    private MemberGrade memberGrade;

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, String loginId, String password) {
        this.username = username;
        this.loginId = loginId;
        this.password = password;
    }

    public Member(String username, String loginId, String password, MemberGrade memberGrade) {
        this.username = username;
        this.loginId = loginId;
        this.password = password;
        this.memberGrade = memberGrade;
    }
}