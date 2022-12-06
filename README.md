# inditex-test

Aplicaci&oacute;n [Spring Boot](http://projects.spring.io/spring-boot/) para prueba de Inditex.

## Requerimientos

Para construir y levantar la aplicacion se necesitan los siguientes componentes:

- [JDK 11](https://www.oracle.com/es/java/technologies/javase/jdk11-archive-downloads.html)
- [Maven 3](https://maven.apache.org)

## Levantar localmente la aplicaci&oacute;n

Existen varias maneras de levantar una aplicaci&oacute;n Spring Boot en un ambiente local. Una forma es ejecutar el m&eacute;todo `main` de la clase `com.inditex.msprices.MsPricesApplication` desde alg&uacute;n IDE como IDEA Intellij o Eclipse.

De manera alternativa, tambi&eacute;n es posible utilizar [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) de la siguiente manera:

```shell
mvn spring-boot:run
```

Configuraci&oacute;n a tener en cuenta antes de levantar la aplicaci&oacute;n:

En el archivo `resources/application.properties` se encuenta la siguiente propiedad:

```shell
prices.include.date.equals=true
```

En caso de estar en true, las fechas de aplicaci&oacute;n especificadas a la API se tomar&aacute;n en cuenta inclusive si calzan de manera exacta en los campos START_DATE y END_DATE.

## Endpoint

Una vez que la aplicaci&oacute;n haya levantado, a trav&eacute;s de Postman se puede utilizar el siguiente cURL:

```shell
curl --location --request POST 'http://localhost:8080/prices' \
--header 'Content-Type: application/json' \
--data-raw '{
"date": "2020-06-16-21.00.00",
"productId" : 35455,
"brandId": 1
}'
```

En donde `date` corresponde a la fecha de aplicaci&oacute;n, `productId` al identificador del producto y `brandId` al identificador de cadena.

* En caso de que se env&iacute;e un body vac&iacute;o, el endpoint devolver&aacute; todos los registros.
* En caso de que se env&iacute;e solo el productId, el endpoint devolver&aacute; todos los registros asociados a ese productid.
* En caso de que se env&iacute;e solo el brandId, el endpoint devolver&aacute; todos los registros asociados a ese brandId.
* En caso de que se env&iacute;e un productId y un brandId, el endpoint devolver&aacute; todos los registros asociados a ambos campos, y solo en este caso se buscar&aacute;n registros por medio de la fecha de aplicacion en caso de que esta haya sido proporcionada.

## Contexto aplicacion

En la base de datos de comercio electr&oacute;nico de la compañ&iacute;a disponemos de la tabla PRICES que refleja el precio final (pvp) y la tarifa que aplica a un producto de una cadena entre unas fechas determinadas. A continuaci&oacute;n se muestra los campos relevantes de la tabla:

* BRAND_ID: foreign key de la cadena del grupo (1 = ZARA).
* START_DATE , END_DATE: rango de fechas en el que aplica el precio tarifa indicado.
* PRICE_LIST: Identificador de la tarifa de precios aplicable.
* PRODUCT_ID: Identificador c&oacute;digo de producto.
* PRIORITY: Desambiguador de aplicaci&oacute;n de precios. Si dos tarifas coinciden en un rago de fechas se aplica la de mayor prioridad (mayor valor num&eacute;rico).
* PRICE: precio final de venta.
* CURR: iso de la moneda.

## Test

Los test unitarios de la clase `com.inditex.msprices.controller.PricesControllerTest` corresponden a test de integraci&oacute;n que validan los siguientes escenarios solicitados:

- Test 1: petici&oacute;n a las 10:00 del d&iacute;a 14 del producto 35455 para la brand 1 (ZARA)
- Test 2: petici&oacute;n a las 16:00 del d&iacute;a 14 del producto 35455 para la brand 1 (ZARA)
- Test 3: petici&oacute;n a las 21:00 del d&iacute;a 14 del producto 35455 para la brand 1 (ZARA)
- Test 4: petici&oacute;n a las 10:00 del d&iacute;a 15 del producto 35455 para la brand 1 (ZARA)
- Test 5: petici&oacute;n a las 21:00 del d&iacute;a 16 del producto 35455 para la brand 1 (ZARA)