package demo.jjboard.repository;

import demo.jjboard.entity.Board;
import demo.jjboard.entity.Member;
import demo.jjboard.repository.custom.BoardRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    Board findByMember(Member member);
}
