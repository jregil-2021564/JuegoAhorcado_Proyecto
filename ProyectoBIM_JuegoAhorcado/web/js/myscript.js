// myscript.js
const palabras_local = [
    {
        palabra: "javascript",
        imagen: "Image/imagen_javascript.png"
    },
    {
        palabra: "computadora",
        imagen: "Image/imagen_computadora.png"
    },
    {
        palabra: "programacion",
        imagen: "Image/imagen_progra.png"
    },
    {
        palabra: "teclado",
        imagen: "Image/imagen_teclado.png"
    },
    {
        palabra: "internet",
        imagen: "Image/imagen_internet.png"
    }
];

// Variables globales
let palabraSecreta = "";
let pista = "";
let imagenPalabra = "";
let palabraAdivinada = [];
let letrasAdivinadas = new Set();
let letrasIncorrectas = new Set();
let intentosRestantes = 6;
let pistasUsadas = 0;
let tiempoInicio = 0;
let tiempoTranscurrido = 0;
let cronometroInterval = null;
let juegoEnPausa = false;
let juegoActivo = false;
let dificultadActual = "normal";

// Imágenes del ahorcado 
const imagenesAhorcado = [
    "Image/ImagenFondo_Ahorcado.png",
    "Image/Imagen_cabeza.png",
    "Image/imagen_cuerpo.png",
    "Image/imagen_brazoderecho.png",
    "Image/imagen-brazoizq.png",
    "Image/imagen_piernader.png",
    "Image/imagen_piernaizq.png"
];

// Elementos del DOM
const menuInicio = document.getElementById('menu-inicio');
const selectorDificultad = document.getElementById('selector-dificultad');
const tablaPuntajes = document.getElementById('tabla-puntajes');
const instrucciones = document.getElementById('instrucciones');
const juego = document.getElementById('juego');
const palabraElement = document.getElementById('palabra');
const pistasElement = document.getElementById('pistas');
const intentosElement = document.getElementById('intentos-restantes');
const tiempoElement = document.getElementById('tiempo');
const tecladoVirtual = document.getElementById('teclado-virtual');
const contadorPistas = document.getElementById('contador-pistas');
const imagenAhorcado = document.getElementById('imagen-ahorcado');

// Sonidos
const clickSound = document.getElementById('click-sound');
const winSound = document.getElementById('win-sound');
const loseSound = document.getElementById('lose-sound');

// Eventos de botones
document.getElementById('btn-comenzar').addEventListener('click', () => {
    playSound(clickSound);
    window.location.href = "JuegoControlador";
});
document.getElementById('btn-dificultad').addEventListener('click', mostrarDificultad);
document.getElementById('btn-puntajes').addEventListener('click', mostrarPuntajes);
document.getElementById('btn-instructions').addEventListener('click', mostrarInstrucciones);
document.getElementById('btn-salir').addEventListener('click', () => {
    playSound(clickSound);
    if (confirm('¿Estás seguro de que quieres salir?')) {
        window.close();
    }
});

document.getElementById('btn-dificultad-cancelar').addEventListener('click', ocultarDificultad);
document.getElementById('btn-dificultad-aplicar').addEventListener('click', aplicarDificultad);
document.getElementById('btn-puntajes-cerrar').addEventListener('click', ocultarPuntajes);
document.getElementById('btn-instrucciones-cerrar').addEventListener('click', ocultarInstrucciones);

document.getElementById('btn-reiniciar').addEventListener('click', reiniciarJuego);
document.getElementById('btn-pausa').addEventListener('click', pausarJuego);
document.getElementById('btn-menu').addEventListener('click', volverAlMenu);
document.getElementById('btn-pista').addEventListener('click', usarPista);
document.getElementById('btn-reanudar').addEventListener('click', reanudarJuego);
document.getElementById('btn-menu-pausa').addEventListener('click', volverAlMenuDesdePausa);
document.getElementById('btn-jugar-otra-vez').addEventListener('click', reiniciarJuego);
document.getElementById('btn-volver-menu-fin').addEventListener('click', volverAlMenu);

