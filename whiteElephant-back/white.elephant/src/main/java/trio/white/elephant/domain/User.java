package trio.white.elephant.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NonNull @Email
    @Column(unique = true)
    private String email;

    @NonNull
    private String password;
    
    @NonNull
    private String name;

    @NonNull
    @Column(unique = true)
    private String phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Member> members = new ArrayList<>();
}
