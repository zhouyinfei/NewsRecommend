/*一些通用的方法和变量
 *author by cxm
 */ 
var  _timeoutAjax=10000 ,
_timerId = 0,
_timeout = 900000,// 15分钟
_IntervalTimeout = 60000,// 60秒
_counter = _timeout,
map_text={},
language="zh";//需要与手机一致
var apiVersion="2.2";
var isIOS=false;//标记是否是IOS设备，js交互不一样
$(function() {
	console.log("loadddddd");
	isIOS = !!navigator.userAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	language=sessionStorage.getItem("language");
	
//	if(language){
//		
//	}else{
//		language=check_userlanguage();
//	}
//	initLanguageTextMap("", function() {
//		map_text=$.i18n.map;
//	});
////	ConsoleInfo(map_text.expire_tip);
//if(map_text.expire_tip==null||map_text.expire_tip==undefined){map_text.expire_tip="登陆已过期，请重新登陆！";}
//	
//	$(".before-content-spinner").before("<div id='expire-dialog' class='expire-dialog'> <div class='exdiv1' align='center'><div id='exDialogTitle'>过期提示</div><div id='exDialogText'>"+map_text.expire_tip+"</div> <div><span id='loginout'>确定</span></div> </div></div>");
////	$("center").before("<div id='expire-dialog' class='expire-dialog'> <div class='exdiv1' align='center'><div id='exDialogTitle'>" +map_text.expire_tip+"</div> <div><span id='loginout'>确定</span></div> </div></div>");
//	$(".before-content-spinner").before(" <div id='spinner' class='spinner'  ><div align='center' class='child'> <div class=' iconfont icon-spinner spinner_loading'>  </div></div> </div>");
//
//	updateExpireAndSpinnerUI();
//	  $(".exdiv1").popup( $("#exDialogTitle"),{ifDrag:true,dragLimit:true});
//	$("html").click(function() {
//		_counter = _timeout;
//		_IntervalTimeout = 60000;
//		_closeTimer();
//		if(location.pathname.lastIndexOf("login")!=-1){
//			return;
//		}else  if(location.pathname.lastIndexOf("regis")!=-1){
//				return;
// 		 }
//		_startTimer();
//	});
//	$("body").on("click","#loginout",function(){
//		$("#expire-dialog").css("display", "none");
//		_logout();
//	}); 
});
function updateExpireAndSpinnerUI(){
	var width=parseInt($(document).width()) ;
	var height=parseInt($(document).height()) ;
	$("#spinner").css("width",width  +"px");
	$("#spinner").css("height",  height  + "px");
//	$("#spinner").css("line-height",  height + "px");
	var icon=$("#spinner .iconfont");
	var width_icon=parseInt(icon.css("width"));
//	icon.css("margin-left", (width/2-width_icon)+"px");
//	console.log(width+":hei="+height);
	
	var y=window.parent.document.body.scrollTop;
	$(".exdiv1").css("top",(y<60?180:y+60)+"px");
}

function _logout(){
	var type=sessionStorage.getItem("type");
	sessionStorage.clear();
//	window.location.href = getURLBasePath() + "loginout";
	if(type=="sys"){
		type="Configuration";
	}else{
		type="Customer";
	} 
//	window.location.href=base_url_newton+type+"/loginout";
	if(self.frameElement){//判断处于frame中
//		window.parent.location.href= "./loginout";
		window.parent.location.href= "./login.html";
	}else{
//		window.location.href= "./loginout";
		window.location.href= "./login.html";
	}
}
function _startTimer() {
	_timerId = setInterval(function() {
		_counter -= _IntervalTimeout;
		if (_counter <= 0) {
//			 ("token已过期，请重新登录！");
//			$("#expire-dialog").css("display","block");
			_closeTimer();
			return;
		} else if (_counter <= 10000) {// 最后10秒倒计时
			if (_IntervalTimeout != 1000) {
				_IntervalTimeout = 1000;
				_closeTimer();
				_startTimer();
			}
		} else if (_counter <= 60000) {// 最后60秒
			_IntervalTimeout = 5000;
		}
	}, _IntervalTimeout);
}
function _closeTimer() {
	clearInterval(_timerId);
}
function getURLBasePath() {
	path = window.location.pathname;
	paths = path.split("/");
	if (paths.length != 0) {
		path = "";
		for ( var i in paths) {
			if (i != paths.length - 1) {
				path += paths[i] + "/";
			}
		}
	}
	// window.ConsoleInfo(path);
	return path;
}

/**
 * just to Check the token if has expired.检查Token是否过期
 * 
 * @param msg
 * @param funcId:1=Configration；-1=Customer
 * @param evt
 */
function loadErrForToken(msg, funcId, evt) {
	loadEnding();
	if ((funcId == 1 && msg.retcode == "203")
			|| (funcId == -1 && msg.retcode == "203")) {
		evt();
//		$("#exDialogTitle").text(map_text.expire_tip);//("登陆已过期，请重新登陆！--");
//		$("#expire-dialog").css("display","block");
		setInterval(function() {
			_logout();
			console.log("前往登录");
		}, 1500);
		alert("登录过期");
		_closeTimer();
		return true;
	}
	return false;
}
/**
 * 加载开始
 * 
 */