function playSound(sound) {
    if (sound) {
        sound.currentTime = 0;
        sound.play().catch(e => console.log("Error reproduciendo sonido:", e));
    }
}

function mostrarDificultad() {
    playSound(clickSound);
    selectorDificultad.style.display = 'flex';
}

function ocultarDificultad() {
    playSound(clickSound);
    selectorDificultad.style.display = 'none';
}

function aplicarDificultad() {
    playSound(clickSound);
    const dificultad = document.querySelector('input[name="dificultad"]:checked').value;
    dificultadActual = dificultad;
    
    switch(dificultad) {
        case 'facil':
            intentosRestantes = 8;
            break;
        case 'normal':
            intentosRestantes = 6;
            break;
        case 'dificil':
            intentosRestantes = 4;
            break;
    }
    
    ocultarDificultad();
    alert(`Dificultad establecida a: ${dificultad.toUpperCase()}`);
}

function mostrarPuntajes() {
    playSound(clickSound);
    tablaPuntajes.style.display = 'flex';
}

function ocultarPuntajes() {
    playSound(clickSound);
    tablaPuntajes.style.display = 'none';
}

function mostrarInstrucciones() {
    playSound(clickSound);
    instrucciones.style.display = 'flex';
}

function ocultarInstrucciones() {
    playSound(clickSound);
    instrucciones.style.display = 'none';
}

// Inicializar teclado virtual
function inicializarTeclado() {
    tecladoVirtual.innerHTML = '';
    
    const abecedario = 'ABCDEFGHIJKLMNOPQRSTUVWXYZÑ'.split('');
    
    abecedario.forEach(letra => {
        const tecla = document.createElement('div');
        tecla.className = 'tecla';
        tecla.textContent = letra;
        tecla.dataset.letra = letra;
        tecla.addEventListener('click', () => {
            playSound(clickSound);
            adivinarLetra(letra);
        });
        tecladoVirtual.appendChild(tecla);
    });
}

// Reiniciar el estado del juego
function reiniciarEstadoJuego() {
    letrasAdivinadas.clear();
    letrasIncorrectas.clear();
    pistasUsadas = 0;
    tiempoTranscurrido = 0;
    
    switch(dificultadActual) {
        case 'facil':
            intentosRestantes = 8;
            break;
        case 'normal':
            intentosRestantes = 6;
            break;
        case 'dificil':
            intentosRestantes = 4;
            break;
    }

    intentosElement.textContent = intentosRestantes;
    imagenAhorcado.src = imagenesAhorcado[0];
    
    const teclas = document.querySelectorAll('.tecla');
    teclas.forEach(tecla => {
        tecla.classList.remove('acertada', 'fallada', 'deshabilitada');
    });
}

// Actualizar la visualización de la palabra
function actualizarPalabra() {
    palabraElement.innerHTML = '';
    palabraAdivinada.forEach((letra) => {
        const letraElement = document.createElement('span');
        letraElement.className = 'letra';
        letraElement.textContent = letra;
        if (letra !== '_') {
            letraElement.classList.add('revelada');
        }
        palabraElement.appendChild(letraElement);
    });
}

// Mostrar imagen del ahorcado según los errores
function mostrarImagenAhorcado() {
    const errores = (dificultadActual === 'facil' ? 8 : dificultadActual === 'normal' ? 6 : 4) - intentosRestantes;
    const indiceImagen = Math.min(errores, imagenesAhorcado.length - 1);
    imagenAhorcado.src = imagenesAhorcado[indiceImagen];
}

