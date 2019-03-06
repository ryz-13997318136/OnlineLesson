$(document).ready(function(){
	$.getTopicType = function(code){
		var x;
		switch (code)
		  {
		  case '1':
		    x="简答题";
		    break;
		  case '2':
		    x="选择题";
		    break;
		  case '3':
		    x="填空题";
		    break;
		  case '4':
		    x="判别题";
		    break;
		default:
		    x="未知题型";
			break;
		  }
		return x;
	},
	$.getLocalTime = function (nS) {
		if(nS){
			return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/,' '); 
		}else{
			return "";
		}
	},  
	
	//弹出框
	$.showMsg = function(title,msg){
		$('#myAlert strong').html(title);
		$('#myAlert span').html(": "+msg);
		$('#myAlert').show();
		setTimeout(function(){
		$("#myAlert").fadeOut("slow");
		},500);
	},
	
	$.ajaxPost = function(url,data,fun){
		 $.ajax({ 
		    	url: url,
		    	type:"POST", 
		    	contentType : 'application/json', 
		    	data : data, 
		    	dataType : 'text',
		    	success:function(data,status){
		    		if(status == 'success'){
		    			var data = JSON.parse(data);
		    			if(data.res=='SUCCESS'){
		    				fun(data)
		    			}else{
		    				$.showMsg('Fail',data.msg);
		    			}
		    		}
		    	},
		    	error:function(data){
		    		$.showMsg('Error',data);
		    	}});
	},
	
	$.ajaxGet = function(url,data,fun){
		 $.ajax({ 
		    	url: url,
		    	type:"GET", 
		    	contentType : 'application/json', 
		    	data : data, 
		    	dataType : 'json',
		    	success:function(data,status){
		    		if(status == 'success'){
		    			if(data.res=='SUCCESS'){
		    				fun(data)
		    			}else{
		    				$.showMsg('Fail',data.msg);
		    			}
		    		}
		    	},
		    	error:function(data){
		    		$.showMsg('Error',data);
		    	}
		    	});
	}
	
	
});