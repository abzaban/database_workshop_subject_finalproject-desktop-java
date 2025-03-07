create database CRIANZA
go
use CRIANZA

create table CLASIFICACIONES (
    clasi_id int,
    clasi_descripcion varchar(200) not null,
    clasi_nombre varchar(20) not null,
    constraint pk_clasificaciones primary key (clasi_id)
)
alter table CLASIFICACIONES add constraint uni_clasi unique (clasi_nombre)

create table DIETAS (
    dieta_id int,
    dieta_descripcion varchar(100) not null,
    dieta_nombre varchar(20) not null,
    constraint pk_dietas primary key (dieta_id)
)
alter table DIETAS add constraint uni_dietas unique (dieta_nombre)

create table ESTADOS (
    edo_id int,
    edo_nombre varchar(35) not null,
    constraint pk_estados primary key (edo_id)
)
alter table ESTADOS add constraint uni_edos unique (edo_nombre)

create table [COLORES MUSCULOS] (
    colorMusc_id int,
    colorMusc_nombre varchar(30) not null,
    constraint pk_colorMusculos primary key (colorMusc_id)
)
alter table [COLORES MUSCULOS] add constraint uni_coloresMusculos unique (colorMusc_nombre)

create table CRIAS (
    cr_id int,
    dieta_id int not null,
    clasi_id int not null,
    edo_id int not null,
    colorMusc_id int not null,
    cr_estatus varchar(1) not null,
    cr_peso decimal(4,2) not null,
    cr_cantidadGrasa decimal(2,2) not null,
    cr_fechaIngreso date not null,
    cr_noProceso tinyint not null,
    constraint pk_crias primary key (cr_id),
    constraint fk_criasDIE foreign key (dieta_id) references DIETAS (dieta_id),
    constraint fk_criasCLASI foreign key (clasi_id) references CLASIFICACIONES (clasi_id),
    constraint fk_criasEDO foreign key (edo_id) references ESTADOS (edo_id),
    constraint fk_criasCOLOR foreign key (colorMusc_id) references [COLORES MUSCULOS] (colorMusc_id)
)

create table MEDICAMENTOS (
    med_id int,
    med_descripcion varchar(150) not null,
    med_nombre varchar(20) not null,
    constraint pk_medicamentos primary key (med_id)
)
alter table MEDICAMENTOS add constraint uni_meds unique (med_nombre)

create table CUARENTENAS (
    cr_id int,
    cua_noConsulta int,
    med_id int not null,
    cua_fechaIngreso date not null,
    cua_fechaSalida date,
    constraint pk_cuarentena primary key (cr_id, cua_noConsulta),
    constraint fk_cuarentenaCRIA foreign key (cr_id) references CRIAS (cr_id),
    constraint fk_cuarentenaMED foreign key (med_id) references MEDICAMENTOS (med_id)
)

create table SENSORES (
    sen_id int primary key identity,
    cr_id int not null,
    constraint fk_sensorCRIA foreign key (cr_id) references CRIAS (cr_id)
)

create table [SENSORES LOG] (
    [sen_id] [int] NOT NULL,
	[slg_noRenglon] [int] NOT NULL,
	[slg_temperatura] [int] NOT NULL,
	[slg_ritmoCardiaco] [int] NOT NULL,
	[slg_fecha] [datetime] NOT NULL
    constraint pk_sensoresLog primary key (sen_id, slg_noRenglon),
    constraint fk_sensoresLogSEN foreign key (sen_id) references SENSORES (sen_id)
)

CREATE TABLE [SESIONES].[USUARIOS](
	[user_id] [int] NOT NULL,
	[user_nombre] [varchar](50) NULL
)

CREATE TABLE [SESIONES].[PERMISOS](
	[user_id] [int] NOT NULL,
	[fun_id] [int] NOT NULL
)

CREATE TABLE [SESIONES].[FUNCIONES APP](
	[fun_id] [int] NOT NULL,
	[fun_nombre] [varchar](50) NULL
)

insert into DIETAS values
(1, 'Conformada por alimentos saludables', 'Saludable'),
(2, 'Conformada por alimentos que harán que la cria mejore su estado de salud', 'Curativa')

insert into CLASIFICACIONES values
(1, 'Peso > 65 kg && Peso <= 75kg, Color de músculo = Rosa, Cantidad de grasa = 0.10%', 'Grasa de Cobertura 2'),
(2, 'Peso > 30 kg && Peso <= 65kg, Color de músculo = Café ó Rojo oscuro ó Rojo, Cantidad de grasa > 0.20%', 'Grasa de Cobertura 1')

insert into ESTADOS values
(1, 'Sinaloa'),
(2, 'Jalisco'),
(3, 'Chihuahua'),
(4, 'Sonora')

insert into MEDICAMENTOS values
(1, 'Es un antiparasitario', 'Actuol 22%'),
(2, 'Inhibidor del desarrollo de las garrapatas de acción sistématica para el control de garrapatas en bovinos', 'Acatak Pour On')

insert into [COLORES MUSCULOS] values
(1, 'Rojo'),
(2, 'Marrón Rojizo'),
(3, 'Belgian Blue'),
(4, 'Rosa')

go
create procedure SPInsercionCria
    @cr_peso decimal(4,2),
    @cr_cantidadGrasa decimal(2,2),
    @cr_colorMusculo varchar(20),
    @cr_id int,
    @edo_nombre varchar(30),
    @cr_fechaIngreso date
