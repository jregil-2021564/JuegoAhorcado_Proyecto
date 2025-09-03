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
    <link rel="stylesheet" href="css/estilo.css">
</head>
<body>
    <div class="video-background">
        <video autoplay muted loop id="bg-video">
            <source src="Image/videofondo.mp4" type="video/mp4">
        </video>
        <div class="video-overlay"></div>
    </div>

    <div class="container">
        <div id="menu-inicio" class="menu-inicio">
            <div class="logo-container">
                <div class="logo">
                    <h1>AHORCADO</h1>
                    <div class="logo-subtitle">Adivina la Palabra</div>
                </div>
                <div class="animated-icon">
                    <i class="fas fa-puzzle-piece"></i>
                </div>
            </div>
            
            <div class="menu-options">
                <div class="menu-buttons">
                    <button id="btn-comenzar" class="btn btn-primary">
                        <i class="fas fa-play"></i> Comenzar Juego
                    </button>
                    <button id="btn-dificultad" class="btn btn-secondary">
                        <i class="fas fa-sliders-h"></i> Dificultad
                    </button>
                    <button id="btn-puntajes" class="btn btn-secondary">
                        <i class="fas fa-trophy"></i> Puntajes
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
                        <div class="stat-icon">
                            <i class="fas fa-font"></i>
                        </div>
                        <div class="stat-number">5</div>
                        <div class="stat-label">Palabras</div>
                    </div>
                    <div class="stat">
                        <div class="stat-icon">
                            <i class="fas fa-lightbulb"></i>
                        </div>
                        <div class="stat-number">3</div>
                        <div class="stat-label">Pistas</div>
                    </div>
                    <div class="stat">
                        <div class="stat-icon">
                            <i class="fas fa-heart"></i>
                        </div>
                        <div class="stat-number">6</div>
                        <div class="stat-label">Intentos</div>
                    </div>
                </div>
            </div>
        </div>
        <div id="selector-dificultad" class="modal" style="display: none;">
            <div class="modal-content">
                <div class="modal-icon">
                    <i class="fas fa-sliders-h"></i>
                </div>
                <h2>Seleccionar Dificultad</h2>
                <div class="dificultad-opciones">
                    <div class="dificultad-opcion">
                        <input type="radio" id="facil" name="dificultad" value="facil" checked>
                        <label for="facil">
                            <i class="fas fa-smile"></i>
                            <span>Fácil</span>
                            <small>8 intentos, palabras cortas</small>
                        </label>
                    </div>
                    <div class="dificultad-opcion">
                        <input type="radio" id="normal" name="dificultad" value="normal">
                        <label for="normal">
                            <i class="fas fa-meh"></i>
                            <span>Normal</span>
                            <small>6 intentos, palabras medianas</small>
                        </label>
                    </div>
                    <div class="dificultad-opcion">
                        <input type="radio" id="dificil" name="dificultad" value="dificil">
                        <label for="dificil">
                            <i class="fas fa-frown"></i>
                            <span>Difícil</span>
                            <small>4 intentos, palabras largas</small>
                        </label>
                    </div>
                </div>
                <div class="modal-buttons">
                    <button id="btn-dificultad-cancelar" class="btn btn-secondary">
                        <i class="fas fa-times"></i> Cancelar
                    </button>
                    <button id="btn-dificultad-aplicar" class="btn btn-primary">
                        <i class="fas fa-check"></i> Aplicar
                    </button>
                </div>
            </div>
        </div>
        <div id="tabla-puntajes" class="modal" style="display: none;">
            <div class="modal-content">
                <div class="modal-icon">
                    <i class="fas fa-trophy"></i>
                </div>
                <h2>Mejores Puntajes</h2>
                <div class="puntajes-container">
                    <div class="puntajes-header">
                        <span>Posición</span>
                        <span>Jugador</span>
                        <span>Puntaje</span>
                        <span>Tiempo</span>
                    </div>
                    <div class="puntajes-list">
                        <div class="puntaje-item">
                            <span class="puesto">1</span>
                            <span class="jugador">Jugador 1</span>
                            <span class="puntos">950</span>
                            <span class="tiempo">01:45</span>
                        </div>
                        <div class="puntaje-item">
                            <span class="puesto">2</span>
                            <span class="jugador">Jugador 2</span>
                            <span class="puntos">850</span>
                            <span class="tiempo">02:10</span>
                        </div>
                        <div class="puntaje-item">
                            <span class="puesto">3</span>
                            <span class="jugador">Jugador 3</span>
                            <span class="puntos">750</span>
                            <span class="tiempo">02:30</span>
                        </div>
                    </div>
                </div>
                <div class="modal-buttons">
                    <button id="btn-puntajes-cerrar" class="btn btn-primary">
                        <i class="fas fa-times"></i> Cerrar
                    </button>
                </div>
            </div>
        </div>

        <div id="instrucciones" class="modal" style="display: none;">
            <div class="modal-content">
                <div class="modal-icon">
                    <i class="fas fa-info-circle"></i>
                </div>
                <h2>Instrucciones del Juego</h2>
                <div class="instrucciones-content">
                    <div class="instruccion-item">
                        <div class="instruccion-icon">
                            <i class="fas fa-mouse-pointer"></i>
                        </div>
                        <div class="instruccion-text">
                            <h3>Selección de Letras</h3>
                            <p>Haz clic en las letras del teclado virtual o usa tu teclado físico para adivinar la palabra.</p>
                        </div>
                    </div>
                    <div class="instruccion-item">
                        <div class="instruccion-icon">
                            <i class="fas fa-lightbulb"></i>
                        </div>
                        <div class="instruccion-text">
                            <h3>Pistas</h3>
                            <p>Tienes 3 pistas disponibles. Cada pista revelará información útil sobre la palabra.</p>
                        </div>
                    </div>
                    <div class="instruccion-item">
                        <div class="instruccion-icon">
                            <i class="fas fa-heart"></i>
                        </div>
                        <div class="instruccion-text">
                            <h3>Intentos</h3>
                            <p>Tienes 6 intentos. Cada letra incorrecta reduce tus intentos restantes.</p>
                        </div>
                    </div>
                    <div class="instruccion-item">
                        <div class="instruccion-icon">
                            <i class="fas fa-clock"></i>
                        </div>
                        <div class="instruccion-text">
                            <h3>Tiempo</h3>
                            <p>El cronómetro registra tu tiempo. ¡Adivina la palabra lo más rápido posible!</p>
                        </div>
                    </div>
                </div>
                <div class="modal-buttons">
                    <button id="btn-instrucciones-cerrar" class="btn btn-primary">
                        <i class="fas fa-times"></i> Cerrar
                    </button>
                </div>
            </div>
        </div>

        <div id="juego" class="juego" style="display: none;">
            <div class="header">
                <div class="game-info">
                    <div class="game-title">Ahorcado</div>
                    <div class="cronometro">
                        <i class="fas fa-clock"></i> <span id="tiempo">00:00</span>
                    </div>
                </div>
                <div class="controles">
                    <button id="btn-pausa" class="btn btn-control">
                        <i class="fas fa-pause"></i> Pausa
                    </button>
                    <button id="btn-reiniciar" class="btn btn-control">
                        <i class="fas fa-redo"></i> Reiniciar
                    </button>
                    <button id="btn-menu" class="btn btn-control">
                        <i class="fas fa-home"></i> Menú
                    </button>
                </div>
            </div>

            <div class="contenido-juego">
                <div class="seccion-superior">
                    <div class="ahorcado-container">
                        <canvas id="ahorcado-canvas" width="300" height="300"></canvas>
                        <div class="intentos">
                            <i class="fas fa-heart"></i> Intentos: <span id="intentos-restantes">6</span>/6
                        </div>
                    </div>

                    <div class="palabra-container">
                        <div id="palabra" class="palabra"></div>
                    </div>
                </div>
                
                <div class="seccion-inferior">
                    <div class="pistas-container">
                        <h3><i class="fas fa-lightbulb"></i> Pistas Disponibles:</h3>
                        <div id="pistas" class="pistas"></div>
                        <button id="btn-pista" class="btn btn-pista">
                            <i class="fas fa-question-circle"></i> Usar Pista 
                            <span id="contador-pistas">(3 restantes)</span>
                        </button>
                    </div>
                    
                    <div class="teclado-container">
                        <div id="teclado-virtual" class="teclado-virtual"></div>
                    </div>
                </div>
            </div>
        </div>

        <div id="modal-pausa" class="modal" style="display: none;">
            <div class="modal-content">
                <div class="modal-icon">
                    <i class="fas fa-pause-circle"></i>
                </div>
                <h2>Juego en Pausa</h2>
                <p>Tiempo transcurrido: <span id="tiempo-pausa">00:00</span></p>
                <div class="modal-buttons">
                    <button id="btn-reanudar" class="btn btn-primary">
                        <i class="fas fa-play"></i> Reanudar
                    </button>
                    <button id="btn-menu-pausa" class="btn btn-secondary">
                        <i class="fas fa-home"></i> Volver al Menú
                    </button>
                </div>
            </div>
        </div>

        <div id="modal-fin" class="modal" style="display: none;">
            <div class="modal-content">
                <div id="icono-fin" class="modal-icon"></div>
                <h2 id="titulo-fin"></h2>
                <p id="mensaje-fin"></p>
                
                <!-- Contenedor para la imagen de la palabra adivinada -->
                <div id="imagen-palabra-container" class="imagen-palabra-container">
                    <img id="imagen-palabra" src="" alt="Imagen de la palabra">
                </div>
                
                <div class="palabra-info">
                    La palabra era: <span id="palabra-correcta"></span>
                </div>
                <div class="tiempo-info">
                    <i class="fas fa-clock"></i> Tiempo: <span id="tiempo-fin">00:00</span>
                </div>
                <div class="modal-buttons">
                    <button id="btn-jugar-otra-vez" class="btn btn-primary">
                        <i class="fas fa-redo"></i> Jugar otra vez
                    </button>
                    <button id="btn-volver-menu-fin" class="btn btn-secondary">
                        <i class="fas fa-home"></i> Volver al menú
                    </button>
                </div>
            </div>
        </div>
    </div>

    <audio id="click-sound" src="" preload="auto"></audio>
    <audio id="win-sound" src="" preload="auto"></audio>
    <audio id="lose-sound" src="" preload="auto"></audio>

    <script src="js/myscript.js"></script>
</body>
</html>