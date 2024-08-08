package demo.jjboard.repository;

import demo.jjboard.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByLoginId(String loginId);
    Member findByUsername(String username);
}
