-- Creación de la base de datos
DROP DATABASE IF EXISTS DB_ahorcado; 
CREATE DATABASE DB_ahorcado;
USE DB_ahorcado;

-- Tabla de Usuarios (simplificada para coincidir con tu HTML)
CREATE TABLE Usuarios(
    codigoUsuario INT AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    fechaRegistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY PK_codigoUsuario (codigoUsuario)
);

-- Tabla de Palabras
CREATE TABLE Palabras(
    codigoPalabra INT AUTO_INCREMENT,
    palabra VARCHAR(100),
    pista VARCHAR(200),     
    PRIMARY KEY PK_codigoPalabra (codigoPalabra)
);

-- --------------------------- PROCEDIMIENTOS ALMACENADOS PARA USUARIOS ---------------------------

-- Agregar Usuario (Registro)
DELIMITER //
CREATE PROCEDURE sp_AgregarUsuario(
    IN _username VARCHAR(100),
    IN _password VARCHAR(100))
BEGIN
    INSERT INTO Usuarios(username, password)
    VALUES (_username, _password);
    SELECT LAST_INSERT_ID() as codigoUsuario;
END //
DELIMITER ;

-- Verificar Login (Inicio de sesión)
DELIMITER //
CREATE PROCEDURE sp_VerificarLogin(
    IN _username VARCHAR(100), 
    IN _password VARCHAR(100))
BEGIN
    SELECT codigoUsuario, username 
    FROM Usuarios
    WHERE username = _username AND password = _password;
END //
DELIMITER ;

-- Verificar si usuario existe
DELIMITER //
CREATE PROCEDURE sp_VerificarUsuarioExistente(IN _username VARCHAR(100))
BEGIN
    SELECT COUNT(*) as existe FROM Usuarios WHERE username = _username;
END //
DELIMITER ;

-- Listar Usuarios
DELIMITER //
CREATE PROCEDURE sp_ListarUsuarios()
BEGIN
    SELECT codigoUsuario, username, fechaRegistro 
    FROM Usuarios;
END //
DELIMITER ;

-- Buscar Usuario por ID
DELIMITER //
CREATE PROCEDURE sp_BuscarUsuarioPorId(IN _codigoUsuario INT)
BEGIN
    SELECT codigoUsuario, username, fechaRegistro 
    FROM Usuarios
    WHERE codigoUsuario = _codigoUsuario;
END //
DELIMITER ;

-- Eliminar Usuario
DELIMITER //
CREATE PROCEDURE sp_EliminarUsuario(IN _codigoUsuario INT)
BEGIN
    DELETE FROM Usuarios WHERE codigoUsuario = _codigoUsuario;
    SELECT ROW_COUNT() AS filasEliminadas;
END //
DELIMITER ;

-- --------------------------- PROCEDIMIENTOS ALMACENADOS PARA PALABRAS ---------------------------

-- Agregar Palabra
DELIMITER //
CREATE PROCEDURE sp_AgregarPalabra(
    IN _palabra VARCHAR(100),
    IN _pista VARCHAR(200))
BEGIN
    INSERT INTO Palabras(palabra, pista)
    VALUES (_palabra, _pista);
END //
DELIMITER ;

-- Listar Palabras
DELIMITER //
CREATE PROCEDURE sp_ListarPalabras()
BEGIN
    SELECT codigoPalabra, palabra, pista FROM Palabras;
END //
DELIMITER ;

-- Buscar Palabra por ID
DELIMITER //
CREATE PROCEDURE sp_BuscarPalabra(
    IN _codigoPalabra INT)
BEGIN
    SELECT codigoPalabra, palabra, pista FROM Palabras
    WHERE codigoPalabra = _codigoPalabra;
END //
DELIMITER ;

-- Obtener Palabra Aleatoria
DELIMITER //
CREATE PROCEDURE sp_ObtenerPalabraAleatoria()
BEGIN
    SELECT codigoPalabra, palabra, pista FROM Palabras
    ORDER BY RAND()
    LIMIT 1;
END //
DELIMITER ;

-- Editar Palabra
DELIMITER //
CREATE PROCEDURE sp_EditarPalabra(
    IN _codigoPalabra INT,
    IN _palabra VARCHAR(100),
    IN _pista VARCHAR(200)) 
BEGIN
    UPDATE Palabras
    SET palabra = _palabra,
        pista = _pista
    WHERE codigoPalabra = _codigoPalabra;
END //
DELIMITER ;

-- Eliminar Palabra
DELIMITER //
CREATE PROCEDURE sp_EliminarPalabra(
    IN _codigoPalabra INT)
BEGIN
    DELETE FROM Palabras WHERE codigoPalabra = _codigoPalabra;
    SELECT ROW_COUNT() AS filasEliminadas;
END //
DELIMITER ;

-- --------------------------- INSERCIÓN DE DATOS DE EJEMPLO ---------------------------

-- Insertar usuarios de ejemplo
CALL sp_AgregarUsuario('admin', 'admin123');
CALL sp_AgregarUsuario('carlos', 'carlos123');
CALL sp_AgregarUsuario('ana', 'ana456');
CALL sp_AgregarUsuario('luis', 'luis789');

-- Insertar palabras de ejemplo
CALL sp_AgregarPalabra('TORREFACTO', 'Negro como la noche, en taza me encontrarás, si me pruebas con azúcar, ¿sabes cómo me llamarás?');
CALL sp_AgregarPalabra('SEPTIEMBRE', 'Entre el calor que se apaga y el frío que viene ligero, traigo la patria en bandera y otoño en mi sombrero.');
CALL sp_AgregarPalabra('MANZANILLA', 'Soy una flor sencilla y pequeña, me buscan por mi sabor, en infusiones me toman para calmar el dolor.');
CALL sp_AgregarPalabra('PRECIDENTE', 'Soy uno de los que dio taco de banano');
CALL sp_AgregarPalabra('ABECEDARIO', 'De la A a la Z me puedes recitar, con mis letras se construyen las palabras al hablar.');
CALL sp_AgregarPalabra('JAVASCRIPT', 'Lenguaje de programación interpretado que se ejecuta en navegadores web');
CALL sp_AgregarPalabra('COMPUTADORA', 'Dispositivo electrónico que procesa datos y tiene componentes como CPU y RAM');
CALL sp_AgregarPalabra('PROGRAMACION', 'Proceso de crear software mediante la escritura de código');
CALL sp_AgregarPalabra('TECLADO', 'Dispositivo de entrada con teclas alfabéticas, numéricas y de función');
CALL sp_AgregarPalabra('INTERNET', 'Red global de computadoras interconectadas que permite acceso a la World Wide Web');

select * from Usuarios;