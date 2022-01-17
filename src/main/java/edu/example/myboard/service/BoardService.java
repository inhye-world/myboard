package edu.example.myboard.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.example.myboard.dto.Board;
import edu.example.myboard.dto.BoardEntity;
import edu.example.myboard.dto.Search;
import edu.example.myboard.mapper.BoardMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class BoardService {
    @Autowired
    BoardMapper boardMapper;

    public Page<BoardEntity> getPageList(int pageNo, Search search) throws Exception{
        PageHelper.startPage(pageNo, 10);
        return boardMapper.pagingList(search);
    }

    public int countSearch(Search search){
        return boardMapper.searchCnt(search);
    }

    public Board getContent(int bnum) {
        boardMapper.updateHit(bnum);

        return boardMapper.getContent(bnum);
    }

    public void write(Board board) {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String writeday = format.format(now);

        board.setDate(writeday);

        boardMapper.insertPost(board);
    }

    public void delete(int bnum) {
        boardMapper.delete(bnum);
    }

    public void modify(Board board) {
        boardMapper.modify(board);
    }
}
