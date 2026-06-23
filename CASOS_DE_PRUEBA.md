# Casos de prueba — CostaVista Logistics

Todos los casos se ejecutan corriendo la clase `Main` desde el editor de codigo, sea intellij o Visual Studio. Para cada uno se indica funcionalidad evaluada, datos de entrada, resultado esperdo, resultado obtenido y observaciones.

## 1. Localización de stock (Diccionario)

| # | Funcionalidad evaluada | Datos de entrada | Resultado esperado | Resultado obtenido | Observaciones |
|---|---|---|---|---|---|
| 1.1 | Alta de producto | `agregarProducto("P001", Lavandina, stock 50)` | El producto se agrega al catálogo | `Producto agregado: Producto[P001: Lavandina 1L, stock 50]` | OK |
| 1.2 | Búsqueda por código existente | `buscarProducto("P002")` | Devuelve el producto P002 | `Producto[P002: Jabon en polvo, stock 8]` | OK |
| 1.3 | Localización de ubicación | `buscarUbicacion("P003")` | Devuelve la ubicación de P003 | `Ubicacion[UB-03: pasillo C, estanteria 1, posicion 1]` | OK |
| 1.4 | Búsqueda de código inexistente | `buscarUbicacion("P999")` | Mensaje de error y `null` | `Error: la clave no existe ...` + `null` | Sin excepciones |

## 2. Unicidad de códigos (Conjunto)

| # | Funcionalidad evaluada | Datos de entrada | Resultado esperado | Resultado obtenido | Observaciones |
|---|---|---|---|---|---|
| 2.1 | Alta con código duplicado | `agregarProducto("P001", ...)` (P001 ya existe) | Se rechaza, no se agrega | `Error: codigo duplicado P001 --> no se agrego el producto` | El Conjunto garantiza la unicidad |
| 2.2 | Códigos usados | `mostrarCodigosUsados()` | `{ P001, P002, P003 }` | `Datos del Conjunto: { P001, P002, P003 }` | OK |

## 3. Línea de expedición (Cola FIFO)

| # | Funcionalidad evaluada | Datos de entrada | Resultado esperado | Resultado obtenido | Observaciones |
|---|---|---|---|---|---|
| 3.1 | Despachar con cola vacía | `despacharProximoPedido()` (sin pedidos) | Mensaje de error y `null` | `Error: cola vacia ...` + `null` | Caso límite |
| 3.2 | Agregar ítem con stock suficiente | `ped1.agregarItem(P001, 5)`, `ped1.agregarItem(P002, 2)`, `ped2.agregarItem(P003, 10)` | Se agregan al pedido | PED-1 con 2 ítems, PED-2 con 1 ítem | `agregarItem(producto, cantidad)` valida el stock |
| 3.3 | Agregar ítem con stock insuficiente | `ped2.agregarItem(P002, 100)` (hay 8) | Se rechaza, no se agrega | `Error: stock insuficiente de P002 (se pidieron 100, hay 8) ...` | Validación al armar el pedido |
| 3.4 | Encolar y despachar en orden (FIFO) | `marcarPedidoListo` + `despacharProximoPedido()` x2 | Sale primero PED-1, después PED-2 | `Despachado 1ro -> PED-1`, `Despachado 2do -> PED-2` | Respeta el orden de llegada |
| 3.5 | Descuento de stock al despachar | Despachar PED-1 (5 de P001, 2 de P002) y PED-2 (10 de P003) | Baja la cantidad pedida: P001 50→45, P002 8→6, P003 202→192 | `P001: 45 | P002: 6 | P003: 192` | Usa `actualizarStock(-cantidad)`; P003 estaba en 202 por la prueba 5.3 |

## 4. Trazabilidad (Pila)

| # | Funcionalidad evaluada | Datos de entrada | Resultado esperado | Resultado obtenido | Observaciones |
|---|---|---|---|---|---|
| 4.1 | Actualizar stock (baja válida) | `actualizarStock("P002", -3)` (stock 8) | Stock pasa a 5 | `Stock actualizado: ... stock 5` | OK |
| 4.2 | Actualizar stock (alta) | `actualizarStock("P002", 10)` (stock 5) | Stock pasa a 15 | `Stock actualizado: ... stock 15` | OK |
| 4.3 | Baja que deja stock negativo | `actualizarStock("P002", -1000)` | Se rechaza, no se aplica | `Error: el stock no puede quedar negativo ...` | Validación de caso límite |
| 4.4 | Deshacer último movimiento | `deshacerUltimoMovimiento()` | Stock vuelve de 15 a 5 | `Movimiento deshecho: ... stock 5` | LIFO |
| 4.5 | Deshacer otro movimiento | `deshacerUltimoMovimiento()` | Stock vuelve de 5 a 8 | `Movimiento deshecho: ... stock 8` | Orden inverso correcto |
| 4.6 | Deshacer con pila vacía | `deshacerUltimoMovimiento()` | Mensaje de error | `Error: pila vacia ...` | Caso límite |

## 5. Inventario crítico (Cola de Prioridad)

| # | Funcionalidad evaluada | Datos de entrada | Resultado esperado | Resultado obtenido | Observaciones |
|---|---|---|---|---|---|
| 5.1 | Producto más crítico | `productoMasCritico()` (stocks: P002=8, P001=50, P003=120) | El de menos stock: P002 | `Producto[P002: Jabon en polvo, stock 8]` | Menos stock = más urgente |
| 5.2 | Reordenado al bajar stock | `actualizarStock("P003", -118)` (P003: 120→2) | P003 pasa a ser el más crítico | `Producto mas critico ahora: ... P003 ... stock 2` | Se reordena la cola |
| 5.3 | Reordenado al reponer stock | `actualizarStock("P003", 200)` (P003: 2→202) | P002 vuelve a ser el más crítico | `Producto mas critico ahora: ... P002 ... stock 8` | Reordena en ambos sentidos |

## 6. Conexión de ubicaciones (Grafo)

Mapa de prueba (aristas no dirigidas, peso 1):
`UB-01–UB-02`, `UB-02–UB-03`, `UB-03–UB-05`, `UB-01–UB-04`, `UB-04–UB-05`.

| # | Funcionalidad evaluada | Datos de entrada | Resultado esperado | Resultado obtenido | Observaciones |
|---|---|---|---|---|---|
| 6.1 | Camino más corto | `encontrarCamino(UB-01, UB-05)` | 2 saltos: UB-01 → UB-04 → UB-05 | `Camino mas corto (2 saltos): UB-01 -> UB-04 -> UB-05` | BFS elige 2 saltos en vez del de 3 (por UB-02–UB-03) |
| 6.2 | Camino más corto | `encontrarCamino(UB-01, UB-03)` | 2 saltos: UB-01 → UB-02 → UB-03 | `Camino mas corto (2 saltos): UB-01 -> UB-02 -> UB-03` | OK |