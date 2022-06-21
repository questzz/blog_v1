"use strict"    // 전체 스크립트를 strict 모드로 설정함.

let index = {
	
	init: function() {
		$("#btn-save").bind("click", () => {
			this.save();
		});
	},
	
	save: function() {
		let data = {
			username: $("#username").val(), 
			password: $("#password").val(), 
			email: $("#email").val()
			
		}
		console.log(data);
	}
	
}

index.init(); 