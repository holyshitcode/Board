package demo.jjboard.repository.custom;


import demo.jjboard.controller.form.BoardCond;
import demo.jjboard.entity.Board;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepositoryCustom {
    List<Board> findBoardByCond(BoardCond cond);
}
