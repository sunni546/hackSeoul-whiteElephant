package trio.white.elephant.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "members")
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "receiver_id")
    private Long receiverId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Member createMember(User user) {
        Member member = new Member();
        member.setUser(user);
        member.setRole(Role.LEADER);

        return member;
    }

    public static Member joinMember(Team team, User user) {
        Member member = new Member();
        member.setTeam(team);
        member.setUser(user);
        member.setRole(Role.MEMBER);

        return member;
    }
}
