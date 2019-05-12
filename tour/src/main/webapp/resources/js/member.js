
//사용자 ID 중복 체크 플러그인
$.fn.checkUserId = function() {
	var self = this;
	var idInput = this.closest('.form-group').find('input');	// 사용자 ID 입력 input
	var msgSpan = this.next();	// 처리 결과 출력 메시지 span
	var submitBtn = $(':submit');
	
	// ID 중복 체크
	self.click(function(){
		var userId = idInput.val();
		if(!userId) return alert('사용자 ID를 입력하세요');
		
		$.get('id-check/' + userId, function(data){
			if(data.result == 'ok') {
				msgSpan.html(data.message).removeClass('error');
				//prop vs attr 
				//checkbox's chekced 와 a 태그의 url 참고
				submitBtn.prop('disabled', false);	// submit 버튼 활성화	
				self.prop('disabled', true);	// ID 체크 버튼 비활성화
			} else {
				msgSpan.html(data.message).addClass('error');
				submitBtn.prop('disabled', true); // submit 버튼 비활성화	
			}
		});	
	});
	
	// 사용자 ID가 변경된 경우 
	// submit 버튼 비활성화 
	idInput.change(function(){
		msgSpan.html('ID 중복 체크를 해야합니다.').removeClass('.error');
		submitBtn.prop('disabled', true);	// submit 버튼 비활성화	
		self.prop('disabled', false);	// ID 체크 버튼 활성화
	});
}


// this : form
$.fn.checkPassword = function(pass1='#password', pass2='#password2') {
	this.submit(function(e) {
		e.preventDefault();
		var password1 = $(pass1).val();
		var password2 = $(pass2).val();
		if(password1 == password2) {
			this.submit();
		} else {
			alert('비밀번호가 일치하지 않습니다.');
		}
	});
}

