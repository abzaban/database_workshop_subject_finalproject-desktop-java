select * from SENSORES

create login administrador with password = 'Crianza9'
create user administrador for login administrador with default_schema = dbo
Grant Select, Update, Insert, delete on Schema::dbo to administrador

select * from [SESIONES].[FUNCIONES APP]
select * from [SESIONES].USUARIOS
insert into SESIONES.PERMISOS values (4, 1), (4, 2), (4, 3), (4, 4)
