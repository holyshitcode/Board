package demo.jjboard.service;

import demo.jjboard.controller.form.BoardForm;
import demo.jjboard.entity.Board;
import demo.jjboard.entity.Member;
import demo.jjboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public void save(Board board) {
        boardRepository.save(board);
    }

    public void delete(Board board,Member member) {
        if (member.getId().equals(board.getMember().getId())) {
            boardRepository.delete(board);
        }
    }

    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

    public Optional<Board> findByIdWithCountUP(Long id) {
        Optional<Board> board = boardRepository.findById(id);
        board.ifPresent(Board::boardHitCountUp);
        return board;
    }

    public Page<Board> findAllPaging(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Board findByMember(Member member) {
        return boardRepository.findByMember(member);
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public void boardRecommend(Board board,boolean recommend) {
        if (recommend) {
            board.recommendationUp();
        }else{
            board.notRecommendationUp();
        }
    }

    public void editingBoard(BoardForm boardForm, Member member) {
        Optional<Board> board = boardRepository.findById(boardForm.getBoardId());
        board.ifPresent(value -> value.editingBoard(member.getId(), boardForm.getBoardTitle(),
                boardForm.getBoardWriter(), boardForm.getBoardContent()));

    }

}
