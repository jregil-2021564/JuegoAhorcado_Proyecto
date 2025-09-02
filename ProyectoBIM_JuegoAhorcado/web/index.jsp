<%-- 
    Document   : index
    Created on : 1/09/2025, 18:31:20
    Author     : Usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Juego del Ahorcado Premium</title>
    <link rel="stylesheet" href="css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600;700&family=Poppins:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <!-- Video de fondo -->
    <div class="video-background">
        <video autoplay muted loop id="bg-video">
            <source src="" type="video/mp4">
        </video>
        <div class="video-overlay"></div>
    </div>

    <div class="container">
        <!-- Menú de inicio mejorado -->
        <div id="menu-inicio" class="menu-inicio">
            <div class="logo-container">
                <div class="logo">
                    <h1>AHORCADO</h1>
                    <div class="logo-subtitle">Juego de Ingenio</div>
                </div>
                <div class="animated-icon">
                    <i class="fas fa-gamepad"></i>
                </div>
            </div>
            
            <div class="menu-buttons">
                <button id="btn-comenzar" class="btn btn-primary">
                    <i class="fas fa-play"></i> Comenzar
                </button>
                <button id="btn-instructions" class="btn btn-secondary">
                    <i class="fas fa-book"></i> Instrucciones
                </button>
                <button id="btn-salir" class="btn btn-secondary">
                    <i class="fas fa-times"></i> Salir
                </button>
            </div>
            
            <div class="stats-preview">
                <div class="stat">
                    <div class="stat-number">5</div>
                    <div class="stat-label">Palabras</div>
                </div>
                <div class="stat">
                    <div class="stat-number">3</div>
                    <div class="stat-label">Pistas</div>
                </div>
                <div class="stat">
                    <div class="stat-number">6</div>
                    <div class="stat-label">Intentos</div>
                </div>
            </div>
        </div>

</body>
</html>