function loadStart() {
	$("#spinner").css("display", "block");
}
/**
 * 加载结束
 * 
 */
function loadEnding() {
	updateExpireAndSpinnerUI();
	$("#spinner").hide();
//	$("#spinner").css("display", "none");
//	ConsoleInfo("隐藏="+$("#spinner").css("display"));
}
/**
 * 加载失败
 */
function loadError(evt) {
	loadEnding();
	error_ok_func=function(){
		evt();
		return true;
	};
	if(!map_text.load_fail)
	{
		map_text.load_fail="加载失败，请重试";
	}
	var flag = window.confirm(map_text.load_fail ,"error_ok_func");//"加载失败，请重试！"
//	if (flag) {
//		evt();
//	}
}



/**mms->str  将时间戳（微秒）转为固定格式时间串
 * @param longTime
 * @returns
 */
function getTimeSdf(longTime) {
	var date = new Date();
	date.setTime(longTime/1000 );
	return formatterDate(date);
}
/**ms->str  将时间戳(毫秒)转为固定格式时间串
 * @param longTime
 * @returns
 */
function getTimeSdfFromTimestampMSecond(longTime) {
	if(longTime==0){return 0;}
	return getTimeSdf(longTime*1000);
}
/**
 * 格式化日期（含时间"00:00:00"）
 */
function formatterDate  (date) {
	
	var timezone = -date.getTimezoneOffset() / 60;
	var hour = date.getHours();
	var minute = date.getMinutes();
	var second = date.getSeconds();
	minute = minute >= 10 ? minute : "0" + minute;
	hour = hour >= 10 ? hour : "0" + hour;
	second = second >= 10 ? second : "0" + second;
	if (timezone > 0) {
		timezone = "GMT+" + (timezone >= 10 ? timezone : "0" + timezone);
	} else
		timezone = "GMT" + (timezone <= -10 ? timezone : "-0" + (-timezone));

	var datetime = date.getFullYear()
			+ "-"// "年"
			+ ((date.getMonth() + 1) >= 10 ? (date.getMonth() + 1) : "0"
					+ (date.getMonth() + 1)) + "-"// "月"
			+ (date.getDate() < 10 ? "0" + date.getDate() : date.getDate())
			+ " " + hour + ":" + minute + ":" + second ;//+ " " + timezone;
	//  ConsoleInfo(date.getTime() + " zone=" + timezone);
	return datetime; 
	
}
/**获取不含具体时间点的时间月份日期格式串
 * @param date
 * @returns { String}
 */
function formatterDateNoMinuteValue  (longtime) {
	var date = new Date();
	date.setTime(longtime  );
	var timezone = -date.getTimezoneOffset() / 60;
	var datetime = date.getFullYear()
	+ "-"// "年"
	+ ((date.getMonth() + 1) >= 10 ? (date.getMonth() + 1) : "0"
		+ (date.getMonth() + 1)) + "-"// "月"
		+ (date.getDate() < 10 ? "0" + date.getDate() : date.getDate())
		 ;
	return datetime; 
}

/**from str->timestamp number
 * 从字符串到时间戳
 * 
 * @param strTime
 * @returns timestamp number
 */
function getTimeStamp(strTime) {
//	return Date.parse(new Date(strTime));//这样直接转IOS不识别，转的结果是NAN。
	strTime=(strTime+"").replace(/\-/g, '/');
	return Date.parse(new Date(strTime));
}

/**from STR="hour:minute" to get second number
 * 将时间点字符串转为秒数.
 * @param strTime
 * @returns
 */
function getTimeSecondFromStr(strTime) {
	strTime+="";
	var ts=strTime.split(":") ;
	if(ts==null||ts.length<2){
		return 0;
	}
	var t1=parseInt(ts[0]+""),
	t2=parseInt(ts[1]+"");
	return  t1*60*60+t2*60;
}
/**from second number to get time string.
 * 从秒数获取时间点字符串：60——>0:01
 * @param second
 * @returns {String}
 */
function getTimeStrFormSecond(second){
	second ="0"+second;
	second=parseInt(second);
	var hour=parseInt(second/(60*60));
	var minute=parseInt((second/60)%60);
	hour=hour<10?"0"+hour:hour;
	minute=minute<10?"0"+minute:minute;
	return hour+":"+minute;
}



/**
 * 判断值是否为空：为空则返回false
 * 
 * @param ob
 * @returns {Boolean}
 */
function isDefinedNotNull(ob) {
	if (ob == undefined) {
		return false;
	}
	if (ob == null) {
		return false;
	}
	if (ob.length == 0) {
		return false;
	}
	return true;
}
 

/*获取多语言内容
*/
function initLanguageTextMap(fname,callback) {  
	if(fname==null||fname.length==0){fname='messages';}
	 $.i18n.properties({
		  name : fname, //资源文件名称
		  path : '../i18n/', //资源文件路径
		  mode : 'map', //用Map的方式使用资源文件中的值
		  language :check_userlanguage(),//返回浏览器语言
		  cache:true,
		  async:true,
		  callback : callback   //加载成功后设置显示内容
	  });
	 callback();
	}

/*
 * 校验当前语言类型，决定显示的文字语言
 */
