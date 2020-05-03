<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 이쪽에 메뉴바 포함 할꺼임 -->
    <jsp:include page="../common/menubar.jsp"/>
    
    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>회원가입</h2>
            <br>

            <form id="enrollForm" action="insert.me" method="post" onsubmit="">
                <div class="form-group">
                    <label for="userId">* ID :</label>
                    <input type="text" class="form-control" id="userId" name="userId" placeholder="Please Enter ID" required>
                    <div id="checkResult" style="display:none; font-size:0.8em"></div><br>
                    
                    <label for="userPwd">* Password :</label>
                    <input type="password" class="form-control" id="userPwd" name="userPwd" placeholder="Please Enter Password" required><br>
                    
                    <label for="checkPwd">* Password Check :</label>
                    <input type="password" class="form-control" id="checkPwd" placeholder="Please Enter Password" required><br>
                    
                    <label for="userName">* Name :</label>
                    <input type="text" class="form-control" id="userName" name="userName" placeholder="Please Enter Name" required><br>
                    
                    <label for="email"> &nbsp; Email :</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="Please Enter Email"><br>
                    
                    <label for="age"> &nbsp; Age :</label>
                    <input type="number" class="form-control" id="age" name="age" placeholder="Please Enter Age"><br>
                    
                    <label for="phone"> &nbsp; Phone :</label>
                    <input type="tel" class="form-control" id="phone" name="phone" placeholder="Please Enter Phone (-없이)"><br>
                    
                    <label for="address"> &nbsp; Address :</label>
                    <input type="text" class="form-control" id="address" name="address" placeholder="Please Enter Address"><br>
                    
                    <label for=""> &nbsp; Gender : </label> &nbsp;&nbsp;
                    <input type="radio" name="gender" id="Male" value="M">
                    <label for="Male">남자</label> &nbsp;&nbsp;
                    <input type="radio" name="gender" id="Female" value="F">
                    <label for="Female">여자</label><br>
                    
                </div>
                <br>
                <div class="btns" align="center">
                    <button type="submit" class="btn btn-primary" id="enrollBtn" disabled>회원가입</button>
                    <button type="reset" class="btn btn-danger"> 초기화</button>
                </div>
            </form>
        </div>
        <br><br>
    </div>
    
    <script>
    	$(function() {
    		// 아이디 입력하는 input요소에 keyup 이벤트
    		var $idInput = $("#enrollForm input[name=userId]");
    		
    		$idInput.keyup(function() {
    			//console.log("입력됨");
    			
    			// 아이디는 최소 5글자 이상 (5글자 이상인 경우만 중복체크)
    			if($idInput.val().length >= 5) {
    				$.ajax({
    					url:"idCheck.me",
    					data:{userId:$idInput.val()},
    					type:"post",
    					success:function(result){
    						if(result > 0) { // --> 중복된 아이디 존재 --> 버튼 비활성화, 메세지("중복된 아이디 있음 사용 불가")
    							idCheckValidate(1);
    						} else { // --> 중복된 아이디 존재 X --> 버튼 활성화, 메세지("중복된 아이디 없음 사용 가능")
    							idCheckValidate(2);
    						}
    					},error:function(){
    						console.log("아이디 중복체크 ajax 통신 실패");
    					}
    				});
    			} else { // 5글자 미만 --> 중복체크 안 함 --> 버튼 비활성화, 메세지 안 보임
    				idCheckValidate(0);
    			}
    		});
    	});
    	
    	function idCheckValidate(num) {
    		if(num == 0) { // --> 중복체크 안 함 (유효한 아이디 X) --> 버튼 비활성화, 메세지 안 보여지게
    			$("#enrollBtn").attr("disabled", true);
    			$("#checkResult").hide();
    		} else if(num == 1) { // --> 중복된 아이디가 존재할 경우 --> 버튼 비활성화, 메세지 보여짐 ("사용 불가")
    			$("#enrollBtn").attr("disabled", true);
    			$("#checkResult").css("color", "red").text("중복된 아이디가 존재합니다. 사용이 불가합니다.");
    			$("#checkResult").show();
    		} else if(num == 2) { // --> 중복된 아이디가 존재X --> 버튼 활성화, 메세지 보여짐("사용 가능")
    			$("#enrollBtn").removeAttr("disabled");
    			$("#checkResult").css("color", "green").text("멋진 아이디네요!");
    			$("#checkResult").show();
    		}
    	}
    </script>

    <!-- 이쪽에 푸터바 포함할꺼임 -->
    <jsp:include page="../common/footer.jsp"/>
</body>
</html>