package edu.example.myboard.mapper;

import edu.example.myboard.dto.Comment;
import edu.example.myboard.dto.CommentPage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentMapper {

    List<Comment> commentPaging(CommentPage CommentPage);

    List<Comment> commentList(int bnum);

    void insertComment(Comment comment);

    void modifyComment(Comment comment);

    Comment getComment(int bnum);

    void deleteComment(int cnum);

    int getTotal(int bnum);
}

