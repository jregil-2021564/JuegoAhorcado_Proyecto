-- Creación de la base de datos
drop database if exists db_ahorcado;
create database db_ahorcado;
use db_ahorcado;

-- Tabla de Usuarios (simplificada para coincidir con tu HTML)
create table usuarios (
    codigo_Usuario int auto_increment,
    username varchar(100) unique not null,
    password varchar(100) not null,
    fecha_Registro timestamp default current_timestamp,
    primary key pk_codigo_Usuario (codigo_Usuario)
);

-- Tabla de Palabras
create table palabras (
    codigo_palabra int auto_increment,
    palabra varchar(100),
    pista varchar(200),
    primary key pk_codigo_palabra (codigo_palabra)
);

-- --------------------------- Procedimientos Almacenados para Usuarios ---------------------------

-- Agregar Usuario (Registro)
delimiter //
create procedure sp_agregar_usuario (
    in _username varchar(100),
    in _password varchar(100))
begin
    insert into usuarios (username, password)
    values (_username, _password);
    select last_insert_id() as codigo_Usuario;
end //
delimiter ;

-- Verificar Login (Inicio de sesión)
delimiter //
create procedure sp_verificar_login (
    in _username varchar(100),
    in _password varchar(100))
begin
    select codigo_Usuario, username
    from usuarios
    where username = _username and password = _password;
end //
delimiter ;

-- Verificar si usuario existe
delimiter //
create procedure sp_verificar_usuario_existente (in _username varchar(100))
begin
    select count(*) as existe from usuarios where username = _username;
end //
delimiter ;

-- Listar Usuarios
delimiter //
create procedure sp_listar_usuarios ()
begin
    select codigo_Usuario, username, fecha_Registro
    from usuarios;
end //
delimiter ;

-- Buscar Usuario por ID
delimiter //
create procedure sp_buscar_usuario_por_id (in _codigo_Usuario int)
begin
    select codigo_Usuario, username, fecha_Registro
    from usuarios
    where codigo_Usuario = _codigo_Usuario;
end //
delimiter ;

-- Eliminar Usuario
delimiter //
create procedure sp_eliminar_usuario (in _codigo_usuario int)
begin
    delete from usuarios where codigo_Usuario = _codigo_Usuario;
    select row_count() as filas_eliminadas;
end //
delimiter ;

-- --------------------------- Procedimientos Almacenados para Palabras ---------------------------

-- Agregar Palabra
delimiter //
create procedure sp_agregar_palabra (
    in _palabra varchar(100),
    in _pista varchar(200))
begin
    insert into palabras (palabra, pista)
    values (_palabra, _pista);
end //
delimiter ;

-- Listar Palabras
delimiter //
create procedure sp_listar_palabras ()
begin
    select codigo_palabra, palabra, pista from palabras;
end //
delimiter ;

-- Buscar Palabra por ID
delimiter //
create procedure sp_buscar_palabra (
    in _codigo_palabra int)
begin
    select codigo_palabra, palabra, pista from palabras
    where codigo_palabra = _codigo_palabra;
end //
delimiter ;

-- Obtener Palabra Aleatoria
delimiter //
create procedure sp_obtener_palabra_aleatoria ()
begin
    select codigo_palabra, palabra, pista from palabras
    order by rand()
    limit 1;
end //
delimiter ;

-- Editar Palabra
delimiter //
create procedure sp_editar_palabra (
    in _codigo_palabra int,
    in _palabra varchar(100),
    in _pista varchar(200))
begin
    update palabras
    set palabra = _palabra,
        pista = _pista
    where codigo_palabra = _codigo_palabra;
end //
delimiter ;

-- Eliminar Palabra
delimiter //
create procedure sp_eliminar_palabra (
    in _codigo_palabra int)
begin
    delete from palabras where codigo_palabra = _codigo_palabra;
    select row_count() as filas_eliminadas;
end //
delimiter ;

-- --------------------------- Inserción de Datos de Ejemplo ---------------------------

-- Insertar usuarios de ejemplo
call sp_agregar_usuario('admin', 'admin123');
call sp_agregar_usuario('carlos', 'carlos123');
call sp_agregar_usuario('ana', 'ana456');
call sp_agregar_usuario('luis', 'luis789');

-- Insertar palabras de ejemplo
call sp_agregar_palabra('torrefacto', 'Negro como la noche, en taza me encontrarás, si me pruebas con azúcar, ¿sabes cómo me llamarás?');
call sp_agregar_palabra('septiembre', 'Entre el calor que se apaga y el frío que viene ligero, traigo la patria en bandera y otoño en mi sombrero.');
call sp_agregar_palabra('manzanilla', 'Soy una flor sencilla y pequeña, me buscan por mi sabor, en infusiones me toman para calmar el dolor.');
call sp_agregar_palabra('precidente', 'Soy uno de los que dio taco de banano');
call sp_agregar_palabra('abecedario', 'De la A a la Z me puedes recitar, con mis letras se construyen las palabras al hablar.');
call sp_agregar_palabra('javascript', 'Lenguaje de programación interpretado que se ejecuta en navegadores web');
call sp_agregar_palabra('computadora', 'Dispositivo electrónico que procesa datos y tiene componentes como CPU y RAM');
call sp_agregar_palabra('programacion', 'Proceso de crear software mediante la escritura de código');
call sp_agregar_palabra('teclado', 'Dispositivo de entrada con teclas alfabéticas, numéricas y de función');
call sp_agregar_palabra('internet', 'Red global de computadoras interconectadas que permite acceso a la World Wide Web');

select * from usuarios;
select * from palabras;