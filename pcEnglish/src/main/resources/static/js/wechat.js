var appkey = "" ;
var from = "";
var target = "";

var chatMessageTarget = [];   //存储聊天对象
var chatMessage = [] ;   //存储聊天记录


$(".theme-popover-mask").bind("click",function () {
    closeChat();
})

function closeChat(){
    loginOut();
    $("#chatbox").empty();
    $(".theme-popover").hide();
    $(".theme-popover-mask").hide();
}

//底部扩展键
$(function() {
    $('#doc-dropdown-js').dropdown({justify: '#doc-dropdown-justify-js'});
});

$(function(){
	$(".office_text").panel({iWheelStep:32});
});

//tab for three icon	
$(document).ready(function(){
  	$(".sidestrip_icon a").click(function(){
      $(".sidestrip_icon a").eq($(this).index()).addClass("cur").siblings().removeClass('cur');
	  $(".middle").hide().eq($(this).index()).show();
    });
});

//input box focus
$(document).ready(function(){
  	$("#input_box").focus(function(){
       $('.windows_input').css('background','#fff');
       $('#input_box').css('background','#fff');
   });
    $("#input_box").blur(function(){
       $('.windows_input').css('background','');
       $('#input_box').css('background','');
    });
});

window.onload=function b(){
    document.getElementById('send').onclick=function(){
		if(document.getElementById('input_box').value ==''){
            alert('不能发送空消息');
        }else{
		    var text = document.getElementById('input_box').value ;
            //发送聊天记录
            sendSingleMsg(text);
		};
	};
};


/**
 * 初始化
 * @param appkey
 * @param randowStr
 * @param signature
 * @param timetamp
 */

function init(appkey , randowStr , signature , timetamp , fromName , targetName) {
    appkey = appkey ;
    from = fromName;
    target = targetName ;
    JIM.init({
        "appkey": appkey,
        "random_str": randowStr,
        "signature": signature,
        "timestamp": timetamp,
        "flag": 1
    }).onSuccess(function(data) {
        console.log('success:' + JSON.stringify(data));
        login(from,"123456");
    }).onFail(function(data) {
        console.log('error:' + JSON.stringify(data));
    });
}

function login(userName , password) {
    JIM.login({
        'username' : userName,
        'password': password
    }).onSuccess(function(data) {
        console.log('success:' + JSON.stringify(data));
        JIM.onMsgReceive(function(data) {
            console.log('success:' + JSON.stringify(data));
            insert(data.messages[0]);
        });
        //离线消息（漫游）
        JIM.onSyncConversation(function(data){
            console.log('success:' + JSON.stringify(data));
            for(var i = 0 ; i < data[0].msgs.length ; i++){
                insert(data[0].msgs[i]);
            }
        });
    }).onFail(function(data) {
        console.log('error:' + JSON.stringify(data));
    }).onTimeout(function(data) {
        console.log('timeout:' + JSON.stringify(data));
    });
}

function loginOut(){
    JIM.loginOut();
}

function sendSingleMsg(value) {
    JIM.sendSingleMsg({
        'target_username' : target,
        'appkey' :  appkey,
        'content' : value,
        'no_offline' : false,
        'no_notification' : false,
        need_receipt:true
    }).onSuccess(function(data,msg) {
        insert(msg);
    }).onFail(function(data) {
        console.log('error:' + JSON.stringify(data));
    });
}

function sendSinglePic() {
    var fd = new FormData();
    fd.append("file",  $("#chatImage")[0].files[0]);
    JIM.sendSinglePic({
        'target_username' : target,
        'appkey' :  appkey,
        'image' :fd,
        'nead_receipt':true
    }).onSuccess(function(data,msg) {
        insert(msg);
    }).onFail(function(data) {
        console.log('error:' + JSON.stringify(data));
    });
}

function uploadImage() {
    $("#chatImage").click();
}

function showRealPath(){
    sendSinglePic();
    //清空file
    var file = $("#chatImage") ;
    file.after(file.clone().val(""));
    file.remove();
}

/**
 * 填充聊天记录
 * @param message
 */
function insert(message){
    var isExist = false ;
    // for (var i = 0; i < chatMessage.length ; i++) {
    //     if(chatMessageTarget[i] == message.content.target_name ){
    //         isExist = true ;
    //     }
    // }
    // if(!isExist){
    //     var number = chatMessageTarget.length ;
    //     chatMessageTarget[number] = message.content.target_name ;
    //     chatMessage[number] = "" ;
    // }
    //

    if(message.content.from_id == from){
        if(message.content.msg_type == "text"){
            rightInsertText(message.content.msg_body.text);
        }else {
            rightInsertImage(message.content.msg_body.media_id);
        }
    }else if(message.content.target_name = target) {
        if(message.content.msg_type == "text"){
            leftInsertText(message.content.msg_body.text);
        }else {
            leftInsertImage(message.content.msg_body.media_id);
        }
    }
}

function rightInsertText(text){
    document.getElementById('chatbox').innerHTML += '<li class="me"><img src="${contextPath}/images/chat/1.jpg"><span>'+text+'</span></li>';
    cleanInput();
}

function leftInsertText(text){
    document.getElementById('chatbox').innerHTML += '<li class="other"><img src="${contextPath}/images/chat/2.jpg"><span>'+text+'</span></li>';
    cleanInput();
}

function rightInsertImage(mediaId){
    document.getElementById('chatbox').innerHTML += '<li class="me"><img src="${contextPath}/images/chat/1.jpg"><img src="'+mediaId+'"></li>';
    JIM.getResource({
        'media_id' : mediaId,
    }).onSuccess(function(data) {
        $("#chatbox li img").each(function () {
            if($(this).attr("src") == mediaId){
                $(this).attr("src",data.url);
            }
        });
        cleanInput();
    }).onFail(function(data) {
    });
}

function leftInsertImage(mediaId){
    document.getElementById('chatbox').innerHTML += '<li class="other"><img src="${contextPath}/images/chat/1.jpg"><img src="'+data.url+'"></li>';
    JIM.getResource({
        'media_id' : mediaId,
    }).onSuccess(function(data) {
        $("#chatbox li img").each(function () {
            if($(this).attr("src") == mediaId){
                $(this).attr("src",data.url);
            }
        });
        cleanInput();
    }).onFail(function(data) {
    });
}


function cleanInput(){
    document.getElementById('input_box').value = '';
    document.getElementById('chatbox').scrollTop=document.getElementById('chatbox').scrollHeight;
    document.getElementById('talkbox').style.background="#fff";
    document.getElementById('input_box').style.background="#fff";
}