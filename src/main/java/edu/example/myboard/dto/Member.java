package edu.example.myboard.dto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Member {
    @NotBlank (message="아이디는 필수 정보입니다.")
    private String id;

    @NotBlank(message="이름은 필수 정보입니다.")
    private String name;

    @NotBlank(message="비밀번호는 필수 정보입니다.")
    private String pwd;

    @NotBlank(message="비밀번호를 확인해주세요.")
    private String pwd_cf;
    
    private String authority;

    public boolean isPwdEqual(){
        return pwd.equals(pwd_cf);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd_cf() { return pwd_cf; }

    public void setPwd_cf(String pwd_cf) { this.pwd_cf = pwd_cf; }

    public String getAuthority() { return authority; }

    public void setAuthority(String authority) { this.authority = authority; }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", pwd_cf='" + pwd_cf + '\'' +
                ", authority='" + authority + '\'' +
                '}';
    }
}
