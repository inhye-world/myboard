$(function(){
    $("#uploader").pluploadQueue({
        runtimes : 'html5',
        url : '/board/writeView',

        max_file_count : 1,
        chunk_size : '1mb',

        resize : {
            width : 200,
            height : 200,
            quality : 90,
            crop : true
        },

        filters : {
            max_file_size : '1000mb',

            mime_types : [ {
                title : "Image files",
                extensions : "jpg,gif,png"
            }, {
                title : "Zip files",
                extensions : "zip,war"
            } ]
        },
        rename : true,

        sortable : true,

        dragdrop : true,

        views : {
            list : true,
            thumbs : true,
            active : 'thumbs'
        }
    });
});