as
    begin try
    begin tran
        declare @clasi_id int, @edo_id int, @colorMusc_id int
        if @cr_cantidadGrasa = 0.1 and @cr_colorMusculo = 'Rojo' and @cr_peso > 65 and @cr_peso <= 75 begin
            set @clasi_id = 1
        end else
            set @clasi_id = 2
        set @edo_id = (select edo_id from ESTADOS where edo_nombre = @edo_nombre)
        set @colorMusc_id = (select colorMusc_id from [COLORES MUSCULOS] where colorMusc_nombre = @cr_colorMusculo)
        insert into CRIAS values (@cr_id, 1, @clasi_id, @edo_id, @colorMusc_id, 'V', @cr_peso, @cr_cantidadGrasa, @cr_fechaIngreso, 1)
        if @clasi_id = 1
            insert into SENSORES values (@cr_id)
    commit tran
    end try
    begin catch
        return 
        rollback tran
    end catch

go
create procedure SPEnfermaCria
    @cr_id int,
    @med_nombre varchar(20),
    @dieta_nombre varchar(20),
    @cua_fechaIngreso date
as
    declare @med_id int, @dieta_id int, @noConsulta int, @diasEnferma int
    set @med_id = (select med_id from MEDICAMENTOS where med_nombre = @med_nombre)
    set @dieta_id = (select dieta_id from DIETAS where dieta_nombre = @dieta_nombre)
    set @noConsulta = (select count(*) from CUARENTENAS where cr_id = @cr_id) + 1
    set @diasEnferma = DATEDIFF(dd, @cua_fechaIngreso, getdate())
    update CRIAS set dieta_id = @dieta_id, cr_estatus = 'E' where cr_id = @cr_id
    insert into CUARENTENAS values (@cr_id, @noConsulta, @med_id, @diasEnferma, @cua_fechaIngreso, null)

go
create procedure SPObtenDatosCriaEnferma
as
    select C.cr_id, clasi_nombre, edo_nombre, dieta_nombre, med_nombre, cua_diasEnfermo from (select cr_id, clasi_id, edo_id, dieta_id from CRIAS where cr_estatus = 'E') C
    inner join ESTADOS E on (C.edo_id = E.edo_id)
    inner join DIETAS D on (C.dieta_id = D.dieta_id)
    inner join CLASIFICACIONES CL on (C.clasi_id = CL.clasi_id)
    inner join CUARENTENAS CU on (C.cr_id = CU.cr_id)
    inner join MEDICAMENTOS M on (CU.med_id = M.med_id)

go
create procedure SPObtenCriasListasParaProcesoDos
as
    select cr_id, clasi_nombre from
    (select cr_id, clasi_id from CRIAS where DATEADD(Month, 5, cr_fechaIngreso) <= getdate() and cr_estatus = 'V' and cr_noProceso = 1)A 
    inner join CLASIFICACIONES B on A.clasi_id = B.clasi_id

go
create procedure SPAvanzaCriaProcesoDos
    @cr_id int
as
    update CRIAS set cr_noProceso = 2 from CRIAS where cr_id = @cr_id

go
create procedure SPObtenNombreEstados
as
    select edo_nombre from ESTADOS

go
create procedure SPObtenIDCriasPropensasEnfermar
as
    select S.cr_id from SENSORES S
    inner join [SENSORES LOG] SL on S.sen_id = SL.sen_id
    inner join CRIAS C on C.cr_id = S.cr_id
    where SL.slg_ritmoCardiaco >= 70 and SL.slg_temperatura >= 40 and C.cr_estatus = 'V' and
    SL.slg_noRenglon = (select count(*) from [SENSORES LOG] SLAUX where SL.sen_id = SLAUX.sen_id)

go
create procedure SPObtenNombreMedicamentos
as
    select med_nombre from MEDICAMENTOS

go
create procedure SPObtenNombreDietas
as
    select dieta_nombre from DIETAS

go
create procedure SPObtenColoresDeMusculos
as
    select colorMusc_nombre from [COLORES MUSCULOS]

go
create procedure SPCuraCria
    @cr_id int
as
    update CRIAS set cr_estatus = 'V', dieta_id = 1 where cr_id = @cr_id
    update CUARENTENAS set cua_fechaSalida = getdate() where cr_id = @cr_id and cua_fechaSalida is null

go
create procedure SPActualizaCriasDiasEnferma
as
    update CUARENTENAS set cua_diasEnfermo = DATEDIFF(dd, cua_fechaIngreso, getdate()) where cua_fechaSalida is null

go
create procedure SPActualizaSensorLog
as    
    insert into [SENSORES LOG]
    select sen_id, (select count(*) from [SENSORES LOG] SL where SL.sen_id = S.sen_id) + 1, (select round(((70 - 30) * rand() + 30), 0)), (select round(((100 - 60) * rand() + 60), 0)) from (select cr_id from CRIAS where cr_estatus = 'V' and clasi_id = 1) A
    inner join SENSORES S on A.cr_id = S.cr_id

go
create procedure SPObtenIDCriasValidasSacrificar
as
    select CUA.cr_id from CUARENTENAS CUA 
    inner join CRIAS C on CUA.cr_id = C.cr_id
    where cua_diasEnfermo > 39 and cua_fechaSalida is null and C.cr_estatus = 'E'

go
create procedure SPSacrificaCria
    @cr_id int
as
    update CUARENTENAS set cua_fechaSalida = getdate() where cr_id = @cr_id
    update CRIAS set cr_estatus = 'M' where cr_id = @cr_id
