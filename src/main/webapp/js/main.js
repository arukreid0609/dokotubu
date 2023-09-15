const list = document.getElementById("list");

let p = null;
<%for(Mutter mutter : mutterLIst){%>
	p = document.createElement("p");
	p.innerText = "<%=mutter.getUserName()%><%=mutter.getText()%>";
	list.append(p);
<% } %>