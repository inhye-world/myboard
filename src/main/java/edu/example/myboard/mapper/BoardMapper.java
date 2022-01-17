package edu.example.myboard.mapper;

import com.github.pagehelper.Page;
import edu.example.myboard.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BoardMapper {

    Page<BoardEntity> pagingList(Search search);

    int searchCnt(Search search);

    int getCount();

    Board getContent(int bnum);

    void insertPost(Board board);

    void delete(int bnum);

    void modify(Board board);

    void updateHit(int bnum);
}

