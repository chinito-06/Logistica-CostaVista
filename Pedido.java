public class Pedido {
    private String id;
    private Producto[] items;
    private int cantidadItems;
    private String estado;

    public Pedido(String id, int capacidadItems) {
        this.id = id;
        this.items = new Producto[capacidadItems];
        this.cantidadItems = 0;
        this.estado = "PENDIENTE";
    }

    public void agregarItem(Producto p) {
        if (cantidadItems == items.length) {
            System.out.println("Error: el pedido " + id + " esta lleno, no se puede agregar el item.");
            return;
        }
        items[cantidadItems] = p;
        cantidadItems++;
    }

    public String obtenerId() {
        return id;
    }

    public Producto[] obtenerItems() {
        return items;
    }

    public int obtenerCantidadItems() {
        return cantidadItems;
    }

    public String obtenerEstado() {
        return estado;
    }

    public void establecerEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public String toString() {
        return "Pedido[" + id + ": estado " + estado + ", items " + cantidadItems + "]";
    }
}
