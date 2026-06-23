# CostaVista Logistics

Sistema de gestión de un centro de distribución, desarrollado en **Java** para el Trabajo Práctico Obligatorio (TPO) de **Programación 2**.

## Integrantes del grupo

- Juan Segundo Olazabal
- Mateo Schezzler

## Alternativa elegida

**Alternativa C — Centro Logístico de Distribución.**

CostaVista Logistics opera un centro de distribución sobre un catálogo de productos. El sistema permite localizar stock, despachar pedidos en orden, controlar el inventario crítico, registrar y deshacer movimientos, garantizar la unicidad de los códigos y encontrar el camino más corto entre ubicaciones del depósito.

## Estructuras de datos utilizadas

Todas las estructuras estan implementadas desde cero como se vieron en la catedra, y las fuimos modificando para que funcionen de forma generica, como con el "<T>"

| TDA | Instancia en el sistema | Objetivo que resuelve |
|---|---|---|
| Diccionario | `Diccionario<String, Producto>` | Localizar un producto por su código sin recorrer el catálogo. |
| Cola (FIFO) | `Cola<Pedido>` | Despachar pedidos en orden de llegada (línea de expedición). |
| Pila (LIFO) | `Pila<Movimiento>` | Registrar movimientos y deshacer el último (trazabilidad). |
| Conjunto | `Conjunto<String>` | Evitar productos con código repetido (unicidad). |
| Cola de Prioridad | `ColaPrioridad<Producto>` | Tener a mano el producto con menos stock (inventario crítico). |
| Grafo (matriz de adyacencia) | `GrafoMatrizAdyacencia<Ubicacion>` | Encontrar el camino más corto entre ubicaciones del depósito. |

### Clases de dominio

- `Producto` — código, nombre, stock, stock mínimo y ubicación.
- `Ubicacion` — id, pasillo, estantería y posición (es el vértice del grafo).
- `Pedido` — id, ítems y estado (`PENDIENTE` / `LISTO` / `DESPACHADO`).
- `Movimiento` — registra las operaciones que vamos haciendo en el inventario para poder revertirlas en caso de que quermos

### Clase de gestión

- `CentroLogistico` — clase que junta a todos los TDAs como atributos y reúne todas las operaciones del sistema, para que no sea tan engorroso hacer por cada movimiento en el main

## Organización del proyecto

El código está organizado en paquetes según la responsabilidad de cada componente:

- `model` — clases de dominio (`Producto`, `Ubicacion`, `Pedido`, `Movimiento`).
- `tda` — los TDAs genéricos y sus interfaces (Diccionario, Cola, Pila, Conjunto, Cola de Prioridad, Grafo).
- `service` — la clase de gestión `CentroLogistico`, que coordina los TDAs.
- `app` — la clase `Main` con los casos de prueba (se ejecuta como `app.Main`).

## Funcionalidades implementadas

1. **Localización de stock (Diccionario):** alta de productos, búsqueda por código y localización de su ubicación.
2. **Línea de expedición (Cola FIFO):** armar pedidos indicando producto y cantidad (validando que haya stock suficiente), encolarlos y despacharlos en orden de llegada; al despacharse, se descuenta del stock la cantidad pedida de cada ítem.
3. **Trazabilidad (Pila):** registrar cada cambio de stock y deshacer el último movimiento.
4. **Unicidad de códigos (Conjunto):** rechazar el alta de un producto con código repetido.
5. **Inventario crítico (Cola de Prioridad):** conocer en todo momento el producto con menos stock; se reordena solo al cambiar el stock.
6. **Conexión de ubicaciones (Grafo):** encontrar el camino más corto (menos saltos) entre dos ubicaciones, usando BFS.

Todos los errores se manejan con mensajes por consola (sin excepciones): código duplicado o inexistente, stock negativo, y estructuras vacías o llenas.

## Cómo ejecutar y probar

El proyecto se abre directamente en **IntelliJ IDEA** (o cualquier IDE de Java). Todo el programa se ejecuta desde el MAIN: basta con abrir `Main.java` y correr su método `main`. No se va a requerir librerias externas ni configuracion adicional

Desde el mismo main sobre la linea 9, se puede cambiar la capacidad que querramos para el centro logistico. Actualmente esta en 10, pero es posible cambiarlo

Al ejecutarse, **Main** corre una secuencia de pruebas que las fuimos organizando para que se entienda mejor, e imprime el resultado por la consola. El detalle de los casos de prueba que se pidio en la 3er entrega esta en [CASOS_DE_PRUEBA.md](CASOS_DE_PRUEBA.md)


## Link del repositorio

https://github.com/chinito-06/Logistica-CostaVista

## Actividades realizadas por cada integrante del grupo

| Integrante | Actividades |
|---|---|
| Juan Segundo Olazabal | TDAs Diccionario y Cola FIFO; TDA Pila (trazabilidad); TDA Grafo (camino más corto con BFS) y TDA Cola de Prioridad (inventario crítico). |
| Mateo Schezzler | Clases de dominio; TDA Conjunto (unicidad); clase de gestión `CentroLogistico`; clase `Main` de prueba; documentación (README, documento de entrega y casos de prueba). |

**Como acotacion**, para la escritura del README de este sistema nos hemos servido del modelo largo de lenguaje Claude, ya que nos permitió diseñarlos de una manera mejor y mas legible mediante el uso del formato "Markdown"