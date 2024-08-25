PROYECTO EN GITHUB:
https://github.com/josephStart/cl-jose-huenul-springboot-globallogic-app.git

PRE-REQUISITOS
- Java 11
- Gradle 7.4

PARA LEVANTAR EL PROYECTO:

1. En la carpeta del proyecto ejecutar en una CLI los siguientes comandos: ./gradlew clean && ./gradlew build
2. En la misma carpeta, en la CLI se debe ejecutar después: ./gradlew bootRun

- ACCEDER A LA BASE DE DATOS EN MEMORIA H2: http://localhost:7001/h2-console
	- CONFIGURACION LOGIN:

		Saved Settings: Generic H2(Embedded)
		Setting Name: Generic H2(Embedded)
		
		Driver Class: org.h2.Driver
		JDBC URL: jdbc:h2:mem:UserInfoDB
		User Name: sa
		Password: DBAdmin
		
- PARA CREAR UN NUEVO USUARIO Y MOSTRAR LA INFORMACION(METODO POST):
http://localhost:7001/sign-up

CUERPO DE ENTRADA DE EJEMPLO:
{
    "name": "test",
    "email": "test@test.coam",
    "password": "Tests2024",
    "phones": [
        {
            "number": "1234567",
            "cityCode": "1",
            "countryCode": "57"
        },
        {
            "number": "56789",
            "cityCode": "2",
            "countryCode": "31"
        },
        {
            "number": "98765",
            "cityCode": "10",
            "countryCode": "67"
        },
        {
            "number": "5432",
            "cityCode": "5",
            "countryCode": "45"
        }
    ]
}

- PARA EJECUTAR EL LOGIN(METODO GET):
http://localhost:7001/login
* Para este endpoint se debe enviar un Bearer token