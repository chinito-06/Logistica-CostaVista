public class Main {
    public static void main(String[] args) {
        System.out.println("===== CostaVista Logistics =====");
        CentroLogistico centro = new CentroLogistico(10);

        // Ubicaciones del deposito
        Ubicacion u1 = new Ubicacion("UB-01", "A", 1, 3);
        Ubicacion u2 = new Ubicacion("UB-02", "B", 2, 5);
        Ubicacion u3 = new Ubicacion("UB-03", "C", 1, 1);

        // Productos
        Producto p1 = new Producto("P001", "Lavandina 1L", 50, 10, u1);
        Producto p2 = new Producto("P002", "Jabon en polvo", 8, 15, u2);
        Producto p3 = new Producto("P003", "Esponja x3", 120, 20, u3);

        // ===== Objetivo 1: Localizacion de stock (Diccionario) =====
        System.out.println("\n----- Alta de productos -----");
        centro.agregarProducto("P001", p1);
        centro.agregarProducto("P002", p2);
        centro.agregarProducto("P003", p3);

        System.out.println("\n----- Alta con codigo duplicado (Conjunto debe rechazarlo) -----");
        Producto repetido = new Producto("P001", "Lavandina 2L", 30, 10, u1);
        centro.agregarProducto("P001", repetido);

        System.out.println("\n----- Codigos usados (Conjunto de unicidad) -----");
        centro.mostrarCodigosUsados();

        System.out.println("\n----- Catalogo actual -----");
        centro.mostrarCatalogo();

        System.out.println("\n----- Buscar producto por codigo -----");
        System.out.println("P002 -> " + centro.buscarProducto("P002"));

        System.out.println("\n----- Localizar ubicacion por codigo -----");
        System.out.println("Ubicacion de P003 -> " + centro.buscarUbicacion("P003"));

        System.out.println("\n----- Buscar codigo inexistente (debe avisar) -----");
        Ubicacion noExiste = centro.buscarUbicacion("P999");
        System.out.println("Ubicacion de P999 -> " + noExiste);

        // ===== Objetivo 2: Linea de expedicion (Cola FIFO) =====
        System.out.println("\n----- Despachar con la cola vacia (debe avisar) -----");
        Pedido vacio = centro.despacharProximoPedido();
        System.out.println("Pedido despachado -> " + vacio);

        System.out.println("\n----- Armar y encolar pedidos -----");
        Pedido ped1 = new Pedido("PED-1", 5);
        ped1.agregarItem(p1);
        ped1.agregarItem(p2);

        Pedido ped2 = new Pedido("PED-2", 5);
        ped2.agregarItem(p3);

        centro.marcarPedidoListo(ped1);
        centro.marcarPedidoListo(ped2);
        System.out.println("PED-1 -> " + ped1);
        System.out.println("PED-2 -> " + ped2);

        System.out.println("\n----- Cola de expedicion -----");
        centro.mostrarLineaExpedicion();

        System.out.println("\n----- Despachar en orden de llegada (FIFO) -----");
        Pedido despachado1 = centro.despacharProximoPedido();
        System.out.println("Despachado 1ro -> " + despachado1);
        Pedido despachado2 = centro.despacharProximoPedido();
        System.out.println("Despachado 2do -> " + despachado2);

        System.out.println("\n----- Despachar de nuevo con la cola vacia -----");
        centro.despacharProximoPedido();

        System.out.println("\n===== Fin de la demostracion =====");
    }
}