check_userlanguage = function() {
 
    /* get browser default lang */
    if (navigator.userLanguage) {
        baseLang = navigator.userLanguage.substring(0,2).toLowerCase();
    } else {
        baseLang = navigator.language.substring(0,2).toLowerCase();
    }
    
    /* language match */
    switch(baseLang)
    {
        case "de":
            /* german */
//            this.slideDown("slow");
            break;
        case "en":
            /* english */
            break;
        case "ja":
            /* japanese */
            break;
        case "zh":
            /* 中文 */
//            break;
            return "zh";
        default:
            /* default no match */
    }
    return "en";
}; 




/**
 北极熊
在HTML网页中使用js获取参数 还有更合理的方式，（使用正则） 。
 * @fileoverview 解析Get参数
 * @param {String} Get参数名
 * @return {String} Get参数值 
 */
var GetParameter = function(name){
//document.location.href 返回完整的 URL。 如：http://www.cftea.com/foo.asp?p=1
//location.search是从当前URL的?号开始的字符串 
    var locString = document.location.search;
    if(locString==null||locString.length==0||locString.indexOf(name+"=")<=0){
    	return "";
    }
      var reg = new RegExp("(\\?|\\&)" + name + "=([^\\&]*)(\\&?)", "i").exec(locString);
     var p2=RegExp.$2;
    p2= decodeURIComponent( p2);
      return   p2;
}
/**
 * 有的键为数值时不能直接获取到对应的值，所以需要用以下方式。
 */
var getJsonOneValue=function(jsonObj,key){
	var value="";
	$.each(jsonObj, function(k, v) {// 遍历JSON获取值（因为键名是数字所以需要这样获取）
		if (k == key ) {
			value=v;
			return v ;
		}
	});
	return value;
}
/*
 * 强制刷新缓存：manifest机制
 */
var reloadjs=function(){
	 var appCache = window.applicationCache,
	code=appCache.status;
	try{
		appCache.addEventListener('updateready', function(){
//		 window.location.reload();
			appCache.swapCache();
		});
		if (code == window.applicationCache.UPDATEREADY) {  
			appCache.swapCache();  // The fetch was successful, swap in the new cache.  
//			window.location.reload();
		}else if(code!=appCache.UNCACHED&&code!=appCache.IDLE){
			appCache.update(); // Attempt to update the user's cache.  
				setTimeout(function(){
					if(appCache.status==appCache.UPDATEREADY){
						appCache.swapCache();  // The fetch was successful, swap in the new cache.  
//						window.location.reload();
					}
					
	}, 3*1000);
		}
	}catch (e) {
		// TODO: handle exception
		ConsoleError(e);
	}
} 

//url需要参数：jsonp_callback=ret
var ret=function(text){ };//用来过渡跨域访问的结果的方法，需要为ajax配置jsonpCallback:"ret"
var ret2=function(text){ };//用来过渡跨域访问的结果的方法，需要为ajax配置jsonpCallback:"ret"
var rets=function(text){ };//用来过渡跨域访问的结果的方法，需要为ajax配置jsonpCallback:"ret"
/**
 * newton:Url base
 */  
//var base_url_newton="https://iot.zhuyun-it.com:9343/newton/";
//var base_url_newton="/newton/";
var base_url_newton= "/"+location.pathname.split("/", 2)[1]+"/";
//var base_url_cbs="https://iot.zhuyun-it.com:9343/cbs/";
var base_url_cbs="/cbs/";
var base_url_ip_pic="http://192.168.10.15:14000/webhdfs/v1/usr/lib/newton/pic/";
function getBaseUrlPic(picName,tenantId){
	if(!tenantId){
		tenantId=sessionStorage.getItem("tenantId");
	}
	if(!tenantId){
		tenantId=GetParameter("tenantId");
	}
//	return "./hadoopPic2?tenantId="+tenantId+"&token=t&picName="+picName+"&picUrl="+base_url_ip_pic+picName+"?user.name=root&op=OPEN";
	var ip=location.hostname;
	if(ip.indexOf("10.114")>=0){
		ip="192.168.10.15";
	}
	return base_url_newton+"getPic?tenantId="+tenantId+"&token=t&picName="+picName+"&ip="+ip;
}
var url_page_configuration_main=base_url_newton+"Configuration/devType";
var url_page_customer_main=base_url_newton+"Customer/main";
var url_page_customer_regist=base_url_newton+"Customer/regist/regist.html";
/**
 * 获取所有属性值： tenantId, token ,keyhash 
 */
