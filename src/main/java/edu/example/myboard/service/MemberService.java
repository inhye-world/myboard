package edu.example.myboard.service;

import edu.example.myboard.dto.Login;
import edu.example.myboard.dto.Member;
import edu.example.myboard.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MemberService{
    @Autowired
    MemberMapper memberMapper;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public Map<String, String> validHandlier(Member form, Errors errors){
        Map<String, String> validRes = new HashMap<>();

        for(FieldError error : errors.getFieldErrors()){
            String validKey = String.format("invalid_%s", error.getField());
            validRes.put(validKey, error.getDefaultMessage());
        }

        return validRes;
    }

    public void signup(Member form) throws Exception{
        //비밀번호 암호화
        String encoded = passwordEncoder.encode(form.getPwd());
        form.setPwd(encoded);

        memberMapper.singup(form);
    }

    public Boolean checkUser(Login login) {
        String rawPwd = login.getPwd();
        String userPwd = memberMapper.chkPwd(login.getId());

        return passwordEncoder.matches(rawPwd, userPwd);
    }

    public String selectName(String id) {
        return memberMapper.getUserName(id);
    }

    public Member chkId(String id) {
        return memberMapper.getSameUserId(id);
    }
}
