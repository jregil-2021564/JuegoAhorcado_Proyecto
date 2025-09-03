// Palabras, pistas e imágenes predefinidas
const palabras = [
    {
        palabra: "javascript",
        pistas: [
            "Lenguaje de programación interpretado",
            "Se ejecuta principalmente en navegadores web",
            "Creado por Brendan Eich en 1995"
        ],
        imagen: "Image/imagen_javascript.png"
    },
    {
        palabra: "computadora",
        pistas: [
            "Dispositivo electrónico que procesa datos",
            "Puede ser de escritorio o portátil",
            "Tiene componentes como CPU, RAM y disco duro"
        ],
        imagen: "Image/imagen_computadora.png"
    },
    {
        palabra: "programacion",
        pistas: [
            "Proceso de crear software",
            "Implica escribir código en un lenguaje específico",
            "Se usa para resolver problemas o automatizar tareas"
        ],
        imagen: "Image/imagen_progra.png"
    },
    {
        palabra: "teclado",
        pistas: [
            "Dispositivo de entrada de datos",
            "Tiene teclas alfabéticas, numéricas y de función",
            "Puede ser mecánico o de membrana"
        ],
        imagen: "Image/imagen_teclado.png"
    },
    {
        palabra: "internet",
        pistas: [
            "Red global de computadoras interconectadas",
            "Permite acceso to the World Wide Web",
            "Se originó como ARPANET en los años 60"
        ],
        imagen: "Image/imagen_internet.png"
    }
];


const imagenesAhorcado = [
    "Image/ImagenFondo_Ahorcado.png",  
    "Image/Imagen_cabeza.png", 
    "Image/imagen_cuerpo.png",  
    "Image/imagen_brazoderecho.png", 
    "Image/imagen-brazoizq.png",  
    "Image/imagen_piernader.png", 
    "Image/imagen_piernaizq.png"  

];

// Variables globales
let palabraSecreta = "";
let pistas = [];
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

// Botones del menú principal
document.getElementById('btn-comenzar').addEventListener('click', comenzarJuego);
document.getElementById('btn-dificultad').addEventListener('click', mostrarDificultad);
document.getElementById('btn-puntajes').addEventListener('click', mostrarPuntajes);
document.getElementById('btn-instructions').addEventListener('click', mostrarInstrucciones);
document.getElementById('btn-salir').addEventListener('click', () => {
    playSound(clickSound);
    if (confirm('¿Estás seguro de que quieres salir?')) {
        window.close();
    }
});

// Botones de modales
document.getElementById('btn-dificultad-cancelar').addEventListener('click', ocultarDificultad);
document.getElementById('btn-dificultad-aplicar').addEventListener('click', aplicarDificultad);
document.getElementById('btn-puntajes-cerrar').addEventListener('click', ocultarPuntajes);
document.getElementById('btn-instrucciones-cerrar').addEventListener('click', ocultarInstrucciones);

// Botones del juego
document.getElementById('btn-reiniciar').addEventListener('click', reiniciarJuego);
document.getElementById('btn-pausa').addEventListener('click', pausarJuego);
document.getElementById('btn-menu').addEventListener('click', volverAlMenu);
document.getElementById('btn-pista').addEventListener('click', usarPista);
document.getElementById('btn-reanudar').addEventListener('click', reanudarJuego);
document.getElementById('btn-menu-pausa').addEventListener('click', volverAlMenuDesdePausa);
document.getElementById('btn-jugar-otra-vez').addEventListener('click', reiniciarJuego);
document.getElementById('btn-volver-menu-fin').addEventListener('click', volverAlMenu);

// Reproducir sonido
function playSound(sound) {
    sound.currentTime = 0;
    sound.play().catch(e => console.log("Error reproduciendo sonido:", e));
}

// Mostrar selector de dificultad
function mostrarDificultad() {
    playSound(clickSound);
    selectorDificultad.style.display = 'flex';
}

// Ocultar selector de dificultad
function ocultarDificultad() {
    playSound(clickSound);
    selectorDificultad.style.display = 'none';
}