var url_getDevAttrAll=base_url_newton+"getDevAttrAll?version="+apiVersion+"&jsonp_callback=ret&";
var url_setDevAttrs =base_url_newton+"setDevAttrs?version="+apiVersion+"&jsonp_callback=ret&";
var url_getLogs=base_url_newton+"getLog?version="+apiVersion+"&jsonp_callback=ret&";
var url_getUsers=base_url_newton+"getUserList?version="+apiVersion+"&jsonp_callback=ret2&";
var url_deleteDevice=base_url_newton+"deleteDevice?version="+apiVersion+"&jsonp_callback=ret2&";
var url_resetDevice=base_url_newton+"resetDevice?version="+apiVersion+"&jsonp_callback=ret&";
//二级用户
var url_shareUser=base_url_newton+"authorizeUser?version="+apiVersion+"&jsonp_callback=ret&";
var url_modifyAuthUser=base_url_newton+"modifyAuthUser?version="+apiVersion+"&jsonp_callback=ret&";
var url_unAuthUser=base_url_newton+"unAuthorizeUser?version="+apiVersion+"&jsonp_callback=ret&";
var url_deleteUser=base_url_newton+"deleteUser?version="+apiVersion+"&jsonp_callback=ret&";
//临时用户
var url_shareTempUser=base_url_newton+"shareTempUser?version="+apiVersion+"&jsonp_callback=ret&";
var url_modifyTempUser=base_url_newton+"modifyTempUser?version="+apiVersion+"&jsonp_callback=ret&";
var url_deleteTempUser=base_url_newton+"deleteTempUser?version="+apiVersion+"&jsonp_callback=ret&";

var url_uploadPic=base_url_cbs+"uploadHeadPic?version="+apiVersion+"&jsonp_callback=ret&";
var url_getSpecificPic=base_url_cbs+"getSpecificPic?version="+apiVersion+"&jsonp_callback=ret2&";


var url_statisAPPAction=base_url_cbs+"statisAPPAction?version="+apiVersion+"&jsonp_callback=ret&";//点击进入主控、log、用户管理、设备设置页面时需要统计。

var url_login=base_url_newton+"login?jsonp_callback=ret&";
var url_registVerify=base_url_newton+"getRegistVerify?jsonp_callback=ret&";
var url_regist=base_url_newton+"regist?jsonp_callback=ret&";
var url_getUserInfo=base_url_newton+"getUserInfo?jsonp_callback=rets&";
var url_setUserInfo=base_url_newton+"setUserInfo?jsonp_callback=ret&";
var url_modifyPwd=base_url_newton+"modifyPassword?jsonp_callback=ret&";
var url_modifyAccount=base_url_newton+"modifyAccount?jsonp_callback=ret&";
var url_modifyAccountVerify=base_url_newton+"getModifyAccountVerify?jsonp_callback=ret&";
var url_getDevList=base_url_newton+"getDevList?jsonp_callback=ret&";
var url_getDevListWithAttrs=base_url_newton+"getDevListWithAttrs?jsonp_callback=ret&";
var url_scanDevQr=base_url_newton+"registDev?jsonp_callback=ret&";

var url_feedback=base_url_newton+"feedback?jsonp_callback=ret&"; 
var url_getTriggerList=base_url_newton+"getTriggerList?jsonp_callback=ret2&";//sys和cus通用
var url_getTriggerSwitch=base_url_newton+"getTriggerSwitch?jsonp_callback=ret&"; 
var url_setTriggerSwitch=base_url_newton+"setTriggerSwitch?jsonp_callback=ret&"; 
var url_getChargeInfo=base_url_newton+"getChargeInfos?jsonp_callback=ret2&";//sys和cus通用
var url_getPayInfoList=base_url_newton+"getPayInfoList?jsonp_callback=ret&"; 
var url_deleteCharge=base_url_newton+"deleteCharge?jsonp_callback=ret&"; 


//newton配置网页的URL
var con_sys_url_getUsers=base_url_newton+"sysGetUserList?jsonp_callback=ret2&";//以前sys和cus通用
var con_sys_url_getChargeInfo=base_url_newton+"sysGetChargeInfos?jsonp_callback=ret2&";//以前sys和cus通用
var  con_sys_url_getTriggerList=base_url_newton+"sysGetTriggerList?jsonp_callback=ret2&";//以前sys和cus通用
 


var con_url_sysLogin=base_url_newton+"systemLogin?jsonp_callback=ret&"; //登录不跨域
var con_url_sysGetUserInfo=base_url_newton+"sysGetUserInfo?jsonp_callback=ret&";
var con_url_sysGetDevList=base_url_newton+"sysGetDevList?jsonp_callback=ret&";
var con_url_sysGetDevTypeList =base_url_newton+"sysGetDevTypeList?jsonp_callback=ret&";
var con_url_sysAddDevType=base_url_newton+"sysAddDevType?jsonp_callback=ret&"; 
var con_url_sysDeleteDevType =base_url_newton+"sysDeleteDevType?jsonp_callback=ret&";
var con_url_sysModifyDevType =base_url_newton+"sysModifyDevType?jsonp_callback=ret&";
var con_url_sysGetAttrsOfDevType =base_url_newton+"sysGetAttrsOfDevType?jsonp_callback=ret&";
var con_url_sysAddAttrOfDevType =base_url_newton+"sysAddAttrOfDevType?jsonp_callback=ret&";
var con_url_sysModifyAttrOfDevType =base_url_newton+"sysModifyAttrOfDevType?jsonp_callback=ret&";
var con_url_sysDeleteAttrOfDevType=base_url_newton+"sysDeleteAttrOfDevType?jsonp_callback=ret&";
var con_url_sysDevReset=base_url_newton+"sysDevReset?jsonp_callback=ret&";
var con_url_sysGetPayInfoList=base_url_newton+"sysGetPayInfoList?jsonp_callback=ret&";
var con_url_sysAddCharge =base_url_newton+"sysAddCharge?jsonp_callback=ret&";
var con_url_sysModifyCharge =base_url_newton+"sysModifyCharge?jsonp_callback=ret&";
var con_url_sysDeleteCharge=base_url_newton+"sysDeleteCharge?jsonp_callback=ret&";

