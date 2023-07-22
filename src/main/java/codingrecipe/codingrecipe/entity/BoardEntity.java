package codingrecipe.codingrecipe.entity;

import codingrecipe.codingrecipe.dto.BoardDto;
import codingrecipe.codingrecipe.dto.MemberDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "board")
public class BoardEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_id")
    private Long id;

    @Column(length = 20, nullable = false)
    private String boardWriter;

    @Column
    private String boardPass;

    @Column(length = 100, nullable = false)
    private String boardTitle;

    @Column(length = 500, nullable = false)
    private String boardContents;

    @Column
    private int boardHits;

    @Column
    private int fileAttached;   // 1 or 0

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.ALL)
    private List<BoardFileEntity> boardFileEntities = new ArrayList<>();

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.ALL)
    private List<CommentEntity> commentEntities = new ArrayList<>();

    public static BoardEntity toBoardEntity(BoardDto boardDto) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setId(boardDto.getId());
        boardEntity.setBoardWriter(boardDto.getBoardWriter());
        boardEntity.setBoardPass(boardDto.getBoardPass());
        boardEntity.setBoardTitle(boardDto.getBoardTitle());
        boardEntity.setBoardContents(boardDto.getBoardContents());
        boardEntity.setBoardHits(boardDto.getBoardHits());
        boardEntity.setFileAttached(0);

        return boardEntity;
    }

    public static BoardEntity toSaveFileEntity(BoardDto boardDto) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setId(boardDto.getId());
        boardEntity.setBoardWriter(boardDto.getBoardWriter());
        boardEntity.setBoardPass(boardDto.getBoardPass());
        boardEntity.setBoardTitle(boardDto.getBoardTitle());
        boardEntity.setBoardContents(boardDto.getBoardContents());
        boardEntity.setBoardHits(boardDto.getBoardHits());
        boardEntity.setFileAttached(1);

        return boardEntity;
    }
}
