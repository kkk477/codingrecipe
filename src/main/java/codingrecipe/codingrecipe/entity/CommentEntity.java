package codingrecipe.codingrecipe.entity;

import codingrecipe.codingrecipe.dto.BoardDto;
import codingrecipe.codingrecipe.dto.CommentDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "comment")
public class CommentEntity extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column()
    private String commentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    public static CommentEntity toCommentEntity(CommentDto commentDto, BoardEntity boardEntity) {
        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setId(commentDto.getId());
        commentEntity.setCommentWriter(commentDto.getCommentWriter());
        commentEntity.setCommentContents(commentDto.getCommentContents());
        commentEntity.setBoardEntity(boardEntity);

        return commentEntity;
    }
}
