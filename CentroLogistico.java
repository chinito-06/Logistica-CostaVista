// Clase de gestion del centro de distribucion.
// Guarda las estructuras de datos como atributos y reune las operaciones
// del sistema en metodos. Por ahora cubre dos objetivos:
//   1) Localizacion de stock  -> usa el Diccionario.
//   2) Linea de expedicion    -> usa la Cola FIFO.
public class CentroLogistico {
    private Diccionario<String, Producto> productos;
    private Cola<Pedido> lineaExpedicion;

    public CentroLogistico(int capacidad) {
        this.productos = new Diccionario<String, Producto>(capacidad);
        this.lineaExpedicion = new Cola<Pedido>(capacidad);
    }

    // ----- Objetivo 1: Localizacion de stock (Diccionario) -----

    // Da de alta un producto usando su codigo como clave.
    // El Diccionario ya rechaza un codigo repetido, asi que no se duplica.
    public void agregarProducto(String codigo, Producto producto) {
        boolean agregado = productos.insertar(codigo, producto);
        if (agregado) {
            System.out.println("Producto agregado: " + producto);
        }
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
}
