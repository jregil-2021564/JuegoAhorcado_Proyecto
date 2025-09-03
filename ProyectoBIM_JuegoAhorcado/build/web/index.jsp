<%-- 
    Document   : index
    Created on : 1/09/2025, 18:31:20
    Author     : Usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Juego del Ahorcado Premium</title>
        <link rel="stylesheet" href="css/estilo.css" />
    </head>
    <body>
        <div class="video-background">
            <video autoplay muted loop id="bg-video">
                <source
                    src="Image/videofondo.mp4"
                    type="video/mp4"
                    />
            </video>
            <div class="video-overlay"></div>
        </div>

        <div class="container">
            <div id="menu-inicio" class="menu-inicio">
                <div class="logo-container">
                    <div class="logo">
                        <h1>AHORCADO</h1>
                        <div class="logo-subtitle">Creado por Reg1l</div>
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

            <div id="instrucciones" class="instrucciones" style="display: none">
                <div class="instrucciones-content">
                    <h2><i class="fas fa-info-circle"></i> Instrucciones del Juego</h2>
                    <ul>
                        <li>
                            <i class="fas fa-check"></i> Adivina la palabra antes de que se
                            complete el dibujo del ahorcado
                        </li>
                        <li>
                            <i class="fas fa-check"></i> Tienes 6 intentos para adivinar la
                            palabra
                        </li>
                        <li>
                            <i class="fas fa-check"></i> Puedes usar hasta 3 pistas para
                            obtener ayuda
                        </li>
                        <li>
                            <i class="fas fa-check"></i> Las pistas revelarán información
                            sobre la palabra
                        </li>
                        <li>
                            <i class="fas fa-check"></i> Puedes hacer clic en las letras o
                            usar tu teclado
                        </li>
                    </ul>
                    <button id="btn-volver-menu" class="btn btn-primary">
                        <i class="fas fa-arrow-left"></i> Volver al Menú
                    </button>
                </div>
            </div>

            <div id="juego" class="juego" style="display: none">
                <div class="header">
                    <div class="game-title">Ahorcado</div>
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
                    <div class="cronometro">
                        <i class="fas fa-clock"></i> <span id="tiempo">00:00</span>
                    </div>
                </div>

                <div class="contenido-juego">
                    <div class="columna-izq">
                        <div class="ahorcado-container">
                            <canvas id="ahorcado-canvas" width="300" height="350"></canvas>
                            <div class="intentos">
                                <i class="fas fa-heart"></i> Intentos:
                                <span id="intentos-restantes">6</span>/6
                            </div>
                        </div>
                    </div>

                    <div class="columna-der">
                        <div class="palabra-container">
                            <div id="palabra" class="palabra"></div>
                        </div>

                        <div class="pistas-container">
                            <h3><i class="fas fa-lightbulb"></i> Pistas Disponibles:</h3>
                            <div id="pistas" class="pistas"></div>
                            <button id="btn-pista" class="btn btn-pista">
                                <i class="fas fa-question-circle"></i> Usar Pista
                                <span id="contador-pistas">(3 restantes)</span>
                            </button>
                        </div>

                        <div class="teclado">
                            <div id="teclado-virtual" class="teclado-virtual"></div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="modal-pausa" class="modal" style="display: none">
                <div class="modal-content">
                    <div class="modal-icon">
                        <i class="fas fa-pause-circle"></i>
                    </div>
                    <h2>Juego en Pausa</h2>
                    <p>Tiempo transcurrido: <span id="tiempo-pausa">00:00</span></p>
                    <button id="btn-reanudar" class="btn btn-primary">
                        <i class="fas fa-play"></i> Reanudar
                    </button>
                </div>
            </div>

            <div id="modal-fin" class="modal" style="display: none">
                <div class="modal-content">
                    <div id="icono-fin" class="modal-icon"></div>
                    <h2 id="titulo-fin"></h2>
                    <p id="mensaje-fin"></p>
                    <div class="palabra-info">
                        La palabra era: <span id="palabra-correcta"></span>
                    </div>
                    <div class="tiempo-info">
                        <i class="fas fa-clock"></i> Tiempo:
                        <span id="tiempo-fin">00:00</span>
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

        <audio
            id="click-sound"
            src=""
            preload="auto"
            ></audio>
        <audio
            id="win-sound"
            src=""
            preload="auto"
            ></audio>
        <audio
            id="lose-sound"
            src=""
            preload="auto"
            ></audio>

        <script src="js/myscript.js"></script>
    </body>
</html>