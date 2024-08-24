package trio.white.elephant.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
@Getter @Setter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @NonNull
    @Column(unique = true)
    private String name;
    @NonNull
    private String password;
    @NonNull
    private Integer minPrice;
    @NonNull
    private Integer maxPrice;

    private int memberNumber;

    @Enumerated(EnumType.STRING)
    private TeamStatus status = TeamStatus.ACTIVE;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Member> members = new ArrayList<>();

    public static Team createTeam(String name, String password, Integer minPrice, Integer maxPrice, Member... members) {
        Team team = new Team();
        team.setName(name);
        team.setPassword(password);
        team.setMinPrice(minPrice);
        team.setMaxPrice(maxPrice);
        team.setMemberNumber(1);
        team.setStatus(TeamStatus.ACTIVE);
        for (Member member : members) {
            team.addMember(member);
        }

        return team;
    }

    public void addMember(Member member) {
        members.add(member);
        member.setTeam(this);
    }
}
