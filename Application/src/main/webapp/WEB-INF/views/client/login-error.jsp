<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
  <title>Library</title>   
 <style>
        body {
            font-family: Arial, sans-serif;
            padding: 40px;
        }
        .bloc {
            background: #ffe0e0;
            border: 1px solid #cc0000;
            padding: 24px;
            max-width: 500px;
            margin: auto;
            text-align: center;
        }
        a {
            text-decoration: none;
            background: #cc0000;
            color: white;
            padding: 8px 20px;
            border-radius: 4px;
        }
    </style>
</head>
<body>

<div class="bloc">
    <h2>Connexion requise</h2>
    <p>Vous devez être connecté pour ajouter des produits à votre panier.</p>
    <a href="/connexion">Se connecter</a>
</div>

</body>
</html>