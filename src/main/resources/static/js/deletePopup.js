function checkDelete(bnum) {
    if(confirm("정말 삭제하시겠습니까?") === true){
        location.href = "/board/delete-"+ bnum;
    }
}