package trio.white.elephant.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import trio.white.elephant.domain.Member;
import trio.white.elephant.domain.Team;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.team.id = :teamId")
    List<Member> findByTeamId(@Param("teamId") Long teamId);

    @Query("SELECT m FROM Member m WHERE m.user.id = :userId")
    List<Member> findByUserId(@Param("userId") Long userId);

    @Query("SELECT m FROM Member m WHERE m.team.id = :teamId AND m.user.id = :userId")
    List<Member> findByTeamIdAndUserId(@Param("teamId") Long teamId, @Param("userId") Long userId);
}
