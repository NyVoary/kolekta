<<<<<<< Updated upstream
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.demo.entity.TestEntity" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head><title>Home</title></head>
<body>
    <h1>Bonjour!</h1>
    <%-- <ul>
        <c:forEach var="item" items="${liste}">
            <li>${item.name}</li>
        </c:forEach>
    </ul> --%>
=======
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Accueil</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Manrope:wght@400;700&family=Nunito:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Nunito', 'Manrope', Arial, sans-serif;
            background: linear-gradient(120deg, #e0e7ff 0%, #f8fafc 100%);
            min-height: 100vh;
            margin: 0;
        }
        .container {
            max-width: 500px;
            margin: 80px auto;
            background: #fff;
            border-radius: 18px;
            box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.15);
            padding: 48px 36px 36px 36px;
            text-align: center;
            position: relative;
        }
        h1 {
            color: #2d3e50;
            margin-bottom: 32px;
            font-size: 2.2em;
            font-weight: 700;
            letter-spacing: 1px;
        }
        a.btn {
            display: inline-block;
            background: linear-gradient(90deg, #4e73df 0%, #6a82fb 100%);
            color: #fff;
            padding: 14px 38px;
            border-radius: 8px;
            text-decoration: none;
            font-size: 1.15em;
            font-weight: 600;
            box-shadow: 0 2px 8px #4e73df22;
            transition: background 0.2s, transform 0.2s;
        }
        a.btn:hover {
            background: linear-gradient(90deg, #2e59d9 0%, #4e73df 100%);
            transform: translateY(-2px) scale(1.03);
        }
        .footer {
            margin-top: 40px;
            color: #b0b3b8;
            font-size: 0.98em;
        }
        @media (max-width: 600px) {
            .container { padding: 24px 8px; }
            h1 { font-size: 1.3em; }
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Bienvenue sur l'application de gestion</h1>
    <a class="btn" href="/historique-client?idClient=1">Voir l'historique d'achat d'un client</a>
</div>
>>>>>>> Stashed changes
</body>
</html>
