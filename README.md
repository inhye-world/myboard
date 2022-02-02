## myboard

기본 게시판👩‍💻


*글작성 시에 임베디드 이미지 및 파일업로드를 할 수 있습니다.
*게시판 리스트 화면에서는 제목, 작성자, 내용을 선택하여 검색할 수 있습니다.
*한 페이지당 게시글은 10개입니다.


## 사용방법

현재 로그인은 사이트 로그인만 가능합니다. 추후 sns 로그인도 추가할 예정입니다.

## 로그인/로그아웃

![logout_signin](https://user-images.githubusercontent.com/51586135/149766885-15fe0975-0066-4fc6-8cd4-80c9cac7afb4.gif)  

로그인 후, 로그아웃을 하면 다시 로그인 페이지로 이동합니다.  
회원가입을 통해 신규 아이디가 생성 가능하며 유효성 검사도 진행합니다.  


## 게시물 검색

![search](https://user-images.githubusercontent.com/51586135/149766872-146a35dc-d26f-4581-93f9-e7c320c11a03.gif)  

작성자, 본문, 제목에 따라 게시글을 검색할 수 있습니다.  
한 페이지 당 게시글은 10개입니다.  



## 임베디드이미지 첨부
<img width="485" alt="attached_file" src="https://user-images.githubusercontent.com/51586135/149767601-3d490ee7-f6c9-4f6b-9ab8-a5ac34a87e55.png">  

글작성 시에 이미지와 파일을 첨부할 수 있습니다.


## 댓글기능

<img width="482" alt="reple" src="https://user-images.githubusercontent.com/51586135/149767606-6e06ca6a-ea7e-4543-86cc-4248f72fbc1d.png">

댓글은 수정, 삭제도 가능합니다.



## 프로젝트 구조

css + bootstrap +spring boot로 혼자서 개발했습니다.
사용한 기술 스택은 다음과 같습니다.


* Spring Boot

* Spring Security

* MySQL


## Spring Security

* CSRF : disable
* FormLogin : able


✔️ 전체 사용자가 접근할 수 있어야 하는 페이지는 permitAll을 선언했습니다.

