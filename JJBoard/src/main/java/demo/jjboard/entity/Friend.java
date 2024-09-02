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
public class Friend {

    @Id @GeneratedValue
    @Column(name = "friend_id")
    private Long id;

    @OneToMany
    @JoinColumn(name = "friend_id")
    private List<Member> friends = new ArrayList<>();



}
