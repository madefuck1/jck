function getCookie(key){
    var attr1=document.cookie.split('; ');//通过分号+空格把获取的cookie拆分；attr1存储cookie数组；
    for(var i=0;i<attr1.length;i++){	//分别获取每一个cookie。cookie是由“名字=值”组成的；
        var attr2=attr1[i].split('=');	//把名字与值拆分
        if(attr2[0]==key){	//如果名字=参数（想要获取的cookie），则返回其值；
            return attr2[1];
        }
    }
}

function setCookie(key , value){
    var oDate=new Date()//新建一个日期对象；
    oDate.setDate(oDate.getDate()+1)//设置时间为：用户访问当前页面的后5天；起返回值是object类型的。
    document.cookie= key+'='+value+';path=/;expires'+oDate.toGMTString();
}