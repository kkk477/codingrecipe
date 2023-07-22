package codingrecipe.codingrecipe.controller;

import codingrecipe.codingrecipe.dto.BoardDto;
import codingrecipe.codingrecipe.dto.CommentDto;
import codingrecipe.codingrecipe.repository.BoardRepository;
import codingrecipe.codingrecipe.service.BoardService;
import codingrecipe.codingrecipe.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/save")
    public String saveForm() {
        return "saveForm";
    }

    @PostMapping("/save")
    public String saveFormDto(@ModelAttribute("boardDto") BoardDto boardDto) throws IOException {

        boardService.save(boardDto);
        return "redirect:/";
    }

    @GetMapping()
    public String findAll(Model model) {
        List<BoardDto> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "listBoard";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model, @PageableDefault(page = 1) Pageable pageable) {
        boardService.updateHits(id);
        BoardDto boardDto = boardService.findById(id);
        List<CommentDto> commentDtoList = commentService.findAll(id);

        model.addAttribute("commentDtoList", commentDtoList);
        model.addAttribute("board", boardDto);
        model.addAttribute("page", pageable.getPageNumber());
        return "detailBoard";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDto);
        return "updateBoard";
    }

    @PostMapping("/update")
    public String updateBoard(@ModelAttribute BoardDto boardDto, Model model) {
        Boolean passCheck = boardService.boardPassCheck(boardDto.getId(), boardDto.getBoardPass());
        if(passCheck) {
            BoardDto board = boardService.update(boardDto);
            model.addAttribute("board", board);
            return "redirect:/board/" + boardDto.getId();
        } else {
            model.addAttribute("boardUpdate", boardDto);
            return "redirect:/update/" + boardDto.getId();
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/board";
    }

    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<BoardDto> boardList = boardService.paging(pageable);
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), boardList.getTotalPages());

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "paging";
    }
}
