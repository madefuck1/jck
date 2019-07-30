var maxSize = 20*1024*1024 ;

//判断文件带下
function checkFileSize(size){
    if(size > maxSize){
        return false ;
    }else{
        return true ;
    }
}