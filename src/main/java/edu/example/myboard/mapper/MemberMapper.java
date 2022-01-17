package edu.example.myboard.mapper;

import edu.example.myboard.dto.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MemberMapper {

    void singup(Member member);

    String chkPwd(String id);

    String getUserName(String id);

    Member getSameUserId(String id);

    Member findByUsername(String username);
}

