<%-- 
    Document   : MenuInicio
    Created on : 3 sept 2025, 11:17:24
    Author     : informatica
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión - Juego del Ahorcado</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
    <div class="video-background">
        <video autoplay muted loop id="login-video">
            <source src="Image/videofondo.mp4" type="video/mp4">
            Tu navegador no soporta videos HTML5.
        </video>
        <div class="video-overlay"></div>
    </div>

    <div class="container">
        <div class="login-container">
            <div class="login-header">
                <div class="logo">
                    <i class="fas fa-puzzle-piece"></i>
                    <h1>JUEGO AHORCADO</h1>
                </div>
                <p>Inicia sesión para comenzar esta aventura</p>
            </div>

            <form id="login-form" class="login-form">
                <div class="form-group">
                    <label for="username"><i class="fas fa-user"></i> Usuario</label>
                    <input type="text" id="username" name="username" placeholder="Ingresa tu usuario">
                    <div class="error-message" id="username-error"></div>
                </div>

                <div class="form-group">
                    <label for="password"><i class="fas fa-lock"></i> Contraseña</label>
                    <input type="password" id="password" name="password" placeholder="Ingresa tu contraseña">
                    <div class="error-message" id="password-error"></div>
                </div>

                <div class="form-options">
                    <label class="checkbox-container">
                        <input type="checkbox" id="remember" name="remember">
                        <span class="checkmark"></span>
                        Recordarme
                    </label>
                    <a href="#" class="forgot-password">¿Olvidaste tu contraseña?</a>
                </div>

                <button type="submit" class="btn-login">
                    <i class="fas fa-sign-in-alt"></i> Iniciar Sesión
                </button>

                <div class="register-link">
                    ¿No tienes una cuenta? <a href="#" id="register-btn">Regístrate aquí</a>
                </div>
            </form>

            <form id="register-form" class="login-form" style="display: none;">
                <div class="form-group">
                    <label for="new-username"><i class="fas fa-user"></i> Nuevo Usuario</label>
                    <input type="text" id="new-username" name="new-username" placeholder="Crea tu usuario">
                    <div class="error-message" id="new-username-error"></div>
                </div>

                <div class="form-group">
                    <label for="new-password"><i class="fas fa-lock"></i> Nueva Contraseña</label>
                    <input type="password" id="new-password" name="new-password" placeholder="Crea tu contraseña">
                    <div class="error-message" id="new-password-error"></div>
                </div>

                <div class="form-group">
                    <label for="confirm-password"><i class="fas fa-lock"></i> Confirmar Contraseña</label>
                    <input type="password" id="confirm-password" name="confirm-password" placeholder="Confirma tu contraseña">
                    <div class="error-message" id="confirm-password-error"></div>
                </div>

                <button type="submit" class="btn-login">
                    <i class="fas fa-user-plus"></i> Crear Cuenta
                </button>

                <div class="register-link">
                    ¿Ya tienes una cuenta? <a href="" id="login-btn">Inicia sesión aquí</a>
                </div>
            </form>
        </div>

        <div class="notification" id="notification">
            <span id="notification-text"></span>
        </div>
    </div>

    <script src="js/login.js"></script>
</body>
</html>