var con_sys_url_getSMSChargeInfo=base_url_newton+"sysGetSMSChargeInfos?jsonp_callback=rets&";
var con_url_sysAddSMSCharge =base_url_newton+"sysAddSMSCharge?jsonp_callback=ret&";
var con_url_sysModifySMSCharge =base_url_newton+"sysModifySMSCharge?jsonp_callback=ret&";
var con_url_sysDeleteSMSCharge=base_url_newton+"sysDeleteSMSCharge?jsonp_callback=ret&";

var con_url_sysAddTrigger=base_url_newton+"sysAddTrigger?jsonp_callback=ret&";
var con_url_sysModifyTrigger=base_url_newton+"sysModifyTrigger?jsonp_callback=ret&";
var con_url_sysDeleteTrigger=base_url_newton+"sysDeleteTrigger?jsonp_callback=ret&";
var con_url_sysGetFeedback=base_url_newton+"sysGetFeedback?jsonp_callback=ret&";
var con_url_sysDealFeedback =base_url_newton+"sysDealFeedback?jsonp_callback=ret&";
var con_url_sysGetAttrOperation=base_url_newton+"sysGetAttrOperations?jsonp_callback=ret&";
var con_url_sysAddAttrOperation=base_url_newton+"sysAddAttrOperation?jsonp_callback=ret&";
var con_url_sysModifyAttrOperation=base_url_newton+"sysModifyAttrOperation?jsonp_callback=ret&";
var con_url_sysDeleteAttrOperation=base_url_newton+"sysDeleteAttrOperation?jsonp_callback=ret&";
var con_url_sysGetDevopAccountList =base_url_newton+"sysGetDevopAccountList?jsonp_callback=ret&";
var con_url_sysAddDevopAccount =base_url_newton+"sysAddDevopAccount?jsonp_callback=ret&";
var con_url_sysDeleteDevopAccount =base_url_newton+"sysDeleteDevopAccount?jsonp_callback=ret&";
//var con_url_ =base_url_newton+"?jsonp_callback=ret&";
//var con_url_ =base_url_newton+"?jsonp_callback=ret&";


var msg_url_getMsgTypeList= base_url_newton+"Message/getMsgTypeList?jsonp_callback=ret2&";
var msg_url_getMsgList= base_url_newton+"Message/getMsgList?jsonp_callback=rets&";
var msg_url_addMsgType= base_url_newton+"Message/addMsgType?jsonp_callback=ret&";
var msg_url_modifyMsgType= base_url_newton+"Message/modifyMsgType?jsonp_callback=ret&";
var msg_url_deleteMsgType= base_url_newton+"Message/deleteMsgType?jsonp_callback=ret&";
var msg_url_addMsg= base_url_newton+"Message/addMsg?jsonp_callback=ret&";
var msg_url_modifyMsg= base_url_newton+"Message/modifyMsg?jsonp_callback=ret&";
var msg_url_deleteMsg= base_url_newton+"Message/deleteMsg?jsonp_callback=ret&";

var statis_url_devTotal=base_url_newton+"Statis/devTotal?jsonp_callback=ret&";
var statis_url_userTotal=base_url_newton+"Statis/userTotal?jsonp_callback=ret&";
//var statis_url_=base_url_newton+"Statis/?jsonp_callback=ret&";


var con_url_devopLogin=base_url_newton+"devopLogin?jsonp_callback=ret&"; //登录不跨域
var con_url_devopGetUserInfo=base_url_newton+"devopGetUserInfo?jsonp_callback=ret&";
var con_url_devopGetUserList=base_url_newton+"devopGetUserList?jsonp_callback=ret2&";
var con_url_devopGetDevList=base_url_newton+"devopGetDevList?jsonp_callback=ret&";
var con_url_devopGetDevTypeList =base_url_newton+"devopGetDevTypeList?jsonp_callback=ret&";
var con_url_devopAddDevType=base_url_newton+"devopAddDevType?jsonp_callback=ret&"; 
var con_url_devopDeleteDevType =base_url_newton+"devopDeleteDevType?jsonp_callback=ret&";
var con_url_devopModifyDevType =base_url_newton+"devopModifyDevType?jsonp_callback=ret&";
var con_url_devopGetAttrsOfDevType =base_url_newton+"devopGetAttrsOfDevType?jsonp_callback=ret&";
var con_url_devopAddAttrOfDevType =base_url_newton+"devopAddAttrOfDevType?jsonp_callback=ret&";
var con_url_devopModifyAttrOfDevType =base_url_newton+"devopModifyAttrOfDevType?jsonp_callback=ret&";
var con_url_devopDeleteAttrOfDevType=base_url_newton+"devopDeleteAttrOfDevType?jsonp_callback=ret&";
var con_url_devopDevReset=base_url_newton+"devopDevReset?jsonp_callback=ret&";
var con_url_devopGetPayInfoList=base_url_newton+"devopGetPayInfoList?jsonp_callback=ret&";
var con_url_devopGetChargeInfos=base_url_newton+"devopGetChargeInfos?jsonp_callback=ret2&";
var con_url_devopAddCharge =base_url_newton+"devopAddCharge?jsonp_callback=ret&";
var con_url_devopModifyCharge =base_url_newton+"devopModifyCharge?jsonp_callback=ret&";
var con_url_devopDeleteCharge=base_url_newton+"devopDeleteCharge?jsonp_callback=ret&";

