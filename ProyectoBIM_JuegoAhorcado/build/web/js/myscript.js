// Palabras y pistas predefinidas
const palabras = [
    {
        palabra: "javascript",
        pistas: [
            "Lenguaje de programación interpretado",
            "Se ejecuta principalmente en navegadores web",
            "Creado por Brendan Eich en 1995"
        ]
    },
    {
        palabra: "computadora",
        pistas: [
            "Dispositivo electrónico que procesa datos",
            "Puede ser de escritorio o portátil",
            "Tiene componentes como CPU, RAM y disco duro"
        ]
    },
    {
        palabra: "programacion",
        pistas: [
            "Proceso de crear software",
            "Implica escribir código en un lenguaje específico",
            "Se usa para resolver problemas o automatizar tareas"
        ]
    },
    {
        palabra: "teclado",
        pistas: [
            "Dispositivo de entrada de datos",
            "Tiene teclas alfabéticas, numéricas y de función",
            "Puede ser mecánico o de membrana"
        ]
    },
    {
        palabra: "internet",
        pistas: [
            "Red global de computadoras interconectadas",
            "Permite acceso a la World Wide Web",
            "Se originó como ARPANET en los años 60"
        ]
    }
];

// Variables globales
let palabraSecreta = "";
let pistas = [];
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

// Elementos del DOM
const menuInicio = document.getElementById('menu-inicio');
const instrucciones = document.getElementById('instrucciones');
const juego = document.getElementById('juego');
const palabraElement = document.getElementById('palabra');
const pistasElement = document.getElementById('pistas');
const intentosElement = document.getElementById('intentos-restantes');
const tiempoElement = document.getElementById('tiempo');
const tecladoVirtual = document.getElementById('teclado-virtual');
const contadorPistas = document.getElementById('contador-pistas');
const canvas = document.getElementById('ahorcado-canvas');
const ctx = canvas.getContext('2d');

// Sonidos
const clickSound = document.getElementById('click-sound');
const winSound = document.getElementById('win-sound');
const loseSound = document.getElementById('lose-sound');

// Botones
document.getElementById('btn-comenzar').addEventListener('click', comenzarJuego);
document.getElementById('btn-instructions').addEventListener('click', mostrarInstrucciones);
document.getElementById('btn-salir').addEventListener('click', () => {
    playSound(clickSound);
    if (confirm('¿Estás seguro de que quieres salir?')) {
        window.close();
    }
});
document.getElementById('btn-volver-menu').addEventListener('click', volverAlMenuDesdeInstrucciones);
document.getElementById('btn-reiniciar').addEventListener('click', reiniciarJuego);
document.getElementById('btn-pausa').addEventListener('click', pausarJuego);
document.getElementById('btn-menu').addEventListener('click', volverAlMenu);
document.getElementById('btn-pista').addEventListener('click', usarPista);
document.getElementById('btn-reanudar').addEventListener('click', reanudarJuego);
document.getElementById('btn-jugar-otra-vez').addEventListener('click', reiniciarJuego);
document.getElementById('btn-volver-menu-fin').addEventListener('click', volverAlMenu);

// Reproducir sonido
function playSound(sound) {
    sound.currentTime = 0;
    sound.play().catch(e => console.log("Error reproduciendo sonido:", e));
}

// Inicializar teclado virtual
function inicializarTeclado() {
    tecladoVirtual.innerHTML = '';
    const letras = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('');
    
    letras.forEach(letra => {
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
    
    // Inicializar array de palabra adivinada con guiones bajos
    palabraAdivinada = Array(palabraSecreta.length).fill('_');
}

// Actualizar la visualización de la palabra
function actualizarPalabra() {
    palabraElement.innerHTML = '';
    palabraAdivinada.forEach(letra => {
        const letraElement = document.createElement('span');
        letraElement.className = 'letra';
        letraElement.textContent = letra;
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
        dibujarAhorcado();
        
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
    
    intentosRestantes = 6;
    pistasUsadas = 0;
    letrasAdivinadas.clear();
    letrasIncorrectas.clear();
    tiempoTranscurrido = 0;
    juegoActivo = true;
    juegoEnPausa = false;
    
    intentosElement.textContent = intentosRestantes;
    tiempoElement.textContent = '00:00';
    
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    dibujarAhorcado();
    iniciarCronometro();
}

// Mostrar instrucciones
function mostrarInstrucciones() {
    playSound(clickSound);
    menuInicio.style.display = 'none';
    instrucciones.style.display = 'flex';
}

// Volver al menú desde instrucciones
function volverAlMenuDesdeInstrucciones() {
    playSound(clickSound);
    instrucciones.style.display = 'none';
    menuInicio.style.display = 'block';
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
    modalFin.style.display = 'flex';
    iconoFin.innerHTML = '<i class="fas fa-trophy"></i>';
    document.getElementById('titulo-fin').textContent = '¡Felicidades!';
    document.getElementById('mensaje-fin').textContent = 'Has adivinado la palabra correctamente.';
    document.getElementById('palabra-correcta').textContent = palabraSecreta;
    document.getElementById('tiempo-fin').textContent = tiempoElement.textContent;
}

// Perder juego
function perderJuego() {
    juegoActivo = false;
    detenerCronometro();
    playSound(loseSound);
    
    const modalFin = document.getElementById('modal-fin');
    const iconoFin = document.getElementById('icono-fin');
    modalFin.style.display = 'flex';
    iconoFin.innerHTML = '<i class="fas fa-skull"></i>';
    document.getElementById('titulo-fin').textContent = '¡Game Over!';
    document.getElementById('mensaje-fin').textContent = 'Se te han agotado los intentos.';
    document.getElementById('palabra-correcta').textContent = palabraSecreta;
    document.getElementById('tiempo-fin').textContent = tiempoElement.textContent;
}

// Event listener para teclado físico
document.addEventListener('keydown', (e) => {
    if (juegoActivo && !juegoEnPausa) {
        const letra = e.key.toUpperCase();
        if (/^[A-Z]$/.test(letra) && !letrasAdivinadas.has(letra) && !letrasIncorrectas.has(letra)) {
            playSound(clickSound);
            adivinarLetra(letra);
        }
    }
});

// Inicializar canvas
ctx.clearRect(0, 0, canvas.width, canvas.height);
dibujarAhorcado();