// Aplicar configuración de dificultad
function aplicarDificultad() {
    playSound(clickSound);
    const dificultad = document.querySelector('input[name="dificultad"]:checked').value;
    
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

// Mostrar tabla de puntajes
function mostrarPuntajes() {
    playSound(clickSound);
    tablaPuntajes.style.display = 'flex';
}

// Ocultar tabla de puntajes
function ocultarPuntajes() {
    playSound(clickSound);
    tablaPuntajes.style.display = 'none';
}

// Mostrar instrucciones
function mostrarInstrucciones() {
    playSound(clickSound);
    instrucciones.style.display = 'flex';
}

// Ocultar instrucciones
function ocultarInstrucciones() {
    playSound(clickSound);
    instrucciones.style.display = 'none';
}

// Inicializar teclado virtual
function inicializarTeclado() {
    tecladoVirtual.innerHTML = '';
    
    // Definir todas las letras del abecedario
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

// Seleccionar una palabra aleatoria
function seleccionarPalabra() {
    const indice = Math.floor(Math.random() * palabras.length);
    palabraSecreta = palabras[indice].palabra.toUpperCase();
    pistas = palabras[indice].pistas;
    imagenPalabra = palabras[indice].imagen;
    
    // Inicializar array de palabra adivinada con guiones bajos
    palabraAdivinada = Array(palabraSecreta.length).fill('_');
}

// Actualizar la visualización de la palabra
function actualizarPalabra() {
    palabraElement.innerHTML = '';
    palabraAdivinada.forEach((letra, index) => {
        const letraElement = document.createElement('span');
        letraElement.className = 'letra';
        letraElement.textContent = letra;
        
        // Añadir animación si la letra fue recién revelada
        if (letra !== '_') {
            letraElement.classList.add('revelada');
        }
        
        palabraElement.appendChild(letraElement);
    });
}

// Mostrar pistas
function mostrarPistas() {
    pistasElement.innerHTML = '';
    pistas.forEach((pista, index) => {
        const pistaElement = document.createElement('div');
        pistaElement.className = 'pista';
        pistaElement.id = `pista-${index}`;
        pistaElement.textContent = `Pista ${index + 1}: [Haz clic en Usar Pista para revelar]`;
        pistasElement.appendChild(pistaElement);
    });
    contadorPistas.textContent = `(${3 - pistasUsadas} restantes)`;
}

// Mostrar la imagen del ahorcado según los errores
function mostrarImagenAhorcado() {
    const errores = 6 - intentosRestantes;
    const indiceImagen = Math.min(errores, imagenesAhorcado.length - 1);
    imagenAhorcado.src = imagenesAhorcado[indiceImagen];
}

// Usar una pista
function usarPista() {
    if (pistasUsadas < 3 && juegoActivo && !juegoEnPausa) {
        playSound(clickSound);
        
        // Revelar una letra aleatoria que no haya sido adivinada
        let letrasNoAdivinadas = [];
        for (let i = 0; i < palabraSecreta.length; i++) {
            if (palabraAdivinada[i] === '_') {
                letrasNoAdivinadas.push(i);
            }
        }
        
        if (letrasNoAdivinadas.length > 0) {
            const indiceAleatorio = letrasNoAdivinadas[Math.floor(Math.random() * letrasNoAdivinadas.length)];
            const letra = palabraSecreta[indiceAleatorio];
            
            // Revelar todas las instancias de esta letra
            for (let i = 0; i < palabraSecreta.length; i++) {
                if (palabraSecreta[i] === letra) {
                    palabraAdivinada[i] = letra;
                }
            }
            
            letrasAdivinadas.add(letra);
            actualizarPalabra();
            
            // Revelar la pista
            const pistaElement = document.getElementById(`pista-${pistasUsadas}`);
            pistaElement.textContent = `Pista ${pistasUsadas + 1}: ${pistas[pistasUsadas]}`;
            pistaElement.classList.add('revelada');
            
            pistasUsadas++;
            contadorPistas.textContent = `(${3 - pistasUsadas} restantes)`;
            
            // Verificar si se ganó el juego
            if (!palabraAdivinada.includes('_')) {
                ganarJuego();
            }
        }
    } else if (pistasUsadas >= 3) {
        alert('Ya has usado todas las pistas disponibles.');
    }
}

// Adivinar una letra
function adivinarLetra(letra) {
    if (!juegoActivo || juegoEnPausa || letrasAdivinadas.has(letra) || letrasIncorrectas.has(letra)) {
        return;
    }
    
    letrasAdivinadas.add(letra);
    const tecla = document.querySelector(`.tecla[data-letra="${letra}"]`);
    
    if (palabraSecreta.includes(letra)) {
        // Letra correcta
        tecla.classList.add('acertada');
        
        // Actualizar palabra adivinada
        for (let i = 0; i < palabraSecreta.length; i++) {
            if (palabraSecreta[i] === letra) {
                palabraAdivinada[i] = letra;
            }
        }
        
        actualizarPalabra();
        
        // Verificar si se ganó el juego
        if (!palabraAdivinada.includes('_')) {
            ganarJuego();
        }
    } else {
        // Letra incorrecta
        tecla.classList.add('fallada');
        letrasIncorrectas.add(letra);
        intentosRestantes--;
        intentosElement.textContent = intentosRestantes;
        mostrarImagenAhorcado();
        
        // Verificar si se perdió el juego
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

// Comenzar juego
function comenzarJuego() {
    playSound(clickSound);
    menuInicio.style.display = 'none';
    juego.style.display = 'block';
    
    // Inicializar juego
    seleccionarPalabra();
    inicializarTeclado();
    actualizarPalabra();
    mostrarPistas();
    
    pistasUsadas = 0;
    letrasAdivinadas.clear();
    letrasIncorrectas.clear();
    tiempoTranscurrido = 0;
    juegoActivo = true;
    juegoEnPausa = false;
    
    intentosElement.textContent = intentosRestantes;
    tiempoElement.textContent = '00:00';
    
    // Mostrar imagen inicial del ahorcado
    imagenAhorcado.src = imagenesAhorcado[0];
    
    iniciarCronometro();
}

// Reiniciar juego
function reiniciarJuego() {
    playSound(clickSound);
    detenerCronometro();
    document.getElementById('modal-fin').style.display = 'none';
    comenzarJuego();
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

// Volver al menú desde pausa
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
    
    // Mostrar imagen de la palabra
    imagenPalabraElement.src = imagenPalabra;
    imagenPalabraElement.alt = `Imagen de ${palabraSecreta}`;
    
    // Añadir clase de celebración
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
    
    // Mostrar imagen de la palabra
    imagenPalabraElement.src = imagenPalabra;
    imagenPalabraElement.alt = `Imagen de ${palabraSecreta}`;
}

// Event listener para teclado físico
document.addEventListener('keydown', (e) => {
    if (juegoActivo && !juegoEnPausa) {
        const letra = e.key.toUpperCase();
        // Permitir la Ñ
        if ((/^[A-Z]$/.test(letra) || letra === 'Ñ') && !letrasAdivinadas.has(letra) && !letrasIncorrectas.has(letra)) {
            playSound(clickSound);
            adivinarLetra(letra);
        }
    }
});

// Inicializar imagen del ahorcado
imagenAhorcado.src = imagenesAhorcado[0];