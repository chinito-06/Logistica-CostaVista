// Clase de gestion del centro de distribucion.
// Guarda las estructuras de datos como atributos y reune las operaciones
// del sistema en metodos. Por ahora cubre tres objetivos:
//   1) Localizacion de stock  -> usa el Diccionario.
//   2) Linea de expedicion    -> usa la Cola FIFO.
//   3) Trazabilidad           -> usa la Pila (registrar y deshacer movimientos).
public class CentroLogistico {
    private Diccionario<String, Producto> productos;
    private Cola<Pedido> lineaExpedicion;
    private Conjunto<String> codigosUsados;
    private Pila<Movimiento> trazabilidad;

    public CentroLogistico(int capacidad) {
        this.productos = new Diccionario<String, Producto>(capacidad);
        this.lineaExpedicion = new Cola<Pedido>(capacidad);
        this.codigosUsados = new Conjunto<String>(capacidad);
        this.trazabilidad = new Pila<Movimiento>(capacidad);
    }

    // ----- Objetivo 1: Localizacion de stock (Diccionario) -----

    // Da de alta un producto. El Conjunto de codigos usados garantiza la
    // unicidad: si el codigo ya esta registrado, no se agrega.
    public void agregarProducto(String codigo, Producto producto) {
        if (codigosUsados.pertenece(codigo)) {
            System.out.println("Error: codigo duplicado " + codigo + " --> no se agrego el producto");
            return;
        }
        codigosUsados.insertar(codigo);
        productos.insertar(codigo, producto);
        System.out.println("Producto agregado: " + producto);
    }

    // Busca un producto por su codigo sin recorrer todo el catalogo.
    public Producto buscarProducto(String codigo) {
        return productos.recuperarValor(codigo);
    }

    // Devuelve la ubicacion de un producto a partir de su codigo.
    public Ubicacion buscarUbicacion(String codigo) {
        Producto p = productos.recuperarValor(codigo);
        if (p == null) {
            return null;
        }
        return p.obtenerUbicacion();
    }

    public void mostrarCatalogo() {
        productos.mostrar();
    }

    public void mostrarCodigosUsados() {
        codigosUsados.mostrar();
    }

    // ----- Objetivo 2: Linea de expedicion (Cola FIFO) -----

    // Marca un pedido como LISTO y lo encola para despachar.
    public void marcarPedidoListo(Pedido pedido) {
        if (pedido == null) {
            System.out.println("Error: pedido nulo --> no se encolo");
            return;
        }
        pedido.establecerEstado("LISTO");
        lineaExpedicion.encolar(pedido);
    }

    // Despacha el pedido mas antiguo de la cola (orden de llegada, FIFO).
    public Pedido despacharProximoPedido() {
        Pedido p = lineaExpedicion.desencolar();
        if (p == null) {
            return null;
        }
        p.establecerEstado("DESPACHADO");
        return p;
    }

    public void mostrarLineaExpedicion() {
        lineaExpedicion.mostrar();
    }

    // ----- Objetivo 3: Trazabilidad (Pila) -----

    // Cambia el stock de un producto. Antes de aplicar el cambio, registra un
    // Movimiento en la pila para poder deshacerlo. La cantidad puede ser
    // negativa (baja); no se permite que el stock quede por debajo de 0.
    // NOTA: el reordenado del inventario critico (Cola de Prioridad) se
    // agregara cuando se implemente ese TDA.
    public void actualizarStock(String codigo, int cantidad) {
        Producto p = productos.recuperarValor(codigo);
        if (p == null) {
            return;
        }
        if (p.obtenerStock() + cantidad < 0) {
            System.out.println("Error: el stock no puede quedar negativo --> no se aplico el cambio");
            return;
        }
        trazabilidad.apilar(new Movimiento("ACTUALIZAR_STOCK", codigo, p.obtenerStock()));
        p.establecerStock(p.obtenerStock() + cantidad);
        System.out.println("Stock actualizado: " + p);
    }

    // Deshace el ultimo movimiento registrado, restaurando el stock previo.
    public void deshacerUltimoMovimiento() {
        Movimiento m = trazabilidad.desapilar();
        if (m == null) {
            return;
        }
        Producto p = productos.recuperarValor(m.obtenerCodigoProducto());
        if (p == null) {
            return;
        }
        p.establecerStock(m.obtenerStockPrevio());
        System.out.println("Movimiento deshecho: " + p);
    }

    public void mostrarTrazabilidad() {
        trazabilidad.mostrar();
    }
}