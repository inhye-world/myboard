package edu.example.myboard.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import edu.example.myboard.dto.*;
import edu.example.myboard.security.CustomUserDetails;
import edu.example.myboard.service.BoardService;
import edu.example.myboard.service.CommentService;
import edu.example.myboard.service.MemberService;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Controller
public class BoardController {

    @Autowired
    MemberService memberService;

    @Autowired
    BoardService boardService;

    @Autowired
    CommentService commentService;

    @GetMapping("/")
    public String main(){
        return "board/main";
    }

    @GetMapping("/board/list")
    public String page(@RequestParam(required = false, defaultValue = "1") int pageNum,
                       @AuthenticationPrincipal CustomUserDetails principal,
                       @ModelAttribute Search search, Model model) throws Exception{
        PageInfo<BoardEntity> post = new PageInfo<>(boardService.getPageList(pageNum, search), 10);

        String accessId = principal.getUsername();
        String userName = memberService.selectName(accessId);
        int searchCnt = boardService.countSearch(search);

        model.addAttribute("username", userName);
        model.addAttribute("search", search);
        model.addAttribute("searchCnt", searchCnt);
        model.addAttribute("name", principal.getUsername());
        model.addAttribute("boardList", post);
        model.addAttribute("search",search);
        return "board/boardList";
    }

    @RequestMapping(value ="/board/contentView-{bnum}")
    public String contentView(@PathVariable("bnum") int bnum,
                              @AuthenticationPrincipal CustomUserDetails principal, Model model) throws Exception{

        String accessId = principal.getUsername();
        String userName = memberService.selectName(accessId);
        model.addAttribute("accessId", accessId);
        model.addAttribute("username", userName);

        Board content = boardService.getContent(bnum);

        model.addAttribute("content", content);

        return "board/contentPage";
    }

    @GetMapping (value ="/board/writeView")
    public String writeView(@ModelAttribute Board board, Model model, @AuthenticationPrincipal CustomUserDetails principal){

        String accessId = principal.getUsername();
        String userName = memberService.selectName(accessId);

        model.addAttribute("userName", userName);

        return "board/writePage";
    }

    @Value("${file.upload-dir}")
    String uploadFileDir;

    @PostMapping (value ="/board/writeView")
    public String write(@RequestParam("file") MultipartFile file, Board board,
                        @AuthenticationPrincipal CustomUserDetails principal, Model model) throws Exception{

        String accessId = principal.getUsername();
        String userName = memberService.selectName(accessId);
        board.setBid(accessId);
        board.setBname(userName);

        model.addAttribute("userName", userName);

        if(file.isEmpty()){
            boardService.write(board);
        }else{
            String fileName = file.getOriginalFilename();
            File destinationFile;
            String destinationFileName;

            do{
                destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." +fileName;
                destinationFile = new File(uploadFileDir + destinationFileName);
            }while(destinationFile.exists());


            file.transferTo(destinationFile);

            board.setFile_name(fileName);
            board.setFile_save_name(destinationFileName);
            board.setFile_download_uri(uploadFileDir);

            boardService.write(board);
        }

        return "redirect:/board/list";
    }

    ////////////////////////////이미지 업로드///////////////////////////////

    @Value("${img.upload-dir}")
    String uploadImgDir;
    @PostMapping (value="/board/withImage")
    @ResponseBody
    public void imageUpload(HttpServletResponse response, MultipartHttpServletRequest multifile) throws Exception{
        response.setContentType("application/json");

        PrintWriter printWriter = null;
        OutputStream out = null;

        MultipartFile file = multifile.getFile("upload");

        if(file != null){
            if(file.getSize()>0 && StringUtils.isNotBlank(file.getName())){
                try{
                    String fileName = file.getName();
                    byte[] bytes = file.getBytes();
                    String uploadPath = uploadImgDir;

                    log.info("=====================uploadPath: "+uploadPath+" ====================");

                    //fileName = UUID.randomUUID().toString();
                    UUID uid = UUID.randomUUID();

                    //uploadPath = uploadPath+fileName;
                    uploadPath = uploadPath+uid+"_"+fileName;

                    out = new FileOutputStream(new File(uploadPath));
                    out.write(bytes);

                    printWriter = response.getWriter();
                    response.setContentType("text/html");
                    //String fileUrl = "/board/withImage?file:///"+uploadPath;
                    String fileUrl = "/board/previewImage?uid="+uid+"&fileName="+fileName;

                    log.info("=====================fileUrl: "+fileUrl+" ====================");

                    JsonObject json = new JsonObject();
                    json.addProperty("uploaded", 1);
                    json.addProperty("fileName", fileName);
                    json.addProperty("url", fileUrl);

                    printWriter.println(json);

                }catch (IOException ie){
                    ie.printStackTrace();
                }finally {
                    if(out != null){ out.close(); }
                    if(printWriter != null){ printWriter.close(); }
                }
            }
        }
    }

