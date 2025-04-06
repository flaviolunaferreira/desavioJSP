<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Gestão de Projetos</title>

  <!-- Preconect para melhor performance -->
  <link rel="preconnect" href="https://cdn.jsdelivr.net">
  <link rel="preconnect" href="https://fonts.googleapis.com">

  <!-- Bootstrap CSS com fallback local -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">

  <!-- Bootstrap Icons -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">

  <!-- Font Consolas com fallback para monospace -->
  <link href="https://fonts.googleapis.com/css2?family=Consolas&display=swap" rel="stylesheet">
  <style>
    body { font-family: 'Consolas', monospace; }
  </style>

  <!-- CSS Customizado -->
  <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">

  <!-- Favicon -->
  <link rel="icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon">

  <!-- Pré-carregar scripts importantes -->
  <link rel="preload" href="https://code.jquery.com/jquery-3.6.0.min.js" as="script">
  <link rel="preload" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" as="script">
</head>
<body class="bg-dark text-light">

<!-- Scripts no final do body para melhor performance -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"
        integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
        crossorigin="anonymous"></script>

<script src="https://code.jquery.com/ui/1.13.1/jquery-ui.min.js"
        integrity="sha256-eTyxS0rkjpLEo16uXTS0uVCS4815lc40K2iVpWDvdSY="
        crossorigin="anonymous"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
        crossorigin="anonymous"></script>

<!-- Chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<!-- Seus scripts customizados -->
<script src="${pageContext.request.contextPath}/resources/js/utils/api.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/utils/helpers.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/utils/ui.js"></script>
<!-- ... outros scripts ... -->
<script src="${pageContext.request.contextPath}/resources/js/app.js"></script>