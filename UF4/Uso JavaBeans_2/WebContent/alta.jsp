<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>VayaTela</title>
</head><jsp:useBean id="depart" scope="request" class="dao.departamento.Departamento"></jsp:useBean><jsp:setProperty
	property="*" name="depart" />
<%
	if (request.getParameter("deptno") != null) {
%><jsp:forward
	page="/Controller?action=insertar"></jsp:forward>
<%
	}
%><body>
	<center>
		<h2>Entrada</h2>
		<form method="post" action="">
			<p>
				Num departamento: <input name="deptno" required type="number"
					min="1" max="99" />
			</p>
			<p>
				Nombre: <input name="dnombre" required type="text" size="15"
					maxlength="15" />
			</p>
			<p>
				localidad: <input name="loc" required type="text" size="15"
					maxlength="15" />
			</p>
			<input type="submit" name="insertar" value="insertar" />
			<input type="submit" name="consultar" value="consultar" />
		</form>
		<a href="index.html"> Inicio</a>
	</center>
</body>
</html>