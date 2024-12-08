<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${errors != null }">
		<div id="flush_error">
			入力内容にエラーがあります。<br>
			<c:forEach var="error" items="${errors}">
				<c:out value="${error}"/><br>
			</c:forEach>
		</div>
	</c:if>
	<label for="content_msg">タスクの内容</label><br>
	<input type="text" name="content" id="content_task" value="${task.content}"><br><br>
	<input type="hidden" name="_token" value="${_token}">
	<button type="submit">作成</button>
</body>
</html>