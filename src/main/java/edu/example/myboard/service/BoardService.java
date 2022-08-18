package edu.example.myboard.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.example.myboard.dto.Board;
import edu.example.myboard.dto.BoardEntity;
import edu.example.myboard.dto.Search;
import edu.example.myboard.mapper.BoardMapper;
import edu.example.myboard.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public List<Board> insertExcelData(String excelFile) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Board> list = null;

        try{
            Workbook wbs = ExcelUtil.getWorkbook(excelFile);
            Sheet sheet = (Sheet)wbs.getSheetAt(0);

            for(int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++){
                Row row = sheet.getRow(i);

                map.put("title", ExcelUtil.cellValue(row.getCell(0)));
                map.put("content", ExcelUtil.cellValue(row.getCell(1)));
                map.put("bname", ExcelUtil.cellValue(row.getCell(2)));
                map.put("bid", ExcelUtil.cellValue(row.getCell(3)));
                map.put("date", ExcelUtil.cellValue(row.getCell(4)));

                boardMapper.insertExcelData(map);
            }
        }catch (Exception e){
            log.error("error : {}", e.getMessage());
        }

        return list;
    }

}
