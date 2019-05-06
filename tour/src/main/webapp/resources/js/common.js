function parseParam(query) {
	var params = {};
	query.substring(1)	// ?이후 문자열 리턴
		.split('&')		// &로 분리된 문자열 리턴
		.forEach(e=>{	// 배열 순회
			param = e.split("=");			// 속성명, 속성값 분리
			params[param[0]] = param[1];	// 객체 속성으로 추가
		});
	return params;
}

location.params=parseParam(location.search);



Array.prototype.shuffle = function() {
	var j, x, i;
	for (i = this.length; i > 0 ; --i) {
		j = parseInt(Math.random() * i);
		x = this[i - 1];
		this[i - 1] = this[j];
		this[j] = x;
	}
	return this;	
}

Object.defineProperty(
		Array.prototype, "shuffle", { enumerable: false }
);



//this : form
$.fn.deletePanel = function(opt) {
	
	var templ = `
<form class="my-3">
	비밀번호 : 	
	<input type="password" name="password" required>
	<button type="submit" class="btn btn-primary btn-sm">
		<i class="fas fa-times"></i> 삭제
	</button>
	<button type="button" class="btn btn-primary btn-sm cancel">
		<i class="fas fa-undo"></i> 취소
	</button>
</form>`;
	var self = this;
	self.hide();
	
	self.append(templ);	
	var password = self.find(':password');
	var triger = $(opt.triger); 
	
	var url;
	var items;
	triger.click(function(){
		items =[];
		var userId =$(this).data('user-id'); 
		if(userId) // 특정인 제거
			url = opt.url.replace('{user-id}', userId );
		else {
			if($(this).data('mode') == 'multiple') {	// 선택 삭제
				$(opt.multiple).each(function(){
					items.push($(this).val()); // 선택된 사용자 ID 배열에 추가
				});
				url = opt.url.replace('delete/{user-id}', 'multi_delete');
			} else {	// view 페이지에서 삭제 
				url = opt.url;
			}
		}
		self.show();
	});
	
	self.on('click', '.cancel', function(){
		password.val('');
		self.hide();
	})

	self.on('submit', 'form', function(e) {
		e.preventDefault();
		if(!confirm("삭제할까요?")) return;

		var params = items.map(data=>`users=${data}`).join('&');
		if(params)
			params += '&password=' + password.val();
		else 
			params = 'password=' + password.val();

		
//		var params = {
//				users : items,
//				password : password.val()
//		};
		
		axios.delete(url + '?' + params)
		// axios.delete(url, {params: params})
		// axios.delete(url + '?password=' + password.val())
			.then(function(obj) {
				if(obj.data.result == 'success') {
					location = opt.moveUrl;
				} else {
					alert(obj.data.message);
				}
			})
			.catch(console.log);	
	});
		
}


class DeletePanel {
	constructor(opt) {
		var templ =  `
			<form class="my-3">
			비밀번호 : 	
			<input type="password" name="password" required>
			<button type="submit" class="btn btn-primary btn-sm">
				<i class="fas fa-times"></i> 삭제
			</button>
			<button type="button" class="btn btn-primary btn-sm cancel">
				<i class="fas fa-undo"></i> 취소
			</button>
		</form>`;
		this.opt = opt;
		this.panel = $(opt.el);
		this.panel.append(templ);	
		this.password = this.panel.find(':password');
		console.log(this.panel)
		$(opt.triger).click(()=>this.panel.show());
		
		this.panel.find('.cancel').click(()=>{
			this.password.val('');
			this.panel.hide();
		});
		
		this.panel.find('form').submit(this.remove.bind(this));
	}
	
	static init(opt) {
		new DeletePanel(opt);
	}
	
	cancel() {
		this.password.val('');
		this.panel.hide();
	}
	
	remove(e) {
		e.preventDefault();
		if(!confirm("삭제할까요?")) return;		
		return alert(this.opt.url + '?password=' + this.password.val());
		
		axios.delete(this.opt.url + '?password=' + this.password.val())
			.then(function(data) {
				if(data.result == 'success') {
					location = this.opt.moveUrl;
				} else {
					alert(data.result);
				}
			})
			.catch(console.log);
	}
	
}



//날짜 출력
Date.prototype.date = function() {
	var year = this.getFullYear();
	var month = this.getMonth()+1;
	var date = this.getDate();

	month = (month < 10) ? '0' + month : month;
	date = (date < 10) ? '0' + date : date;

	return `${year}-${month}-${date}`;
}

Date.prototype.datetime = function() {
	var hour = this.getHours();
	var minute = this.getMinutes();
	var second = this.getSeconds();

	hour = (hour < 10) ? '0' + hour : hour;
	minute = (minute < 10) ? '0' + minute : minute;
	second = (second < 10) ? '0' + second : second;

	return `${this.date()} ${hour}:${minute}:${second}`;
}

function formToObject(form) {
	
}