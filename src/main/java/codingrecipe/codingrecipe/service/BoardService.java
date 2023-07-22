package codingrecipe.codingrecipe.service;

import codingrecipe.codingrecipe.dto.BoardDto;
import codingrecipe.codingrecipe.entity.BaseEntity;
import codingrecipe.codingrecipe.entity.BoardEntity;
import codingrecipe.codingrecipe.entity.BoardFileEntity;
import codingrecipe.codingrecipe.repository.BoardFileRepository;
import codingrecipe.codingrecipe.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    public void save(BoardDto boardDto) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if(boardDto.getBoardFile().isEmpty()) {
            boardRepository.save(BoardEntity.toBoardEntity(boardDto));
        } else {
            // 첨부 파일 있음.
            /*
            * 1. DTO에 담긴 파일을 꺼냄
            * 2. 파일의 이름 가져옴
            * 3. 서버 저장용 이름을 만듦
            * 4. 저장 경로 설정
            * 5. 해당 경로에 파일 저장
            * 6. board 테이블에 해당 데이터 save 처리
            * 7. board_file 테이블에 해당 데이터 save 처리
            * */
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDto);
            Long savedId = boardRepository.save(boardEntity).getId();
            Optional<BoardEntity> board = boardRepository.findById(savedId);

            for (MultipartFile boardFile : boardDto.getBoardFile()) {
                String originalFilename = boardFile.getOriginalFilename(); // 2.
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 3.
                String savePath = "C:\\Users\\Ray\\Desktop\\codingrecipe\\img\\" + storedFileName; // 4.
                boardFile.transferTo(new File(savePath)); // 5.

                if(board.isPresent()) {
                    BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board.get(), originalFilename, storedFileName);
                    boardFileRepository.save(boardFileEntity);
                }
            }
        }
    }

    @Transactional
    public List<BoardDto> findAll() {
        return boardRepository.findAll().stream().map(BoardDto::toBoardDto).toList();
    }

    public void updateHits(Long id) {
        Optional<BoardEntity> boardEntity = boardRepository.findById(id);
        boardEntity.ifPresent(entity -> entity.setBoardHits(entity.getBoardHits() + 1));
    }

    public BoardDto findById(Long id) {
        Optional<BoardEntity> boardEntity = boardRepository.findById(id);
        return boardEntity.map(BoardDto::toBoardDto).orElse(null);
    }

    public Boolean boardPassCheck(Long id, String pass) {
        Optional<BoardEntity> boardEntity = boardRepository.findById(id);
        return boardEntity.isPresent() && Objects.equals(boardEntity.get().getBoardPass(), pass);
    }

    @Transactional
    public BoardDto update(BoardDto boardDto) {
        Optional<BoardEntity> boardEntity = boardRepository.findById(boardDto.getId());
        boardEntity.ifPresent(b -> {
            b.setBoardTitle(boardDto.getBoardTitle());
            b.setBoardWriter(boardDto.getBoardWriter());
            b.setBoardContents(boardDto.getBoardContents());
        });

        return boardDto;
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDto> paging(Pageable  pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 3;

        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        return boardEntities.map(BoardDto::toBoardDto);
    }
}
