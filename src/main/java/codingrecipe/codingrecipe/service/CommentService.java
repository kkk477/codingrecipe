package codingrecipe.codingrecipe.service;

import codingrecipe.codingrecipe.dto.CommentDto;
import codingrecipe.codingrecipe.entity.BoardEntity;
import codingrecipe.codingrecipe.entity.CommentEntity;
import codingrecipe.codingrecipe.repository.BoardRepository;
import codingrecipe.codingrecipe.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDto commentDto) {
        return boardRepository
                .findById(commentDto.getBoardId())
                .map(entity -> commentRepository
                        .save(CommentEntity.toCommentEntity(commentDto, entity))
                        .getId()).orElse(null);
    }

    public List<CommentDto> findAll(Long boardId) {
        Optional<BoardEntity> boardEntity = boardRepository.findById(boardId);
        if (boardEntity.isPresent()) {
            List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity.get());
            List<CommentDto> commentDtoList = new ArrayList<>();

            for (CommentEntity commentEntity : commentEntityList) {
                CommentDto commentDto = CommentDto.toCommentDto(commentEntity, boardId);
                commentDtoList.add(commentDto);
            }

            return commentDtoList;
        } else {
            return null;
        }
    }
}
