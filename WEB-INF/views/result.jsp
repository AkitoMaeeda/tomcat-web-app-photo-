<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Context-Type" contant="text/html"; charset=UTF-8>
            <link href="../css/style.css" rel="stylesheet">
            <title>PD実践作品</title>
        </head>

        <body>
        <h1>PD作品ページdayo</h1>

        <% String message = (String)request.getAttribute("message");%>
        <h1><%= message %></h1>

        <% String article = (String)request.getAttribute("photo");%>
        <img src = <%= article %> >
        <h1><%= article %></h1>

        <p><a href = "photo">戻る</a></p>



        </div>
        </body>
    </html>