var con_url_devopGetSMSChargeInfos=base_url_newton+"devopGetSMSChargeInfos?jsonp_callback=rets&";
var con_url_devopAddSMSCharge =base_url_newton+"devopAddSMSCharge?jsonp_callback=ret&";
var con_url_devopModifySMSCharge =base_url_newton+"devopModifySMSCharge?jsonp_callback=ret&";
var con_url_devopDeleteSMSCharge=base_url_newton+"devopDeleteSMSCharge?jsonp_callback=ret&";

var con_url_devopAddTrigger=base_url_newton+"devopAddTrigger?jsonp_callback=ret&";
var con_url_devopModifyTrigger=base_url_newton+"devopModifyTrigger?jsonp_callback=ret&";
var con_url_devopDeleteTrigger=base_url_newton+"devopDeleteTrigger?jsonp_callback=ret&";
var con_url_devopGetFeedback=base_url_newton+"devopGetFeedback?jsonp_callback=ret&";
var con_url_devopDealFeedback =base_url_newton+"devopDealFeedback?jsonp_callback=ret&";
var con_url_devopGetAttrOperation=base_url_newton+"devopGetAttrOperations?jsonp_callback=ret&";
var con_url_devopAddAttrOperation=base_url_newton+"devopAddAttrOperation?jsonp_callback=ret&";
var con_url_devopModifyAttrOperation=base_url_newton+"devopModifyAttrOperation?jsonp_callback=ret&";
var con_url_devopDeleteAttrOperation=base_url_newton+"devopDeleteAttrOperation?jsonp_callback=ret&";
var con_url_devopGetDevAttrList=base_url_newton+"devopGetDevAttrList?jsonp_callback=ret&";


/**
 * invoke  connect API to get data.
 * 调用ajax的API获取数据。
 * @param apiUrl url
 * @param callBackRetName 指定jsonp返回方法
 * @param params 参数
 * @param callbak(bool,result) 访问api的回调方法
 */
function connectAPI(apiUrl,params,jsonpRetName,callback){
	apiUrl=apiUrl+"";
	jsonpRetName=jsonpRetName+"";
	setTimeout(function() {//异步
		try{
			$.ajax({
				type : "post",
				dataType : "json",
				 url : apiUrl,
				data : params,
				async: true,
				beforeSend: function(request) {
					//jsonp 方式此方法不被触发.原因可能是dataType如果指定为jsonp的话,就已经不是ajax事件了
					request.setRequestHeader("web-ajax", "*");
					console.log("head");
				},
				crossDomain: true,
//				jsonp: "callback",//传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(一般默认为:callback)
//				jsonpCallback:jsonpRetName,//"ret",
				success : function(msg) {
					console.log(msg);
					
//					loadEnding();
//					var isExpire=loadErrForToken(msg, -1, function() {
//					}); 
//					if(isExpire==true){
//						return;
//					}
					callback(true,msg);
//					 if(self.frameElement){//判断处于frame中 
//						 if(msg.retcode==200&&(apiUrl.indexOf("get")>=0||apiUrl.indexOf("Get")>=0)){
//							 window.parent.setIframeHeight();
//						 }
//					}
				},
				error : function(err) {
					console.log(err);
					console.log(apiUrl);
					console.log(params);
//					alert(" 网络异常，访问失败！"+err);
					callback(false,err); 
				}
			});
		}catch (err) {
			// TODO: handle exception
			console.error(err);
			callback(false,err); 
		}
	}, 0);
}

function connectJustAjax(url,params,callback,methodType){
	url=url+"";
	setTimeout(function() {//异步
		try{
			$.ajax({
				type : "post",
				 url : url,
				data : params,
				async: true,
				beforeSend: function(request) {
					//jsonp 方式此方法不被触发.原因可能是dataType如果指定为jsonp的话,就已经不是ajax事件了
					request.setRequestHeader("web-ajax", "*");
//					request.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
					console.log("head");
				},
				crossDomain: true,
				success : function(msg) {
//					console.log(msg);
					callback(true,msg);
					 if(self.frameElement){//判断处于frame中 
					}
				},
				error : function(err) {
					console.log(err);
					console.log(url);
					alert(" 网络异常，访问失败！"+err);
					callback(false,err); 
				}
			});
		}catch (err) {
			// TODO: handle exception
			console.error(err);
			callback(false,err); 
		}
	}, 0);
}
/**0.use a href
 * 1.用ajax无法请求文件会被当成字符串内容返回；
 * 2.form的方式或window.location.href下载可能在谷歌浏览器会被直接打开而不是提示下载
 * @param elt
 * @param hintText
 */
