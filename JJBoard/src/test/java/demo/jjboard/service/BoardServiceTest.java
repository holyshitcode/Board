package demo.jjboard.service;

import demo.jjboard.controller.form.BoardCond;
import demo.jjboard.controller.form.BoardForm;
import demo.jjboard.entity.Board;
import demo.jjboard.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    private BoardService boardService;
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("게시글 생성 및 멤버와의 관계확인")
    void board() {
        Member member = new Member("tester");
        memberService.save(member);

        Board board = new Board(member, "test title", "nothing", null);
        boardService.save(board);

        Member foundMember = memberService.findMemberById(member.getId()).get();
        Board foundBoard = boardService.findByMember(foundMember);

        assertThat(foundBoard.getMember().getId()).isEqualTo(foundMember.getId());
        assertThat(foundBoard).isEqualTo(board);
    }

    @Test
    @DisplayName("조회수 증가 테스트")
    void countUp() {
        Member member = new Member("tester");
        memberService.save(member);
        Board board = new Board(member, "test title", "nothing", null);
        boardService.save(board);

        Optional<Board> foundBoard = boardService.findByIdWithCountUP(board.getId());
        assertThat(foundBoard.get().getBoardHitCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("추천 비추천 테스트")
    void recommend() {
        Member member = new Member("tester");
        memberService.save(member);
        Board board = new Board(member, "test title", "nothing", null);
        boardService.save(board);

        Optional<Board> foundBoard = boardService.findById(board.getId());
        boardService.boardRecommend(foundBoard.get(), true);
        boardService.boardRecommend(foundBoard.get(), false);
        boardService.boardRecommend(foundBoard.get(), false);

        assertThat(foundBoard.get().getRecommendation()).isEqualTo(1);
        assertThat(foundBoard.get().getNotRecommendation()).isEqualTo(2);

    }

    @Test
    @DisplayName("회원소유 게시물 수정")
    void edit() {
        Member member = new Member("tester");
        memberService.save(member);
        Board board = new Board(member, "test title", "nothing", null);
        boardService.save(board);

        Optional<Board> foundBoard = boardService.findById(board.getId());
        BoardForm form = new BoardForm();
        form.setBoardId(board.getId());
        form.setBoardContent("test content");
        form.setBoardTitle("test title new");
        form.setBoardWriter("hongildong");

        boardService.editingBoard(form, member);

        assertThat(board.getBoardTitle()).isEqualTo(form.getBoardTitle());
        assertThat(board.getBoardWriter()).isEqualTo(form.getBoardWriter());
        assertThat(board.getBoardContent()).isEqualTo(form.getBoardContent());
        assertThat(board.getMember().getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("게시판 검색조건")
    void search() {
        Member member = new Member("tester");
        memberService.save(member);
        String title = "test title";
        String writer = "hongildong";
        String content = "test content";

        Board board = new Board(member, title, writer, content);
        Board board2 = new Board(member, title+"new", "gildong", content);
        Board board3 = new Board(member, title+"new2", "gildong", "test test");
        boardService.save(board);
        boardService.save(board2);
        boardService.save(board3);


        BoardCond cond = new BoardCond();
        cond.setName(title);


        List<Board> foundBoard = boardService.findBoardByCond(cond);
        assertThat(foundBoard.size()).isEqualTo(1);
        assertThat(foundBoard).extracting("boardTitle").containsExactly(title);

        boardService.findByIdWithCountUP(board2.getId());
        BoardCond cond2 = new BoardCond();
        cond2.setName(title+"new");
        cond2.setHitCount(1);
        List<Board> foundBoard2 = boardService.findBoardByCond(cond2);

        assertThat(foundBoard2.size()).isEqualTo(1);
        assertThat(foundBoard2).extracting("boardTitle").containsExactly(title+"new");
        assertThat(foundBoard2.getFirst().getBoardHitCount()).isEqualTo(1);

        BoardCond cond3 = new BoardCond();
        cond3.setMemberName("tester");

        List<Board> foundBoard3 = boardService.findBoardByCond(cond3);
        assertThat(foundBoard3.size()).isEqualTo(3);
        assertThat(foundBoard3.getFirst().getMember().getUsername()).isEqualTo("tester");
        assertThat(foundBoard3).extracting("member.username").containsExactly("tester","tester","tester");

    }
}