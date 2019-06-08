# Blog-Web-Service

## 프로젝트 목적

웹 클라이언트 단과 서버 단 개발 역량을 종합적으로 확인 및 테스트 하기 위한 개인 프로젝트로 네이버 블로그 형식의 회원 가입 / 로그인 / 회원 관리 / 게시판, 블로그 및 갤러리 기능을 구현해보자 하였다. 



## 개발 환경 및 사용 언어

- Eclipse 
- Apache Tomcat 8.5
- HTML [boostrap]
- CSS
- Javascript / jQuery
- Spring Framework
- MyBatis

## 주요 기능

1. 회원 가입

   <img src="./images/join.JPG" width="65%" ></img>
 
2. 로그인

   <img src="./images/login.JPG" width="65%" ></img>

3. 회원 정보 수정

   <img src="./images/modi_member.JPG" width="65%" ></img>

4. 회원 관리 (**관리자 권한**, 회원 정보 수정 및 다중 삭제 기능 포함)

   <img src="./images/manage_members.JPG" width="65%" ></img>

5. 블로그 관리[**수정 중..**.]

   <img src="./images/manage_blog.JPG" width="65%" ></img>

6. 갤러리 게시판 

   <img src="./images/gallery.JPG" width="65%" ></img>

7. 종합 게시판

   <img src="./images/board.JPG" width="65%" ></img>

8. 댓글

   <img src="./images/reply.JPG" width="65%" ></img>

9. 나만의 블로그[수정 중...]

   <img src="./images/blog.JPG" width="65%" ></img>

## 핵심 코드

1. 예외 처리

   사용자 정의의 ResultMsg  객체 및 자료구조 Map 을 통한 예외 처리 

   ```java
   // 아이디 유효성 검사
   @GetMapping("/id-check/{userId}")
   @ResponseBody
   public ResponseEntity<ResultMsg> checkId(@PathVariable String userId)
       throws Exception {
       if (service.getMember(userId)==null) {
           return ResultMsg.response("ok", "사용가능한 ID 입니다.");
       } else {
           return ResultMsg.response("duplicate", "이미 사용중인 ID 입니다.");
       }
   }
   ```

   ```java
   // 특정 게시글 삭제 요청 처리 메소드
   @DeleteMapping("/delete/{boardId}")
   @ResponseBody
   public ResponseEntity<Map<String, String>> delete(@PathVariable int boardId, @RequestParam(value = "password") String password) throws Exception {
       Map<String, String> map = new HashMap<>();
       if (service.delete(boardId, password)) {
           map.put("result", "success");
       } else {
           map.put("result", "비밀번호가 일치하지 않습니다.");
       }
   
       HttpHeaders headers = new HttpHeaders();
       headers.add("Content-Type", "application/json; charset=utf-8");
       return new ResponseEntity<Map<String, String>>(map, headers, HttpStatus.OK);
   }
   ```

   

2. 비밀번호 암호화

   ```java
   public static String getEncrypt(String source, String salt) {
   		String result = "";
   		String temp = source + salt;
   		byte[] bytes = temp.getBytes();
   		try {
   			MessageDigest md = MessageDigest.getInstance("SHA-256");
   			md.update(bytes);
   			byte[] byteData = md.digest();
   			// 바이트를 문자열로 변환
   			StringBuilder sb = new StringBuilder();
   			for (int i = 0; i < byteData.length; i++) {
   				sb.append(String.format("%02x", byteData[i]));
   			}
   			result = sb.toString();
   		} catch (NoSuchAlgorithmException e) {
   			e.printStackTrace();
   		}
   
   		return result;
   	}
   ```

   

3. 타일을 이용한 UI 탬플릿 모듈화

   ```html
   <body>
   <div class="container">
   	<tiles:insertAttribute name="header"/>
   	<tiles:insertAttribute name="menu"/>
   	<tiles:insertAttribute name="body"/>
   	<tiles:insertAttribute name="footer"/>
   </div>
   </body>
   ```