    //json방식으로 데이터 전송 시 이미지 미리보기가 바로 생성될 것으로 보였으나
    //405에러가 발생하여 GET방식으로 데이터 전송이 제대로 이루어지지 않음을 확인 -> GET방식 매핑을 추가
    //이미지 업로드 시, 파일명 중복 방지를 위해 uid를 적용했던 fileName을 담은 fileUrl을 json으로 넘겼으므로,
    //uid와 fileName을 파라미터로 넘겨야했다.
    @RequestMapping(value="/board/previewImage")
    public void imagePreview(@RequestParam(value = "uid") String uid, @RequestParam(value = "fileName") String fileName, HttpServletResponse response) throws Exception{

        String path = uploadImgDir;

        String sDirPath = path + uid + "_" + fileName;

        File imgFile = new File(sDirPath);

        if(imgFile.isFile()){
            byte[] buf = new byte[1024];
            int readByte = 0;
            int length = 0;
            byte[] imgBuf = null;

            FileInputStream fileInputStream = null;
            ByteArrayOutputStream outputStream = null;
            ServletOutputStream out = null;

            try{
                fileInputStream = new FileInputStream(imgFile);
                outputStream = new ByteArrayOutputStream();
                out = response.getOutputStream();

                while((readByte = fileInputStream.read(buf)) != -1){
                    outputStream.write(buf, 0, readByte);
                }

                imgBuf = outputStream.toByteArray();
                length = imgBuf.length;
                out.write(imgBuf, 0, length);
                out.flush();

            }catch(IOException e){
                log.info(String.valueOf(e));
            }
        }
    }

    //////////////////////////////////////////////////////////////////////

    @GetMapping("/download/{bnum}")
    public void filedownload(@PathVariable("bnum") int bnum, HttpServletResponse response) throws Exception{
        Board board = boardService.getContent(bnum);

        try{
            //테스트 서버
            String savePath = "/apps/tomcat3/work/Catalina/localhost/ROOT/uploadfiles/";

            //로컬
            //String savePath = board.getFile_download_uri();

            String fileName = board.getFile_save_name();
            String oriFileName = board.getFile_name();

            InputStream in = null;
            OutputStream out = null;
            File file = null;

            try{
                file = new File(savePath, fileName);
                in = new FileInputStream(file);

                response.reset();
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + new String(oriFileName.getBytes(StandardCharsets.UTF_8), "ISO8859_1") + "\"");
                response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");

                response.setHeader("Content-Length", "" + file.length());
                out = response.getOutputStream();
                byte[] bytes = new byte[(int) file.length()];
                int length = 0;
                while ((length = in.read(bytes)) > 0) {
                    out.write(bytes, 0, length);
                }
            } catch (FileNotFoundException fe) {
                throw new RuntimeException("file Load Error");
            }
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }


    /*
    @GetMapping("/download/{bnum}")
    public void filedownload(@PathVariable("bnum") int bnum, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Board board = boardService.getContent(bnum);

        //String fileName = board.getFile_save_name();
        String fileName = board.getFile_name();

        //로컬
        StringBuilder sb = new StringBuilder(uploadFileDir);

        //테스트 서버
        //StringBuilder sb = new StringBuilder("/apps/tomcat3/work/Catalina/localhost/ROOT/uploadfiles/");
        sb.append(fileName);

        String downloadname = sb.toString();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");

        try(FileInputStream fis = new FileInputStream(downloadname); OutputStream out = response.getOutputStream();){
            int readCount = 0;
            byte[] buffer = new byte[1024];
            while ((readCount = fis.read(buffer)) != -1) {
                out.write(buffer, 0, readCount);
            }
        } catch (Exception ex) {
            throw new RuntimeException("file Load Error");
        }
    }*/

    @RequestMapping (value ="/board/delete-{bnum}")
    public String delete(@PathVariable("bnum") int bnum){

        boardService.delete(bnum);

        return "redirect:/board/list";
    }

    @GetMapping(value ="/board/modify-{bnum}")
    public String modifyView(@PathVariable("bnum") int bnum, @ModelAttribute Board board, Model model){

        model.addAttribute("temp", boardService.getContent(bnum));
        return "board/modifyPage";
    }

    @PostMapping(value ="/board/modify")
    public String modify(@RequestParam("file") MultipartFile file, Board board) throws Exception{

        if(file.isEmpty()){
            boardService.modify(board);
        }else{
            String fileName = file.getOriginalFilename();
            File destinationFile;
            String destinationFileName;

            do{
                destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." +fileName;
                destinationFile = new File(uploadFileDir + destinationFileName);
            }while(destinationFile.exists());

            file.transferTo(destinationFile);

            board.setFile_name(fileName);
            board.setFile_save_name(destinationFileName);
            board.setFile_download_uri(uploadFileDir);

            boardService.modify(board);
        }

        return "redirect:/board/list";
    }
}



