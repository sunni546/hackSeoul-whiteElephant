package trio.white.elephant.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

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

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Interest> interests = new ArrayList<>();

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Review> reviews = new ArrayList<>();
}