4. 댓글 구현

   ```javascript
   class ReplyList {
   	constructor(opt) {
   		this.opt = opt;
   		this.editForm = `
   		<div class="card card-body mp-3"> ... </div>`;
   		this.deleteForm = `
   		<div class="card card-body mp-3"> ... </div>`;
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
   		.then(result=>this.makeList(result.data)).catch(console.log)		
   	} ...
   ```

   

5. 페이징 처리

   ```java
   public PageInfo(int currentPage, int total, int perPage, int perBlock) {
   		page = currentPage;
   		this.totalCount = total;
   		this.perPage = perPage;
   		this.perBlock = perBlock;
   		
   		start= (page-1)*perPage+1;
   		end = start + perPage-1;
   		totalPage = (int)Math.ceil((float)total/perPage);	
   
   		currentBlock= (int)Math.ceil((float)currentPage/perBlock);
   		totalBlock = (int)Math.ceil((float)totalPage/perBlock); 
   		
   		startPage = (currentBlock-1)* perBlock + 1;	// 블록 시작 페이지
   		endPage = startPage + perBlock - 1; 		//블록 마지막 페이지
   		
   		if(endPage > totalPage) endPage = totalPage;
   
   		prevBlockPage = page - perBlock;
   		nextBlockPage = page + perBlock;	
   	}
   ```

   

## 주요 기술

1. Ajax 비동기식 통신

   ```javascript
   // ID 중복 체크
   self.click(function(){
       var userId = idInput.val();
       if(!userId) return alert('사용자 ID를 입력하세요');
       $.get('id-check/' + userId, function(data){
           if(data.result == 'ok') {
               msgSpan.html(data.message).removeClass('error');
               submitBtn.prop('disabled', false);	// submit 버튼 활성화	
               self.prop('disabled', true);	// ID 체크 버튼 비활성화
           } else {
               msgSpan.html(data.message).addClass('error');
               submitBtn.prop('disabled', true); // submit 버튼 비활성화	
           }
       });	
   });
   ```

2. Restful API 방식의 데이터 처리

   ```java
   // 특정 게시글 삭제 요청 처리 메소드
   @DeleteMapping("/delete/{boardId}")
   @ResponseBody
   public ResponseEntity<Map<String, String>> delete(@PathVariable int boardId,
                                                     @RequestParam(value = "password") String password) throws Exception {
       Map<String, String> map = new HashMap<>();
       if (service.delete(boardId, password)) {
           map.put("result", "success");
       } else {
           map.put("result", "비밀번호가 일치하지 않습니다.");
       }
       HttpHeaders headers = new HttpHeaders();
       headers.add("Content-Type", "application/json; charset=utf-8");
       return new ResponseEntity<Map<String, String>>(map, headers, HttpStatus.OK);
   }
   ```

   

   행위가 아닌 자원(명사) 위주의 URL 처리

   <img src="./images/restful_url.JPG" width="65%" ></img>

   

3. 스프링 프레임워크 인터셉터

   로그인 처리 간 효율적 세션관리를 위한 인터셉터 활용

   ```java
   @Component
   public class LoginInterceptor extends BaseInterceptor {
   	@Override
   	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 		Object handler) throws Exception {
   		HttpSession session = request.getSession();
   		if (session.getAttribute("USER") == null) {
   			System.out.println("로그인이 필요한 서비스입니다.");
   			redirect(request, response, "/login", "로그인이 필요한 서비스입니다.");
   			return false;
   		}
   		return super.preHandle(request, response, handler);
   	}
   }
   ```

   

4. lombok & builder library

   - 갤러리 게시판의 이미지들을 업로드할 때 

   ```java
   private void saveImages(Gallery gallery) throws Exception {
       for(MultipartFile file : gallery.getFiles()) {	
           if(file.isEmpty()) continue;
           Image image = Image.builder()
               .galleryId(gallery.getGalleryId())
               .originalName(file.getOriginalFilename())
               .fileSize((int)file.getSize())
               .mimeType(file.getContentType())
               .build();
   
           imageService.create(image);
           imageService.saveImage(image, file);
       }
   }
   ```

5. 제너릭과 상속 및 Static 활용

   

6. JSTL 과 EL 

7. 회원 이미지 처리를 위한 아바타 라이브러리 활용

## 주요 패키지 및 클래스 설명

## 클래스 다이어 그램

## ERD

