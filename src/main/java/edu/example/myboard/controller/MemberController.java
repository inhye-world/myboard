package edu.example.myboard.controller;

import edu.example.myboard.dto.Login;
import edu.example.myboard.dto.Member;
import edu.example.myboard.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

    //로그인 화면창
    @GetMapping("/login")
    public String loginPage(@ModelAttribute Login login) throws Exception{

        return "member/loginForm";
    }
    //로그인 실패
    @GetMapping("/login/failLogin")
    public String loginFail() {

        return "member/failLogin";
    }

    //회원가입 페이지
    @GetMapping(value = "/member/join")
    public String signupPage(Member member){

        return "member/signupForm";
    }

    //회원가입 처리
    @PostMapping(value = "/member/join")
    public String signup(@Valid Member member, Errors errors, Model model) throws Exception{

        Member chksameid = memberService.chkId(member.getId());

        if(chksameid != null) {
            model.addAttribute("invalid_id", "이미 존재하는 아이디 입니다.");
            return "member/signupForm";
        }
        //비밀번호 확인이 일치하지 않을 때
        if(!member.getPwd().isEmpty()){
            if(!member.isPwdEqual()){
                model.addAttribute("invalid_pwd_cf", "비밀번호가 일치하지 않습니다.");
                return "member/signupForm";
            }
        }

        //Member에 지정한 어노테이션 에러에 관련해서만!
        if(errors.hasErrors()) {
            model.addAttribute("member", member);

            Map<String, String> validation = memberService.validHandlier(member, errors);
            for(String key: validation.keySet()){
                model.addAttribute(key, validation.get(key));
            }

            return "member/signupForm";
        }

        memberService.signup(member);
        return "redirect:/login";
    }
}
