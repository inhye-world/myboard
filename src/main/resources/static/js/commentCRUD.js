/*<![CDATA[*/
$("#insertReply").click(function(){
    let bno = [[${content.bnum}]];
    let replytext =$("#replytext").val();

    $.ajax({
        type: 'POST',
        url: "/board/insertReply",
        data:{
            cmt_content:replytext,
            bnum:bno,
        },
        success: function() {
            commentList();
        },
        error: function(status){
            console.log("에러: " + status);
        }
    })
})

//입력 창 끼워넣기
function commentUpdate(cnum, cmt_content){
    var str ='';

    str += '<div class="makeInputBox">';
    str += '<input type="text" name="cmt_content" value="'+cmt_content+'"/>';
    str += '<span><button class="goModify" type="button" onclick="commentUpdateProc('+cnum+');">수정</button> </span>';
    str += '</div>';

    $('.commentContent'+cnum).html(str);
}

//댓글 수정
function commentUpdateProc(cnum){
    let updateContent = $('[name=cmt_content]').val();

    $.ajax({
        url : '/board/commentUpdate',
        type : 'POST',
        data : {
            cmt_content : updateContent,
            cnum : cnum
        },
        success : function(status){
            console.log("수정 성공!: "+status);
            commentList();
        },
        error:function(status){
            console.log("수정 결과: "+status);
        }
    });
}

function commentDelete(cnum){
    if(confirm("정말 댓글을 삭제하시겠습니까?") === true){
        $.ajax({
            url : '/board/commentDelete',
            type : 'GET',
            data : {cnum : cnum},
            success : function(status){
                console.log("삭제 성공!: "+status);
                commentList();
            },
            error:function(status){
                console.log("삭제 결과: "+status);
            }
        });
    }
}
/*]]>*/