function connectUrlDownFile(fileUrl){
	var link = document.createElement('a');
	link.setAttribute("download", "");
	link.href =  fileUrl;
	link.click();
}

/**submit form. ajax提交表单（提交后不跳转刷新页面。且可提交文件）。*/
function connectSubmitForm(url,formId,callback){
	 var form = new FormData(document.getElementById(formId));
		url=url+"";
		setTimeout(function() {//异步
			try{
				$.ajax({
					type : "post",
					 url : url,
					data : form,
					async: true,
					processData : false,//为了能提交表单中文件 
					contentType : false,//为了能提交文件 
					beforeSend: function(request) {
						request.setRequestHeader("web-ajax", "*");
					},
					crossDomain: true,
					success : function(msg) {
						try{
							if(((msg instanceof Object)==false)&&(msg+"").trim().indexOf("{")==0){
								msg=JSON.parse(msg);
							}
						}catch (e) { }
						loadEnding();
						var isExpire=loadErrForToken(msg, -1, function() {
						}); 
						if(isExpire==true){
							return;
						}
						callback(true,msg);
						if(msg.retcode==200&&(url.indexOf("get")>=0||url.indexOf("Get")>=0)){
							 window.parent.setIframeHeight();
						 }
					},
					error : function(err) {
						console.log(err);
						console.log(url);
						alert(" 网络异常，访问失败！"+err);
						callback(false,err); 
					}
				});
			}catch (err) {
				// TODO: handle exception
				console.error(err);
				callback(false,err); 
			}
		}, 0);
	}

/*  
 * API
 */

//var api_getDevAttrAll= location.origin+"/newton/getDevAttrAll?version="+apiVersion;
// var api_setDevAttrs =location.origin+"/newton/setDevAttrs?version="+apiVersion;
// var api_getLogs=location.origin+"/newton/getLog?version="+apiVersion;
// var api_getUsers=location.origin+"/newton/getUserList?version="+apiVersion;
// var api_deleteDevice=location.origin+"/newton/deleteDevice?version="+apiVersion;
// var api_resetDevice=location.origin+"/newton/resetDevice?version="+apiVersion;
// var api_getDevListWithAttrs=location.origin+"/newton/getDevListWithAttrs?version="+apiVersion;
// var api_getAttrOperation=location.origin+"/newton/getAttrOperation?version="+apiVersion;
// var api_getSceneInfo=location.origin+"/newton/getSceneInfo?version="+apiVersion;
// var api_addScene =location.origin+"/newton/addScene?version="+apiVersion;
// var api_modifyScene =location.origin+"/newton/modifyScene?version="+apiVersion;
// var api_deleteScene =location.origin+"/newton/deleteScene?version="+apiVersion;
// var api_addDevLinkage =location.origin+"/newton/addDevLinkage?version="+apiVersion;
// var api_modifyDevLinkage =location.origin+"/newton/modifyDevLinkage?version="+apiVersion;
// var api_deleteDevLinkage =location.origin+"/newton/deleteDevLinkage?version="+apiVersion;
// var api_addTimedTask =location.origin+"/newton/addTimedTask?version="+apiVersion;
// var api_modifyTimedTask =location.origin+"/newton/modifyTimedTask?version="+apiVersion;
// var api_deleteTimedTask =location.origin+"/newton/deleteTimedTask?version="+apiVersion;
//// var api_ =location.origin+"/newton/";
// //二级用户
// var api_shareUser=location.origin+"/newton/authorizeUser?version="+apiVersion;
// var api_modifyAuthUser=location.origin+"/newton/modifyAuthUser?version="+apiVersion;
// var api_deleteDevUser=location.origin+"/newton/unAuthorizeUser?version="+apiVersion;
// //临时用户
// var api_shareTempUser=location.origin+"/newton/shareTempUser?version="+apiVersion;
// var api_modifyTempUser=location.origin+"/newton/modifyTempUser?version="+apiVersion;
// var api_deleteTempUser=location.origin+"/newton/deleteTempUser?version="+apiVersion;
// 
// var api_uploadPic=location.origin+"/cbs/uploadHeadPic?version="+apiVersion;

 

//为输入框设置hint，实现点击后隐藏，移除焦点后内容为空时显示
function toSetHintForInputText(elt,hintText){
	var text=elt.val(),hasVal=elt.attr("hasval");
	if(text==""||text==hintText){
		elt.css("color","#b0b0b0");
		elt.val(hintText);
		elt.attr("hasval","0");
	} else{
		elt.css("color","#666");
		elt.attr("hasval","1");
	}
	elt.on("blur",function(){
		var elt=$(this),
		text=elt.val(),hasVal=elt.attr("hasval");
		if(text==""||text==hintText){
			elt.css("color","#b0b0b0");
			elt.val(hintText);
			elt.attr("hasval","0");
		} else{
			elt.attr("hasval","1");
		}
	}).on("focus",function(){
		var elt=$(this),text=elt.val(),hasVal=elt.attr("hasval");
		if(hasVal=="0"){
			elt.css("color","#666");
			elt.val("");
		} 
	}) .on("input propertychange",function(){ 
		var elt=$(this);
		if(elt.val()==""){  
			elt.attr("hasval","0");
		} else{
			elt.attr("hasval","1");
		} 
//		console.info("文字变化 "+elt.val());
	});
}


