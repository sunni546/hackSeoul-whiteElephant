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

    private int memberNumber = 1;

    @Enumerated(EnumType.STRING)
    private TeamStatus status = TeamStatus.ACTIVE;
}
