<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<c:import url="../layout/app.jsp">
		<c:param name="content">
			<c:choose>
				<c:when test="${task!=null}">
					<h2>id:${task.id}のタスク詳細ページ</h2>

					<table>
						<tbody>
					      <tr>
					        <td>タスクの内容：<c:out value="${task.content}" /></td>
					      </tr>
					      <tr>
					        <td>作成日時：<fmt:formatDate value="${task.created_at}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					      </tr>
					      <tr>
					        <td>更新日時：<fmt:formatDate value="${task.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					      </tr>
					   </tbody>
					</table>
					<p><a href="${pageContext.request.contextPath}/index">一覧に戻る</a></p>
		        	<p><a href="${pageContext.request.contextPath}/edit?id=${task.id}">このタスクを編集する</a></p>
				</c:when>
				<c:otherwise>
					<h2>お探しのデータは見つかりませんでした。</h2>
				</c:otherwise>
			</c:choose>
		</c:param>
	</c:import>
</body>
</html>