package trio.white.elephant.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trio.white.elephant.domain.Team;
import trio.white.elephant.domain.TeamStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByName(String name);

    List<Team> findByStatus(TeamStatus status);
}
