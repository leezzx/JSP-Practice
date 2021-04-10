<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<%-- <% // MVC model 1 방식, 하나의 서블릿에 MVC 다 있음
	int num = 0;
	String num_ = request.getParameter("n");
	if(num_ != null && !num_.equals("")) {
		num = Integer.parseInt(num_);
	}
	
	String result;
	
	if(num % 2 != 0) {
		result = "홀수";
	} else {
		result = "짝수";
	}
%> --%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<%
pageContext.setAttribute("result", "hello"); // pageContext : page내에서 저장소로 쓸 수 있는 객체
%>

<body>
<%--	<%=result %>입니다.  --%>
	<%=request.getAttribute("result") %>입니다.
	${requestScope.result}<br > <%-- EL활용한 표기, requestScope라는 한정사를 통해 request 객체에서 찾음 (같은 변수가 있을 경우) --%>
	${names[0]}<br > <%-- EL활용한 표기 --%>
	${notice.title}<br > <%-- EL활용한 표기 --%>
	${result}<br > <%-- EL활용한 표기 --%>
	${param.n gt 3}<br > <%-- EL활용한 파라미터 정보 표기, 연산자 활용 (gt = >) --%>
	${empty param.n?'값이 비어있습니다.':param.n}<br > <%-- empty : 빈문자열 혹은 Null일때 참, 삼항 연산자? 사용 --%>
	${header.accept}<br > <%-- EL활용한 헤더 정보 표기 --%>
</body>
</html>