# CostaVista Logistics

Sistema de gestión de un centro de distribución, desarrollado en **Java** para el Trabajo Práctico Obligatorio (TPO) de **Programación 2**.

## Integrantes del grupo

- Juan Segundo Olazabal
- Mateo Schezzler

## Alternativa elegida

**Alternativa C — Centro Logístico de Distribución.**

CostaVista Logistics opera un centro de distribución sobre un catálogo de miles de productos. El sistema permite localizar stock, gestionar la línea de despacho de pedidos, controlar el inventario crítico, registrar la trazabilidad de los movimientos y garantizar la unicidad de los códigos de producto.

## Estructuras de datos utilizadas


| Estructura | TDA | Rol en el sistema |
|---|---|---|
| Diccionario | `Diccionario<String, Producto>` | Localizar un producto por su código sin recorrer el catálogo. |
| Cola (FIFO) | `Cola<Pedido>` | Despachar pedidos en orden de llegada (línea de expedición). |
| Cola de Prioridad | `ColaPrioridad<Producto>` | Tener a mano el producto con menos stock (inventario crítico). |
| Pila (LIFO) | `Pila<Movimiento>` | Registrar movimientos y poder deshacer el último (trazabilidad). |
| Conjunto | `Conjunto<String>` | Evitar productos con código repetido (unicidad). |
| Grafo | `GrafoMatrizAdyacencia<Ubicacion>` | Conexión de ubicaciones del depósito *(etapa final, no incluida en esta entrega).* |

### Clases de dominio

- `Producto` — código, nombre, stock, stock mínimo y ubicación.
- `Ubicacion` — id, pasillo, estantería y posición (será el vértice del grafo).
- `Pedido` — id, ítems y estado (`PENDIENTE` / `LISTO` / `DESPACHADO`).
- `Movimiento` — registra una operación sobre el inventario para poder revertirla.

## Funcionalidades implementadas en esta segunda etapa

Se cumplen dos objetivos especificos, con toda su funcionalidad asociada:

### 1. Localización de stock (Diccionario)

- `agregarProducto(...)` — alta de un producto en el catálogo (con control de código duplicado).
- `buscarUbicacion(codigo)` — devuelve la ubicación de un producto a partir de su código, sin recorrer todo el catálogo.
- Manejo de errores: código inexistente y código duplicado se informan por consola sin cortar la ejecución.

### 2. Línea de expedición (Cola FIFO)

- `marcarPedidoListo(pedido)` — encola un pedido listo para despachar.
- `despacharProximoPedido()` — despacha el pedido más antiguo (orden de llegada / FIFO).
- Manejo de errores: despachar con la cola vacía se informa por consola.

## Ejecución demostrable

El proyecto incluye una clase `Main` (en [`src/`](src/)) que demuestra el funcionamiento de los módulos implementados llamando directamente a los métodos de `CentroLogistico`.

## Actividades realizadas por cada integrante

| Integrante | Actividades |
|---|---|
| Juan Segundo Olazabal | TDA Diccionario (`Dato`, `IDiccionario`, `Diccionario`) y funcionalidad de localización de stock; TDA Cola FIFO (`ICola`, `Cola`) y funcionalidad de línea de expedición. |
| Mateo Schezzler | Clases de dominio (`Producto`, `Ubicacion`, `Pedido`, `Movimiento`); clase de gestión `CentroLogistico`; clase `Main` de prueba; documentación (README). |

## Link del repositorio

https://github.com/chinito-06/Logistica-CostaVista
