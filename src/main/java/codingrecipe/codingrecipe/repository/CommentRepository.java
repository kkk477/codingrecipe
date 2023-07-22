package codingrecipe.codingrecipe.repository;

import codingrecipe.codingrecipe.entity.BoardEntity;
import codingrecipe.codingrecipe.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
}
