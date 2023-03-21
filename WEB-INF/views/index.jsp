<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Context-Type" contant="text/html"; charset=UTF-8>
            <link href="../css/style.css" rel="stylesheet">
            <title>PD実践作品</title>
            <script src="<%= request.getContextPath() %>/js/script.js"></script>
        </head>

    <body>

        <form name="uploadForm" method = "post" action ="result" enctype="multipart/form-data">
            <div>
                <label for="photo"></label>
                <input id="uploadInput" type="file" name="myFiles" >
                <button id ="fileselect">アップロードするファイルを選択してください。</button>
                
                <h2>選択されたファイル:</h2><span id="filenum">0</span>
                <h2>合計サイズ:</h2><span id ="filesize">0</span>

                <h1>編か</h1>        
            </div>
            <div><input type="submit" value="Sendfile" id = "insert"></div>
            <span id = "outprint">0</span>
            </form>

            <div id = "preview"></div>


    </body>
    </html>