function usarPista() {
    if (pistasUsadas < 3 && juegoActivo && !juegoEnPausa) {
        playSound(clickSound);
        
        pistasElement.textContent = "Pista: " + pista;
        pistasUsadas++;
        contadorPistas.textContent = `(${3 - pistasUsadas} restantes)`;

        let letrasNoAdivinadas = [];
        for (let i = 0; i < palabraSecreta.length; i++) {
            if (palabraAdivinada[i] === '_') {
                letrasNoAdivinadas.push(i);
            }
        }
        
        if (letrasNoAdivinadas.length > 0) {
            const indiceAleatorio = letrasNoAdivinadas[Math.floor(Math.random() * letrasNoAdivinadas.length)];
            const letra = palabraSecreta[indiceAleatorio];
            
            for (let i = 0; i < palabraSecreta.length; i++) {
                if (palabraSecreta[i] === letra) {
                    palabraAdivinada[i] = letra;
                }
            }
            letrasAdivinadas.add(letra);
            actualizarPalabra();
            
            if (!palabraAdivinada.includes('_')) {
                ganarJuego();
            }
        }
    } else {
        alert('Ya has usado todas las pistas disponibles.');
    }
}


// Adivinar una letra
function adivinarLetra(letra) {
    if (!juegoActivo || juegoEnPausa || letrasAdivinadas.has(letra) || letrasIncorrectas.has(letra)) {
        return;
    }
    
    const tecla = document.querySelector(`.tecla[data-letra="${letra}"]`);
    
    if (palabraSecreta.includes(letra)) {
        tecla.classList.add('acertada');
        
        for (let i = 0; i < palabraSecreta.length; i++) {
            if (palabraSecreta[i] === letra) {
                palabraAdivinada[i] = letra;
            }
        }
        
        letrasAdivinadas.add(letra);
        actualizarPalabra();
        
        if (!palabraAdivinada.includes('_')) {
            ganarJuego();
        }
    } else {
        tecla.classList.add('fallada');
        letrasIncorrectas.add(letra);
        intentosRestantes--;
        intentosElement.textContent = intentosRestantes;
        mostrarImagenAhorcado();
        
        if (intentosRestantes === 0) {
            perderJuego();
        }
    }
}

// Iniciar cronómetro
function iniciarCronometro() {
    tiempoInicio = Date.now() - tiempoTranscurrido;
    cronometroInterval = setInterval(actualizarCronometro, 1000);
}

// Actualizar cronómetro
function actualizarCronometro() {
    if (!juegoEnPausa) {
        tiempoTranscurrido = Date.now() - tiempoInicio;
        const segundos = Math.floor(tiempoTranscurrido / 1000);
        const minutos = Math.floor(segundos / 60);
        tiempoElement.textContent = `${minutos.toString().padStart(2, '0')}:${(segundos % 60).toString().padStart(2, '0')}`;
    }
}

// Detener cronómetro
function detenerCronometro() {
    clearInterval(cronometroInterval);
}

// Función principal para iniciar el juego desde el JSP
function iniciarNuevoJuego(nuevaPalabra, nuevaPista) {
    if (!nuevaPalabra || nuevaPalabra.length === 0) {
        console.error("Error: no se recibió una palabra del servidor.");
        return;
    }
    
    palabraSecreta = nuevaPalabra.toUpperCase();
    pista = nuevaPista;

    const palabraEncontrada = palabras_local.find(p => p.palabra.toUpperCase() === palabraSecreta);
    if (palabraEncontrada) {
        imagenPalabra = palabraEncontrada.imagen;
    } else {
        imagenPalabra = "Image/imagen_default.png"; 
    }

    palabraAdivinada = Array(palabraSecreta.length).fill('_');

    menuInicio.style.display = 'none';
    juego.style.display = 'block';
    
    reiniciarEstadoJuego();
    actualizarPalabra();
    inicializarTeclado();
    
    pistasElement.textContent = ""; 
    contadorPistas.textContent = '(1 restante)';

    tiempoTranscurrido = 0;
    juegoActivo = true;
    juegoEnPausa = false;
    tiempoElement.textContent = '00:00';
    iniciarCronometro();
}

function reiniciarJuego() {
    playSound(clickSound);
    detenerCronometro();
    document.getElementById('modal-fin').style.display = 'none';
    window.location.href = "JuegoControlador";
}

