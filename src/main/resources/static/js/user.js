let index = {
	
	init: function() {
		
		$("#btn-save").bind("click", () => {
			this.save();
		});
		
		$("#btn-login").bind("click", () => {
			this.login();
		});
		
		
	},
	
	save: function() {
		let data = {
			username: $("#username").val(), 
			password: $("#password").val(), 
			email: $("#email").val()
			
		}
		// console.log(data);
		
		// ajax 호출 
		$.ajax({
			// 서버측에 회원가입 요청 	
			type: "POST", 
			url: "/auth/joinProc",
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json" // 응답이 왔을 때 기본 데이터 타입(Buffered 문자열) => js object 자동 변환
		
		}).done(function(data, textStatus, xhr){
			// 통신 성공시
			console.log("xhr :" + xhr); 
			console.log(xhr); 
			console.log("textStatus :" + textStatus);
			console.log("data  : " + data);
			alert("회원가입이 완료 되었습니다.");
			location.href = "/";  
		}).fail(function(error){
			console.log(error)
			alert("회원가입에 실패 하였습니다.");
		});
	}, 
	
	login: function() {
		
		let data = {
			username: $("#username").val(), 
			password: $("#password").val()
		}
		
		// ajax 호출 
		$.ajax({
			// 회원 로그인 요청 
			type: "POST",
			url: "/api/user/login",
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json" 
		}).done(function(data, textStatus, xhr) {
			alert("로그인이 완료 되었습니다.")
			location.href = "/"
			console.log(data);
		}).fail(function(error) {
			alert("로그인에 실패 했습니다.")
			console.log(error);
		});
		
	}
	
}

index.init(); 