function htmlEncodeOneChar(charater) {
    switch(charater) {
       case '&':
           return "&amp;";
       case '<':
           return "&lt;";
       case '>':
           return "&gt;";
       case '"':
           return "&quot;"; 
       case ' ':
           return "&nbsp;";
       default:
           return charater + "";
    }
}
 
/** 对传入的字符串str进行Html encode转换,防止注入脚本代码 */
function htmlEncodeString( str) {
    if (str ==null || ""==str.trim()||!("string"==typeof(str)) )   return str;
    var result=""; 
     var len = str.length;
    for ( var  i = 0 ; i < len; i++) {
      result=result+htmlEncodeOneChar(str.charAt(i)) ;
    }
    return result;
}

//缓存key说明：
/** @param keyhash */
 function  getCache_Key_keyhash_attrs(keyhash){
	 keyhash+="";
	return keyhash+"_attrs";//缓存:keyhash的属性值
}
 /** @param userId @param keyhash */
function getCache_Key_userId_keyhash_logs(userId,keyhash){
	userId+="";
	keyhash+="";
	return userId+keyhash+"_logs";//缓存:log页面
}
/** @param userId @param keyhash */
function getCache_Key_userId_keyhash_userList(userId,keyhash){
	userId+="";
	keyhash+="";
//	ConsoleInfo(userId+"===="+keyhash);
	return userId+keyhash+"_ulist";//缓存:用户列表页面
}
/**  @param sceneId {String} */
function getCache_Key_sceneId_infos(sceneId){
	sceneId+="";
	return sceneId+"_scene_list";//缓存:场景页面（场景里面的定时和联动信息）
}
/**  @param     */
function getCache_Key_scene_devs( ){
	return  "scene_devs";//缓存:场景页面（设备列表）
}
//缓存数据：localStorage
function updateCacheLocalStorage(key,value){
	var hasUpdate=false;
	var infos=localStorage.getItem(key);
	if(infos!=value){
		try {
			localStorage.setItem(key, value);
			hasUpdate=true;
		} catch (e) {
			// TODO: handle exception
		}
	}
	return hasUpdate;
}
//获取缓存数据：localStorage
function getCacheLocalStorage(key){
	var infos=localStorage.getItem(key);
//	ConsoleInfo(key+" key==="+infos);
	return infos;
}
function removeCacheLocalStorage(key){
	 localStorage.setItem(key,null);
}



/* xml相关方法 start*/
/***convert xml object to string (将xml对象转换成字符串)*/
function xmlToString(xmlObject) {
    // for IE
    if (window.ActiveXObject) {      
      return xmlObject.xml;
    }
    // for other browsers
    //所有浏览器统一用这种方式处理(因为高版本的浏览器都支持)
    else {       
      return (new XMLSerializer()).serializeToString(xmlObject);
    }
  }
function createXmlObFromStr(str) {
	if (document.all) {
		var xmlDom = new ActiveXObject("Microsoft.XMLDOM")
		xmlDom.loadXML(str)
		return xmlDom
	} else
		return new DOMParser().parseFromString(str, "text/xml")
}

function getXmlEleAttrValue(ele, key) {
	if (ele) {
		var attrs = ele.attributes;
		if (attrs.length > 0) {
			var attr = attrs.getNamedItem(key);
			if (attr) {
				return attr.value;
			}
		}
	}
	return "";
}

function getXmlEleValue(ele, key) {
	if (ele) {
		var eleChild = ele.getElementsByTagName(key);
		if (eleChild.length > 0) {
			var eleChild2 = eleChild[0];
			if (eleChild2 && eleChild2.firstChild) {
				return eleChild2.firstChild.nodeValue;
			}
		}
	}
	return "";
}

function setXmlEleValue(ele, key, value) {
	if (ele) {
		var eleChild = ele.getElementsByTagName(key);
		if (eleChild.length > 0) {
			var eleChild2 = eleChild[0];
			if (eleChild2 && eleChild2.firstChild) {
				eleChild2.firstChild.nodeValue = value;
				return true;
			}
			if (eleChild2 && eleChild2) {
				eleChild2.innerHTML = value;
				return true;
			}
		}
	}
	return false;
}


function byteToString(arr) {
//	console.log( (typeof arr === 'object')+" 是不是数组="+Array.isArray( arr)+" ["+(arr instanceof Array)+"]");
	if(isDefinedNotNull(arr)==false||Array.isArray(arr)==false) {
		console.log(" 不是数组 " );
		return arr;
	}
	
	var str = '',
		_arr = arr;
	for(var i = 0; i < _arr.length; i++) {
		var one = _arr[i].toString(2),
			v = one.match(/^1+?(?=0)/);
		if(v && one.length == 8) {
			var bytesLength = v[0].length;
			var store = _arr[i].toString(2).slice(7 - bytesLength);
			for(var st = 1; st < bytesLength; st++) {
				store += _arr[st + i].toString(2).slice(2);
			}
			str += String.fromCharCode(parseInt(store, 2));
			i += bytesLength - 1;
		} else {
			str += String.fromCharCode(_arr[i]);
		}
	}
	return str;
}

/* xml相关方法 end*/


