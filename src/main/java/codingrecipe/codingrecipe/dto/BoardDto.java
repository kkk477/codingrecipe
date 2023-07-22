package codingrecipe.codingrecipe.dto;

import codingrecipe.codingrecipe.entity.BoardEntity;
import codingrecipe.codingrecipe.entity.BoardFileEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardDto {

    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;

    private List<MultipartFile> boardFile;
    private List<String> originalFileName;
    private List<String> storedFileName;
    private int fileAttached;   // 파일 첨부 여부(첨부 1, 미첨부 0)

    public static BoardDto toBoardDto(BoardEntity boardEntity) {
        BoardDto boardDto = new BoardDto();

        boardDto.setId(boardEntity.getId());
        boardDto.setBoardWriter(boardEntity.getBoardWriter());
        boardDto.setBoardPass(boardEntity.getBoardPass());
        boardDto.setBoardTitle(boardEntity.getBoardTitle());
        boardDto.setBoardContents(boardEntity.getBoardContents());
        boardDto.setBoardHits(boardEntity.getBoardHits());
        boardDto.setBoardCreatedTime(boardEntity.getCreatedDate());
        boardDto.setBoardUpdatedTime(boardEntity.getLastModifiedDate());

        if(boardEntity.getFileAttached() == 0) {
            boardDto.setFileAttached(0); // 0
        } else {
            // 파일 이름을 가져가야 함.
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();

            boardDto.setFileAttached(boardEntity.getFileAttached()); // 1

            for (BoardFileEntity boardFileEntity : boardEntity.getBoardFileEntities()) {
                originalFileNameList.add(boardFileEntity.getOriginalFileName());
                storedFileNameList.add(boardFileEntity.getStoredFileName());
            }
            boardDto.setOriginalFileName(originalFileNameList);
            boardDto.setStoredFileName(storedFileNameList);
        }

        return boardDto;
    }
}
