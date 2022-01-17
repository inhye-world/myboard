package edu.example.myboard.service;

import edu.example.myboard.dto.Comment;
import edu.example.myboard.dto.CommentPage;
import edu.example.myboard.mapper.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;

    public List<Comment> commentList(CommentPage commentPage){
        return commentMapper.commentPaging(commentPage);
    }

    /*public List<Comment> commentList(int bnum){
        return commentMapper.commentList(bnum);
    }*/

    public void insertComment(Comment comment) {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String commentDay = format.format(now);

        comment.setCdate(commentDay);

        commentMapper.insertComment(comment);
    }

    public void modifyComment(Comment comment) {
        commentMapper.modifyComment(comment);
    }

    public Comment getComment(int bnum) {
        return commentMapper.getComment(bnum);
    }

    public void deleteComment(int cnum) {
        commentMapper.deleteComment(cnum);
    }

    public int getTotal(int bnum) {
        return commentMapper.getTotal(bnum);
    }

    /*public Page<BoardEntity> getPageList(int pageNo, Search search) throws Exception{
        PageHelper.startPage(pageNo, 10);
        return boardMapper.pagingList(search);
    }*/
}
