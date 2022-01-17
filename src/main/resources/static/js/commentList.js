/*<![CDATA[*/
function commentList(){
    let bno = [[${content.bnum}]];
    $.ajax({
        url: '/board/commentList?bnum'+bno,
        type: 'get',
        data : {'bnum': bno},
        success : function (data) {
            console.log("데이터 확인");
            console.log(data);

            let str = '';
            $.each(data, function(index, item){
                str += '<div class="commentArea">';
                str += '<div class="commentInfo'+item.cnum+'">'+' 작성자 : '+item.cname+'('+item.cdate+')';
                str += '<div class="commentContent'+item.cnum+'"> <p class="paragraphArea"> 내용 : '+item.cmt_content +'</p>';
                if([[${accessId}]] === item.cid){
                    str += '<a style="cursor: pointer" onclick="commentUpdate('+item.cnum+',\''+item.cmt_content+'\');"> 수정 </a>';
                    str += '<a style="cursor: pointer" onclick="commentDelete('+item.cnum+');"> 삭제 </a> </div>';
                }
                str += '</div></div>';
            });
            $("#replyList").html(str);
        }
    });
}
/*]]>*/