// Pausar juego
function pausarJuego() {
    if (juegoActivo && !juegoEnPausa) {
        playSound(clickSound);
        juegoEnPausa = true;
        detenerCronometro();
        document.getElementById('modal-pausa').style.display = 'flex';
        document.getElementById('tiempo-pausa').textContent = tiempoElement.textContent;
    }
}

// Reanudar juego
function reanudarJuego() {
    if (juegoActivo && juegoEnPausa) {
        playSound(clickSound);
        juegoEnPausa = false;
        document.getElementById('modal-pausa').style.display = 'none';
        iniciarCronometro();
    }
}

// Volver al menú desde modo de pausa
function volverAlMenuDesdePausa() {
    playSound(clickSound);
    juegoEnPausa = false;
    document.getElementById('modal-pausa').style.display = 'none';
    volverAlMenu();
}

// Volver al menú
function volverAlMenu() {
    playSound(clickSound);
    if (juegoActivo) {
        if (confirm('¿Estás seguro de que quieres volver al menú? Se perderá el progreso actual.')) {
            detenerCronometro();
            juego.style.display = 'none';
            document.getElementById('modal-pausa').style.display = 'none';
            document.getElementById('modal-fin').style.display = 'none';
            menuInicio.style.display = 'block';
            juegoActivo = false;
        }
    } else {
        detenerCronometro();
        juego.style.display = 'none';
        document.getElementById('modal-pausa').style.display = 'none';
        document.getElementById('modal-fin').style.display = 'none';
        menuInicio.style.display = 'block';
    }
}

// Ganar juego
function ganarJuego() {
    juegoActivo = false;
    detenerCronometro();
    playSound(winSound);
    
    const modalFin = document.getElementById('modal-fin');
    const iconoFin = document.getElementById('icono-fin');
    const imagenPalabraElement = document.getElementById('imagen-palabra');
    
    modalFin.style.display = 'flex';
    iconoFin.innerHTML = '<i class="fas fa-trophy"></i>';
    document.getElementById('titulo-fin').textContent = '¡Felicidades!';
    document.getElementById('mensaje-fin').textContent = 'Has adivinado la palabra correctamente.';
    document.getElementById('palabra-correcta').textContent = palabraSecreta;
    document.getElementById('tiempo-fin').textContent = tiempoElement.textContent;

    // Usa la imagen de la palabra actual
    imagenPalabraElement.src = imagenPalabra; 
    imagenPalabraElement.alt = `Imagen de ${palabraSecreta}`;

    document.getElementById('imagen-palabra-container').classList.add('celebrate');
}

// Perder juego
function perderJuego() {
    juegoActivo = false;
    detenerCronometro();
    playSound(loseSound);
    
    const modalFin = document.getElementById('modal-fin');
    const iconoFin = document.getElementById('icono-fin');
    const imagenPalabraElement = document.getElementById('imagen-palabra');
    
    modalFin.style.display = 'flex';
    iconoFin.innerHTML = '<i class="fas fa-skull"></i>';
    document.getElementById('titulo-fin').textContent = '¡Game Over!';
    document.getElementById('mensaje-fin').textContent = 'Se te han agotado los intentos.';
    document.getElementById('palabra-correcta').textContent = palabraSecreta;
    document.getElementById('tiempo-fin').textContent = tiempoElement.textContent;
    
    imagenPalabraElement.src = imagenPalabra; 
    imagenPalabraElement.alt = `Imagen de ${palabraSecreta}`;
}

// Código para que sirva el teclado físico
document.addEventListener('keydown', (e) => {
    if (juegoActivo && !juegoEnPausa) {
        const letra = e.key.toUpperCase();
        if ((/^[A-Z]$/.test(letra) || letra === 'Ñ') && !letrasAdivinadas.has(letra) && !letrasIncorrectas.has(letra)) {
            playSound(clickSound);
            adivinarLetra(letra);
        }
    }
});

// Inicializar imagen del ahorcado al cargar
imagenAhorcado.src = imagenesAhorcado[0];