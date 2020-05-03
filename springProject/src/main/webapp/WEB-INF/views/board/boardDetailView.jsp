<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	table *{margin:5px;}
    table{width:100%;}
</style>
</head>
<body>
    <jsp:include page="../common/menubar.jsp"/>

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 상세보기</h2>
            <br>
            
            <!-- 로그인후 상태일 경우만 보여지는 글쓰기 버튼-->
            <a class="btn btn-secondary" style="float:right" href="list.bo">목록으로</a>
            <br><br>
            <table id="contentArea" align="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">${ b.boardTitle }</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${ b.boardWriter }</td>
                    <th>작성일</th>
                    <td>${ b.createDate }</td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td colspan="3">
                    	<c:choose>
                    		<c:when test="${ !empty b.originName }">
                    	    	<a href="${ pageContext.servletContext.contextPath }/resources/upload_files/${b.changeName}" download="${ b.originName }">${ b.originName }</a>
                    	    </c:when>
                    	    <c:otherwise>
                    	    	첨부파일이 없습니다
                    	    </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4"><p style="height:150px">${ b.boardContent }</p></td>
                </tr>
            </table>
            <br>
			
            <!-- 수정하기, 삭제하기 버튼은 이글이 본인글일 경우만 보여져야됨 -->
            <c:if test="${ loginUser.userId eq b.boardWriter }">
	            <div align="center">
	                <button class="btn btn-primary" onclick="postFormSubmit(2);">수정하기</button>
	                <button class="btn btn-danger" onclick="postFormSubmit(1);">삭제하기</button>
	            </div>
	            
	            <!-- 삭제 시 다른 사용자가 글 번호를 이용하여 악의적으로 글을 삭제할 수 있으므로 post로 보내도록 -->
	            <form id="postForm" action="" method="post">
	            	<input type="hidden" name="bno" value="${ b.boardNo }">
	            	<input type="hidden" name="fileName" value="${ b.changeName }">
	            </form>
	            
	            <script>
	            	function postFormSubmit(num) {
	            		if(num == 1) { // 삭제하기 클릭 시
	            			$("#postForm").attr("action", "delete.bo");
	            		} else { // 수정하기 클릭 시
	            			$("#postForm").attr("action", "updateForm.bo");
	            		}
	            		// 공통된 코드니까
	            		$("#postForm").submit();
	            	}
	            </script>
            </c:if>
			
			<br><br>
			
            <!-- 댓글 기능은 나중에 ajax 배우고 접목시킬예정! 우선은 화면구현만 해놓음 -->
            <table id="replyArea" class="table" align="center">
                <thead>
                	<c:choose>
                		<c:when test="${ !empty loginUser }"> <!-- 로그인이 되어 있을 때 -->
		                    <tr>
		                        <th colspan="2">
		                            <textarea class="form-control" name="" id="replyContent" cols="55" rows="2" style="resize:none; width:100%"></textarea>
		                        </th>
		                        <th style="vertical-align: middle"><button class="btn btn-secondary" id="addReply">등록하기</button></th>
		                    </tr>
	                    </c:when>
	                    <c:otherwise> <!-- 로그인이 되어있지 않을 때 -->
		                    <tr>
		                        <th colspan="2">
		                            <textarea class="form-control" cols="55" rows="2" style="resize:none; width:100%" readonly>로그인한 사용자만 사용 가능한 서비스입니다. 로그인 후 이용해주세요.</textarea>
		                        </th>
		                        <th style="vertical-align: middle"><button class="btn btn-secondary" disabled>등록하기</button></th>
	                    	</tr>
                    	</c:otherwise>
	                </c:choose>
                    
                    <tr>
                       <td colspan="3">댓글 (<span id="rcount">0</span>) </td> 
                    </tr>
                </thead>
                <tbody>
                    	
                </tbody>
            </table>
        </div>
        <br><br>
    </div>
    
    <script>
    	$(function() {
    		selectReplyList();
    		
    		$("#addReply").click(function(){
    			if($("#replyContent").val().trim().length != 0) { // 텍스트가 작성되어있을 경우
    				$.ajax({
    					url:"rinsert.bo",
    					data:{replyContent:$("#replyContent").val(),
    						  refBoardNo:${b.boardNo}, // refBoardNo
    						  replyWriter:'${loginUser.userId}'}, // replyWrier:admin
    					type:"post",
    					success:function(result) {
    						if(result > 0) { // 댓글 작성 성공
    							$("#replyContent").val("");
    							selectReplyList();
    						}
    					},error:function() {
    						console.log("댓글 작성용 ajax 통신 실패");
    					}
    				});
    			} else {
    				alertify.alert("댓글을 작성해주세요");
    			}
    		});
    	});
    	
    	// 처음 창 열었을 때, 댓글 작성했을 때 조회해야 하기 때문에 따로 function을 빼냄
    	// 해당 게시글에 딸려 있는 댓글 리스트 조회용 ajax 통신
    	function selectReplyList() {
    		$.ajax({
    			url:"rlist.bo",
    			data:{bno:${b.boardNo}},
    			type:"get",
    			success:function(list){
    				$("#rcount").text(list.length); // 댓글 개수 대체
    				
    				var value="";
    				
    				$.each(list, function(i, obj) {
    					value += "<tr>" +
    								"<th>" + obj.replyWriter + "</th>" +
    								"<td>" + obj.replyContent + "</td>" +
    								"<td>" + obj.createDate + "</td>" +
				                 "</tr>";	
    				});
    				
    				$("#replyArea tbody").html(value);
    			},error:function(){
    				console.log("댓글 리스트 조회용 ajax 통신 실패");
    			}
    		});
    	}
    </script>

    <jsp:include page="../common/footer.jsp"/>
</body>
</html>