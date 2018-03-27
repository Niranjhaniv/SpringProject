<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>JSON Validator</title>
</head>
<body>
	<form:form method="post" modelAttribute="json">

		<table>
			
			
			<tr>
				<td><form:label path="json">json :</form:label></td>
				<td><form:textarea path="json" rows="20" cols="100" /></td>
			</tr>

			<tr>
				<td colspan="2"><input type="submit" value="Submit" /></td>
			</tr>
		</table>

	</form:form>
</body>
</html>