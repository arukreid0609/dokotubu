<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ page import="model.*, java.util.List" %>
		<% User loginUsers=(User) session.getAttribute("loginUser"); List<Mutter> mutterList = (List<Mutter>)
				request.getAttribute("mutterList");
				%>
				<jsp:useBean id="loginUser" scope="session" class="model.User" />
				<!DOCTYPE html>
				<html>

				<head>
					<meta charset="UTF-8">
					<title>どこつぶ</title>
				</head>

				<body>
					<script type="text/javascript">
						class Mutter {
							constructor(name, text) {
								this.name = name;
								this.text = text;
							}
						}

						let mlist = new Array();
		<%for (Mutter mutter : mutterList) {%>
							mlist.push(new Mutter("<%=mutter.getUserName()%>", "<%=mutter.getText()%>"));
		<%}%>
					</script>
					<h1>どこつぶメイン</h1>
					<p>
						<%=loginUsers.getName()%>さん、ログイン中
							<jsp:getProperty name="loginUser" property="name" />さん、ログイン中 <a href="Logout">ログアウト</a>
					</p>
					<p>
						<a href="Main">更新</a>
					</p>
					<form action="Main" method="post">
						<input type="text" name="text"> <input type="submit" value="つぶやく">
					</form>
					<%-- <% for (Mutter mutter : mutterList) { %>
						<p>
							<%=mutter.getUserName()%>:<%=mutter.getText()%>
						</p>
						<% } %>
							--%>
							<div id="list"></div>
							<script>
		const list = document.getElementById("list");
								/* 		mlist.forEach(element => {
											list.innerHTML += "<p>"+element.name+":"+element.text+"</p>";
										});
								 */
								let count = 0;
								list.innerHTML += "<p>" + mlist[count].name + ":" + mlist[count].text + "</p>";
								list.addEventListener("click", () => {
									count++;
									count = mlist.length > count ? count : 0;
									list.innerHTML = "<p>" + mlist[count].name + ":" + mlist[count].text + "</p>";
								});
							</script>
				</body>

				</html>