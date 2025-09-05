-- drop database if exists DB_Ahorcado;
create database DB_Ahorcado;
use DB_Ahorcado;

-- Tabla de usuarios
Create table Usuarios (
    codigoUsuario int auto_increment,
    nombreUsuario varchar(100) unique,
    contraseñaUsuario varchar(100),
    fechaRegistro datetime default current_timestamp,
    constraint pk_codigoUsuario primary key (codigoUsuario)
);

-- Tabla de palabras con pistas
Create table Palabras (
    codigoPalabra int auto_increment,
    textoPalabra varchar(100) unique,
    pistaUno varchar(250),
    pistaDos varchar(250),
    pistaTres varchar(250),
    imagenPalabra varchar(255),
    fechaCreacion datetime default current_timestamp,
    constraint pk_codigoPalabra primary key (codigoPalabra)
);

-- PROCEDIMIENTOS ALMACENADOS --

-- Usuarios --
Delimiter $$
Create procedure sp_AgregarUsuario (
    in nomUsu varchar(100), 
    in contUsu varchar(100))
begin 
    -- Verificar si el usuario ya existe
    if not exists (select 1 from Usuarios where nombreUsuario = nomUsu) then
        insert into Usuarios (nombreUsuario, contraseñaUsuario)
        values (nomUsu, contUsu);
        select 1 as resultado; -- Éxito
    else
        select 0 as resultado; -- Usuario ya existe
    end if;
end$$
Delimiter ;

Delimiter $$
Create procedure sp_MostrarUsuarios()
begin
    select codigoUsuario, nombreUsuario, fechaRegistro
    from Usuarios;
end$$
Delimiter ;

Delimiter $$
Create procedure sp_BuscarUsuarioPorId(in idUsu int)
begin
    select * from Usuarios where codigoUsuario = idUsu;
end$$
Delimiter ;

Delimiter $$
Create procedure sp_BuscarUsuarioPorNombre(in nomUsu varchar(100))
begin
    select * from Usuarios where nombreUsuario = nomUsu;
end$$
Delimiter ;

Delimiter $$
Create procedure sp_VerificarUsuario(in nomUsu varchar(100), in contUsu varchar(100))
begin
    select codigoUsuario, nombreUsuario
    from Usuarios 
    where nombreUsuario = nomUsu and contraseñaUsuario = contUsu;
end$$
Delimiter ;

Delimiter $$
Create procedure sp_ActualizarUsuario(in idUsu int, in nomUsu varchar(100), in contUsu varchar(100))
begin
    update Usuarios 
    set nombreUsuario = nomUsu, contraseñaUsuario = contUsu
    where codigoUsuario = idUsu;
end$$
Delimiter ;

Delimiter $$
Create procedure sp_EliminarUsuario(in idUsu int)
begin
    delete from Usuarios where codigoUsuario = idUsu;
end$$
Delimiter ;

-- Palabras --
Delimiter $$
Create procedure sp_AgregarPalabra (
    in texto varchar(100),
    in pista1 varchar(250),
    in pista2 varchar(250),
    in pista3 varchar(250),
    in imagen varchar(255))
begin 
    insert into Palabras (textoPalabra, pistaUno, pistaDos, pistaTres, imagenPalabra)
    values (texto, pista1, pista2, pista3, imagen);
end$$
Delimiter ;

Delimiter $$
Create procedure sp_MostrarPalabras()
begin
    select * from Palabras;
end$$
Delimiter ;

Delimiter $$
Create procedure sp_BuscarPalabraPorId(in idPal int)
begin
    select * from Palabras where codigoPalabra = idPal;
end$$
Delimiter ;

Delimiter $$
Create procedure sp_ObtenerPalabraAleatoria()
begin
    select * from Palabras order by rand() limit 1;
end$$
Delimiter ;

Delimiter $$
Create procedure sp_ActualizarPalabra(in idPal int, in texto varchar(100), in pista1 varchar(250), 
                                     in pista2 varchar(250), in pista3 varchar(250), in imagen varchar(255))
begin
    update Palabras 
    set textoPalabra = texto, pistaUno = pista1, pistaDos = pista2, pistaTres = pista3, imagenPalabra = imagen
    where codigoPalabra = idPal;
end$$
Delimiter ;

Delimiter $$
Create procedure sp_EliminarPalabra(in idPal int)
begin
    delete from Palabras where codigoPalabra = idPal;
end$$
Delimiter ;

-- INSERTAR DATOS DE EJEMPLO --

-- Insertar usuarios
call sp_AgregarUsuario('joaquin gonzales', 'jocaco7852$');
call sp_AgregarUsuario('diego mendez', 'diegoLuhe980');
call sp_AgregarUsuario('pablo santa cruz', 'sdjfjf4141');
call sp_AgregarUsuario('juan', 'contraseña123');
call sp_AgregarUsuario('mlara', 'contraseña456');

-- Insertar palabras con pistas e imágenes
call sp_AgregarPalabra('JAVASCRIPT', 'Lenguaje de programación interpretado', 'Se ejecuta principalmente en navegadores web', 'Creado por Brendan Eich en 1995', 'javascript.png');
call sp_AgregarPalabra('COMPUTADORA', 'Dispositivo electrónico que procesa datos', 'Puede ser de escritorio o portátil', 'Tiene componentes como CPU, RAM y disco duro', 'computadora.png');
call sp_AgregarPalabra('PROGRAMACION', 'Proceso de crear software', 'Implica escribir código en un lenguaje específico', 'Se usa para resolver problemas o automatizar tareas', 'programacion.png');
call sp_AgregarPalabra('TECLADO', 'Dispositivo de entrada de datos', 'Tiene teclas alfabéticas, numéricas y de función', 'Puede ser mecánico o de membrana', 'teclado.png');
call sp_AgregarPalabra('INTERNET', 'Red global de computadoras interconectadas', 'Permite acceso a la World Wide Web', 'Se originó como ARPANET en los años 60', 'internet.png');
call sp_AgregarPalabra('CENICIENTA', 'Personaje de cuento de hadas', 'Tiene una madrastra y hermanastras malvadas', 'Pierde su zapatilla de cristal', 'cenicienta.png');
call sp_AgregarPalabra('ANIMALISTA', 'Persona que defiende los derechos de los animales', 'Se opone al maltrato animal', 'Promueve el vegetarianismo/veganismo', 'animalista.png');
call sp_AgregarPalabra('OBSTACULO', 'Algo que impide el paso o progreso', 'Puede ser físico o abstracto', 'En deportes, prueba de velocidad con barreras', 'obstaculo.png');
call sp_AgregarPalabra('TECNOLOGIA', 'Aplicación del conocimiento científico', 'Incluye dispositivos electrónicos y digitales', 'Avances que mejoran la vida humana', 'tecnologia.png');
call sp_AgregarPalabra('EDUCACION', 'Proceso de enseñanza-aprendizaje', 'Se imparte en escuelas y universidades', 'Derecho fundamental de toda persona', 'educacion.png');

select * from Usuarios;