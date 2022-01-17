package edu.example.myboard.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import edu.example.myboard.dto.*;
import edu.example.myboard.security.CustomUserDetails;
import edu.example.myboard.service.BoardService;
import edu.example.myboard.service.CommentService;
import edu.example.myboard.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Controller
public class CommentController {

    @Autowired
    MemberService memberService;

    @Autowired
    CommentService commentService;

    @RequestMapping (value = "/board/insertReply")
    public @ResponseBody void insertReply(@RequestParam Map<String, Object> param, Comment comment,
                                          @AuthenticationPrincipal CustomUserDetails principal) throws Exception{

        String accessId = principal.getUsername();
        String userName = memberService.selectName(accessId);

        comment.setCid(accessId);
        comment.setCname(userName);
        comment.setCmt_content((String) param.get("cmt_content"));

        int postNo = Integer.parseInt(String.valueOf(param.get("bnum")));
        comment.setBnum(postNo);

        commentService.insertComment(comment);
    }

    @RequestMapping(value = "/board/commentList")
    @ResponseBody
    public Map<String, Object> commentList(@RequestParam(value = "bnum") int bnum, CommentPage commentPage,
                                     @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                     @AuthenticationPrincipal CustomUserDetails principal, Comment comment, Model model) throws Exception{

        String accessId = principal.getUsername();
        String writerId = comment.getCid();
        Map<String, Object> result = new HashMap<String, Object>();

        //한 블록에 댓글 10개씩 블록 5개
        int limit = 5;
        double perBlock = 10.0;

        int total = commentService.getTotal(bnum);

        int maxPage = (int)Math.ceil(total/perBlock);

        int startPage = maxPage-limit+1;
        int endPage = maxPage;

        if (total < perBlock * limit) {
            startPage = 1;
            endPage = maxPage;
        }

        if(page<maxPage-limit+1) {
            startPage = (int) ((int) (((page - 1) / perBlock) * perBlock) + 1.0);
            endPage = (startPage + limit) - 1;
        }

        /*int lastBlock = (int)Math.ceil(maxPage % limit);

        for(int i = 0; i < lastBlock; i++) {
            if (total < perBlock * limit * (i+1)) {
                startPage = (i*limit)+1;
                endPage = maxPage;
            }
        }

        for(int i = 1; i <= lastBlock; i++){
            if(page>= ((i-1)*limit)+1 && page<=limit*(i+1)) {
                startPage = (limit*(i-1))+1;
                endPage = limit * i;
            }
        }

        if((lastBlock * limit) > maxPage){
            endPage = maxPage;
        }*/

        int start = (page - 1) * 10;
        int isPrev = startPage-1;
        int isNext = page+1;

        if(isNext > maxPage){
            isNext = 0;
        }

        commentPage.setBnum(bnum);
        commentPage.setLimit(limit);
        commentPage.setCurPage(page);
        commentPage.setStart(start);
        commentPage.setStartPage(startPage);
        commentPage.setEndPage(endPage);
        commentPage.setMaxPage(maxPage);

        model.addAttribute("accessId", accessId);
        model.addAttribute("writerId", writerId);

        List<Comment> commentList = commentService.commentList(commentPage);

        result.put("total", total);
        result.put("limit", limit);
        result.put("startPage", startPage);
        result.put("isPrev", isPrev);
        result.put("isNext", isNext);
        result.put("commentList", commentList);
        result.put("page", page);
        result.put("endPage", endPage);

        return result;
    }

    /*
    @GetMapping(value = "/board/commentList")
    @ResponseBody
    public List<Comment> commentList(@RequestParam(value = "bnum") int bnum, @AuthenticationPrincipal CustomUserDetails principal, Comment comment, Model model) throws Exception{

        String accessId = principal.getUsername();
        String writerId = comment.getCid();

        model.addAttribute("accessId", accessId);
        model.addAttribute("writerId", writerId);
        return  commentService.commentList(bnum);
    }
    */

    @PostMapping(value = "/board/commentUpdate")
    public @ResponseBody void modifyReply(@RequestParam Map<String, Object> param, Comment comment) throws Exception{

        comment.setCmt_content((String) param.get("cmt_content"));

        int commentNo = Integer.parseInt(String.valueOf(param.get("cnum")));
        comment.setCnum(commentNo);
        commentService.modifyComment(comment);
    }

    @GetMapping(value = "/board/commentDelete")
    public @ResponseBody void deleteReply(@RequestParam(value = "cnum") int cnum, Comment comment) throws Exception{

        commentService.deleteComment(cnum);
    }
}



