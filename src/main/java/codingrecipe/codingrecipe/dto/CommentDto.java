package codingrecipe.codingrecipe.dto;

import codingrecipe.codingrecipe.entity.BoardEntity;
import codingrecipe.codingrecipe.entity.CommentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
public class CommentDto {

    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private LocalDateTime commentCreatedTime;

    public static CommentDto toCommentDto(CommentEntity commentEntity, Long boardId) {
        CommentDto commentDto = new CommentDto();

        commentDto.setId(commentEntity.getId());
        commentDto.setCommentWriter(commentEntity.getCommentWriter());
        commentDto.setCommentContents(commentEntity.getCommentContents());
        commentDto.setBoardId(boardId);
        commentDto.setCommentCreatedTime(commentEntity.getCreatedDate());

        return commentDto;
    }
}
