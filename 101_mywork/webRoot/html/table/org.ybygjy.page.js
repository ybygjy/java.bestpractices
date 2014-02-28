$=function(id){return typeof(id)=='string'? document.getElementById(id):id;}
/**
 * 封装页面级别操作
 * <p>1、界面元素事件绑定
 */
var page = function(){
	var cmpID=0000001;
	return {
		/**
		 * 绑定事件
		 */
		bindAction:function(elemId,callback) {
			if ($(elemId)) {
				$(elemId).onclick=callback;
			}
		},
		sayHello:function(){
			alert('Hello' + cmpID);
		}
	}
}();