package demo.jjboard.controller;


import datadog.trace.api.Trace;
import demo.jjboard.controller.form.BoardCond;
import demo.jjboard.controller.form.BoardForm;
import demo.jjboard.entity.Board;
import demo.jjboard.entity.Member;
import demo.jjboard.entity.session.SessionConst;
import demo.jjboard.service.BoardService;
import demo.jjboard.service.MarkdownService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final MarkdownService mkService;


    @GetMapping("/board/save")
    public String boardShow(@ModelAttribute("boardForm") BoardForm boardForm,@ModelAttribute("member") Member member){
        return "board/boardSaveForm";
    }

    @PostMapping("/board/save")
    public String boardSave(@ModelAttribute("boardForm") BoardForm boardForm,
                            HttpServletRequest request,Model model){
        Member sessionMember = getSessionMember(request);

        // 마크다운 내용을 HTML로 변환
        String htmlContent = mkService.renderMarkdownToHtml(boardForm.getBoardContent());

        // 변환된 HTML로 게시글을 저장
        Board board = saveBoard(boardForm, htmlContent, request);
        boardService.save(board);

        model.addAttribute("member", sessionMember);
        return "redirect:/boards";
    }

    @Trace(operationName = "boardList")
    @GetMapping("/boards")
    public String showBoards(@RequestParam(required = false) String name,
                             @RequestParam(required = false) Integer hitCount,
                             @RequestParam(required = false) Integer recommendation,
                             @RequestParam(required = false) Integer notRecommendation,
                             @RequestParam(required = false) String memberName,
                             Model model) {
        BoardCond cond = new BoardCond(name,memberName,hitCount,recommendation,notRecommendation);
        log.info("cond={}",cond);
        List<Board> boards = boardService.findBoardByCond(cond);
        model.addAttribute("boards", boards);
        log.info("boards={}", boards.toString());
        return "board/boards";
    }


    @GetMapping("/boards/{boardId}")
    public String showBoard(@PathVariable Long boardId, Model model, HttpServletRequest request) {
        Member sessionMember = getSessionMember(request);
        Optional<Board> board = boardService.findByIdWithCountUP(boardId);
        board.ifPresent(value -> model.addAttribute("board", value));
        model.addAttribute("currentMember", sessionMember);
        return "board/board";
    }

    @PostMapping("/boards/{boardId}")
    public String recommending(@PathVariable Long boardId, @RequestParam("action") String demand) {
        Optional<Board> board = boardService.findById(boardId);

        boardService.boardRecommend(board.get(), "recommend".equals(demand));

        return "redirect:/boards/{boardId}";
    }

    private static Board saveBoard(BoardForm boardForm, String htmlContent, HttpServletRequest request) {
        Member member = getSessionMember(request);
        return new Board(member, boardForm.getBoardTitle(), boardForm.getBoardWriter(), htmlContent, boardForm.getBoardGeneralChat());
    }

    @GetMapping("/boards/edit/{boardId}")
    public String editBoard(@PathVariable Long boardId, Model model) {
        Optional<Board> board = boardService.findById(boardId);
        model.addAttribute("boardForm", board.get());
        return "board/boardEditForm";
    }

    @PostMapping("/boards/edit/{boardId}")
    public String boardEditing(@PathVariable Long boardId, @ModelAttribute("boardForm") BoardForm boardForm, BindingResult result
    , HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Member member = getSessionMember(request);
        boardService.editingBoard(boardForm,member);
        redirectAttributes.addAttribute("boardId", boardId);
        return "redirect:/boards";

    }

    @GetMapping("/boards/delete/{boardId}")
    public String deleteBoard(@PathVariable Long boardId,HttpServletRequest request) {
        Member sessionMember = getSessionMember(request);
        Optional<Board> board = boardService.findById(boardId);
        boardService.delete(board.get(), sessionMember);
        return "redirect:/boards";
    }

    private static Member getSessionMember(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
    }


}
