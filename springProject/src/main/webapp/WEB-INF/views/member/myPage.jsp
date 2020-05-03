<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
            <h2>마이페이지</h2>
            <br>

            <form action="update.me" method="post" onsubmit="">
                <div class="form-group">
                    <label for="userId">* ID :</label>
                    <input type="text" class="form-control" id="userId" name="userId" value="${ loginUser.userId }" readonly><br>
                    
                    <label for="userName">* Name :</label>
                    <input type="text" class="form-control" id="userName" name="userName" value="${ loginUser.userName }" readonly><br>
                    
                    <label for="email"> &nbsp; Email :</label>
                    <input type="email" class="form-control" id="email" name="email" value="${ loginUser.email }"><br>
                    
                    <label for="age"> &nbsp; Age :</label>
                    <input type="number" class="form-control" id="age" name="age" value="${ loginUser.age }"><br>
                    
                    <label for="phone"> &nbsp; Phone :</label>
                    <input type="tel" class="form-control" id="phone" name="phone" value="${ loginUser.phone }"><br>
                    
                    <label for="address"> &nbsp; Address :</label>
                    <input type="text" class="form-control" id="address" name="address" value="${ loginUser.address }"><br>
                    
                    <label for=""> &nbsp; Gender : </label> &nbsp;&nbsp;
                    <input type="radio" name="gender" id="Male" value="M">
                    <label for="Male">남자</label> &nbsp;&nbsp;
                    <input type="radio" name="gender" id="Female" value="F" checked>
                    <label for="Female">여자</label><br>
                    
                    <script>
                    	$(function() {
                    		// if(M == "M")으로 하면 변수 M을 찾기 때문에 "" 안에 감싸줘야 한다
                    		if("${loginUser.gender}" == "M") {
                    			$("#Male").attr("checked", true);
                    		} else if("${loginUser.gender}" == "F"){ // 성별 체크가 안 되어있을 수 있어서 else if 
                    			$("#Female").attr("checked", true);
                    		}
                    	});
                    </script>
                    
                </div>
                <br>
                <div class="btns" align="center">
                    <button type="submit" class="btn btn-primary">수정하기</button>
                    <a href="delete.me" class="btn btn-danger">탈퇴하기</a>
                </div>
            </form>

        </div>
        <br><br>
    </div>

    <!-- footer -->
    <jsp:include page="../common/footer.jsp"/>
    
</body>
</html>