# 🛒 Walmart Checkout System - Demo

Este proyecto simula un sistema de checkout dinámico que procesa carritos de compra, aplica reglas de negocio para promociones por SKU, gestiona costos de envío según zona geográfica y aplica descuentos por medio de pago.

---

## ⚙️ Instalación y Ejecución Local

Para levantar este proyecto sin configuraciones adicionales, siga estos pasos:

### 1. Requisitos del Sistema
* **Java JDK 17:** El proyecto utiliza características de Java 17.
* **Puerto 8080:** Asegúrese de que el puerto 8080 esté disponible en su máquina local.

### 2. Clonación y Acceso
Primero, clone el repositorio:
#```bash
git clone [https://github.com/joselaher-prog/walmart-cart-system-demo.git](https://github.com/joselaher-prog/walmart-cart-system-demo.git)
cd walmart-cart-system-demo

cd walmart-cart-system-demo

Ejecute el comando según su sistema operativo:

En Linux o macOS:
./mvnw spring-boot:run

En Windows (CMD o PowerShell):
mvnw.cmd spring-boot:run

Una vez que vea el mensaje Started Application in ... seconds en su terminal, abra su navegador e ingrese a:

URL: http://localhost:8080/ver-carrito

¿Cómo evaluar la funcionalidad?

para dar inicio mediante postman se debe enviar el cart inicial con el siguiente curl :

postman request POST 'http://localhost:8080/carrito' \
  --header 'Content-Type: application/json' \
  --body '{
    "cartId": "cart-1001",
    "items": [
        {
            "sku": "p-001",
            "quantity": 1,
            "name": "Arroz kilo",
            "price": 12000,
            "currency": "CL",
            "taxes": 19
        },
        {
            "sku": "p-010",
            "quantity": 2,
            "name": "Fideos kilo",
            "price": 1000,
            "currency": "CL",
            "taxes": 19
        },
        {
            "sku": "p-003",
            "quantity": 1,
            "name": "Aceite 1 litro",
            "price": 300,
            "currency": "CL",
            "taxes": 19
        },
        {
            "sku": "p-030",
            "quantity": 1,
            "name": "Detergente 1 litro",
            "price": 300,
            "currency": "CL",
            "taxes": 19
        }
    ],
    "paymentMethod": "LIDER_BCI",
    "shippingAddress": {
        "street": "Av. Falsa 123",
        "city": "Ciudad",
        "zoneId": "zone-1"
    }
}'

En este json se pueden agregar mas productos , modificar su cantidad, precio del producto. Ademas de modificar la dirección ciudad y zoneId 
ya que esta ultima según lo que desarrolle estará asociado el cargo por envío de la siguiente forma:

zone-1: Valor del envío 1000 pesos
zone-2: Valor del envío 2000 pesos
zone-3: Valor del envío 3000 pesos

Si la zona no coincide con ninguna de las anteriores el precio por defecto sera 0
De igual forma agregue un descuento por compras superiores a 20.000 que el costo del envío es gratis o sin costo

Para el campo método de pago pueden ser los siguientes ya que cada uno de ellos tiene un porcentaje de descuento diferente:

LIDER_BCI: tiene un 30% de descuento
DEBIT: tiene un 20% de descuento
CREDIT_CARD: tiene un 10% de descuento
CASH: tiene un 5% de descuento
Y cualquier otro medio de pago tiene 0% de descuento.

Con este json se cargara primeramente el carro con los datos definidos anteriormente:
Retiro Pickup o Despacho a Domicilio: Esto serán los datos ingresados en el json de entrada de la sección shippingAddress, en donde comente
anteriormente el costo del envío estará asociado al campo zoneId.

Abra una sección Resumen de pedido en donde se enumeran los productos cargados con las siguientes opciones:
Mostrar detalle, en donde se desplegara una grilla con el detalle de cada item cargado con su descripción, cantidad y precio.
Estará la opción de aumentar o disminuir la cantidad del producto de la grilla.


Para el flujo de promociones se desarrollo lo siguiente:
Se definieron dos promociones (sin rango de tiempo para su caducidad):
1.- Para sku p-010 que consiste en llevar 4 productos y solo pagar 3.
2.- Para sku p-030 que consiste en llevar dos al precio 400 pesos.

Cada vez que se aplican promociones se ven reflejado en la linea de "Descuento Promociones" en donde muestra el valor del descuento

En la linea "Total estimado" es el valor de los productos menos el "Descuento Promociones".

También agregue la funcionalidad de generar puntos por las ventas , la lógica que implemente es por cada 100 pesos de compra se obtienen 
5 pesos de club lider.

Y por ultimo en el botón "Pago con <BCI Lider - Tarjeta de Credito - Efectivo>" mostrara un porcentaje de descuento adicional si se cancela
con ese medio de pago.

Al presionar este botón se generar una boleta con los datos de la venta y sus detalle de descuento o modo de simulación y el servicio retorna
un json con toda la información de lo generado , esto para actualizar el carro o seguir con el flujo de venta, el json se muestra en el log y tiene la siguiente estructura:

DEBUG JSON RECIBIDO:
{
  "cartId" : "cart-1001",
  "costoEnvio" : 1000.0,
  "descuentoAplicado" : 0.0,
  "items" : [ {
    "name" : "Arroz kilo",
    "price" : 2000.0,
    "quantity" : 1,
    "sku" : "p-001"
  }, {
    "name" : "Fideos kilo",
    "price" : 1000.0,
    "quantity" : 4,
    "sku" : "p-010"
  }, {
    "name" : "Aceite 1 litro",
    "price" : 300.0,
    "quantity" : 3,
    "sku" : "p-003"
  }, {
    "name" : "Detergente 1 litro",
    "price" : 300.0,
    "quantity" : 2,
    "sku" : "p-030"
  } ],
  "paymentMethod" : "Tarjeta Lider BCI",
  "shippingAddress" : null,
  "totalConDescuento" : 5110.0,
  "totalEstimado" : 7300.0
}


La solución se planteo de la siguiente forma , se carga un carrito base al ckechout en donde este se puede modificar sus cantidades, a lo cual por cada modificación se va a métodos de calculo de promociones, descuentos, calculo de envío y calculo de puntos lider. Si bien todo lo anterior deberían estar dado por servicios externos al checkout para este ejercicio y para su implementación se crearon módulos independientes para que la modificación de ellos sea lo mas rápida y menos invasiva posible con la posibilidad de agregar nuevas promociones , nuevas zonas con valores de despacho, nuevos métodos de pago con descuento y nueva lógica  calculo de puntos lider.
Lo anterior con la finalidad de hacerlo lo mas extendible posible para nuevas lógicas de negocio y a cara del cliente mostrar toda la información posible sin recargar mucho la pagina de información para la tranquilidad de cliente al momento del pago.
