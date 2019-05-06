

class TopLevelReply {
	constructor (opt){
		this.opt = opt;
		this.templ = `
<div class="card card-body mp-3">
	<form id="reply-form">
		<div class="mb-2">
			<strong>[댓글 달기] </strong>
			작성자 : ${this.opt.writer}
		</div>		 
		<textarea name="content" required style="width:100%">테스트</textarea>
		<div class="text-right">
			비밀번호 : <input type="password" name="password" required value="1234">
			<button type="submit">등록</button>
			<button type="reset">취소</button>
		</div>
	</form>
</div>`;
		$(this.opt.el).append(this.templ)
			.on('submit', 'form', this.submit.bind(this));
		this.opt.form =$(this.opt.el).find('form')[0];
		console.log(this.opt.form)
	}

	static init(opt) {
		return new TopLevelReply(opt);
	}
	
	submit(e) {
		e.preventDefault();
		var form = $(this.opt.form);
		var data = {
				tableName : this.opt.tableName,
				groupId : this.opt.groupId,
				writer : this.opt.writer,
				password : form.find('[name=password]').val(),
				content : form.find('[name=content]').val()
		}
		axios.post(this.opt.action, data)
			.then(result=>{
				console.log(result)
				console.log('등록 성공');
			})
			.catch(alert);		
	}
}


class ReplyList {
	constructor(opt) {
		this.opt = opt;
		this.editForm = `
		<div class="card card-body mp-3">
			<div class="mb-2">
				<strong>[댓글 달기] </strong>
				작성자 : ${this.opt.writer}
			</div>		 
			<textarea name="content" required class="w-100 my-1">테스트</textarea>
			<div class="text-right">
				비밀번호 : <input type="password" name="password" required value="1234">
				<button type="button" class="reply-ok">등록</button>
				<button type="button" class="reply-cancel">취소</button>
			</div>
		</div>`;
		
		this.deleteForm = `
		<div class="card card-body mp-3">
			<div class="text-right">
				비밀번호 : <input type="password" name="password" required value="1234">
				<button type="button" class="reply-ok">삭제</button>
				<button type="button" class="reply-cancel">취소</button>
			</div>
		</div>`;
		
		
		$(this.opt.el)
		.on('click', '.show-reply-add', e=>this.showAdd(e))
		.on('click', '.show-reply-edit', e=>this.showEdit(e))
		.on('click', '.show-reply-delete', e=>this.showDelete(e))
		.on('click', '.reply-ok', e=>this.ok(e))
		.on('click', '.reply-cancel', e=>this.cancel(e))
		
		axios.get(opt.action, {
			params : {
				tableName : this.opt.tableName,
				groupId : this.opt.groupId,	
			}			
		})
		.then(result=>this.makeList(result.data))
		.catch(console.log)		
	}
	
	static init(opt) {
		return new ReplyList(opt);
	}
	
	makeReply(reply) {
		var addBtn = '<button class="show-reply-add"><i class="fa fa-reply"></i></button>';
		var editBtn = '<button class="show-reply-edit"><i class="fa fa-edit"></i></button>';
		var deleteBtn = '<button class="show-reply-delete"><i class="fa fa-trash"></i></button>' 
		return `
<div class="media reply my-3" style="margin-left:${50*(reply.level-1)}px">
	<img src="/tour/member/avata/${reply.writer}" class="rounded-circle avata-md">
	<div class="media-body ml-3">
		<div class="button-group float-right">
			작성일 : <span class="update-date">${reply.updateDate.datetime()}</span>
			<span class="button-group">
				${reply.writer != this.opt.writer ? addBtn : ''}
				${reply.writer == this.opt.writer ? editBtn : ''}
				${reply.writer == this.opt.writer ? deleteBtn : ''}
			</span>			
		</div>
		<h5>${reply.writer}</h5>
		<div class="reply-content">${reply.content}</div>
		<form>
			<input type="hidden" name="replyId" value="${reply.replyId}"/>
			<input type="hidden" name="groupId" value="${reply.groupId}"/>
			<input type="hidden" name="writer" value="${reply.writer}"/>
			<input type="hidden" name="parent" value="${reply.replyId}"/>
			<input type="hidden" name="level" value="${reply.level}"/>
			<div class="reply-work"></div>
		</form>		
	</div>
</div>`;
	}
	
	makeList(data) {
		var list = $(this.opt.el);
		data.map(reply=>{
				reply.updateDate = new Date(reply.updateDate);
				return reply;
			})
			.map(reply=>this.makeReply(reply))
			.forEach(reply=>list.append(reply))
	}
	
	getForm() {
		return ;
	}
	
	
	disableBtnGroup(target, disabled=true) {
		$(target).closest('.reply').find('.button-group button').prop('disabled', disabled);
	}
	
	showAdd(e) {
		this.disableBtnGroup(e.target);		
		var work = $(e.target).closest('.media-body').find('.reply-work');
		work.data('method', 'post');
		work.append(this.editForm);
	}
	
	showEdit(e) {
		this.disableBtnGroup(e.target);
		var work = $(e.target).closest('.media-body').find('.reply-work');
		var content = $(e.target).closest('.media-body').find('.reply-content').text();
		var form = $(this.editForm);
		form.find('textarea').val(content);
		
		work.data('method', 'edit');
		work.append(form);
	}
	
	showDelete(e) {
		this.disableBtnGroup(e.target);
		var work = $(e.target).closest('.media-body').find('button').find('.reply-work');
		work.data('method', 'remove');
		work.append(this.deleteForm);		
	}
	
	ok(e) {
		var work = $(e.target).closest('.media-body').find('button').find('.reply-work');
		var method = work.data('method');
		this[method](work);
	}	
	cancel(e) {
		this.disableBtnGroup(e.target, false);
		$(e.target).closest('.media-body')
					.find('.reply-work').empty();
	}
	
	add(work) {
		alert('등록할까요?');
	}
	
	edit(work) {
		var form = work.parent();		
		var formData = new FormData(form[0]);
		
		var reply={	tableName: this.opt.tableName};
		formData.forEach((value, key)=>reply[key] = value);
		
		axios.put(this.opt.action + "/" + reply.replyId, reply)
			.then(result=>{
				var reply = result.data;
				reply.updateDate = new Date(reply.updateDate);
				work.closest('.media-body').find('.reply-content')
					.text(reply.content);
				work.closest('.media-body').find('.update-date')
					.text(reply.updateDate.datetime());
				work.empty();

			})
			.catch(console.log);
	}
	
	remove(work) {
		alert('삭제할까요?');
	}
	
	
}
