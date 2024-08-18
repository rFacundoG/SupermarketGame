import java.util.ArrayList;
import java.util.Scanner;

class Humane {
    protected String nombre;

    public Humane(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}

class Empleado extends Humane {
    private double sueldo;

    public Empleado(String nombre, double sueldo) {
        super(nombre);
        this.sueldo = sueldo;
    }

    public double getSueldo() {
        return sueldo;
    }
}

class Cliente extends Humane {
    private boolean Mayorista;

    public Cliente(String nombre, boolean Mayorista) {
        super(nombre);
        this.Mayorista = Mayorista;
    }

    public boolean Mayorista() {
        return Mayorista;
    }
}

class Producto {
    private String nombre;
    private double precio;
    private int cantidad;

    public Producto(String nombre, double precio, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void reducirCantidad(int cantidadComprada) {
        this.cantidad -= cantidadComprada;
    }
}

class Caja {
    private int numero;
    private Empleado empleado;

    public Caja(int numero, Empleado empleado) {
        this.numero = numero;
        this.empleado = empleado;
    }

    public int getNumero() {
        return numero;
    }

    public Empleado getEmpleado() {
        return empleado;
    }
}

class Transaccion {
    private Cliente cliente;
    private ArrayList<Producto> productos;
    private double total;
    private Caja caja;

    public Transaccion(Cliente cliente, ArrayList<Producto> productos, Caja caja) {
        this.cliente = cliente;
        this.productos = productos;
        this.caja = caja;
        this.total = calcularTotal();
    }

    private double calcularTotal() {
        double suma = 0;
        for (Producto producto : productos) {
            suma += producto.getPrecio();
        }
        if (cliente.Mayorista()) {
            suma *= 0.9; // Descuento del 10% para mayoristas
        }
        return suma;
    }

    public void mostrarCompra() {
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Compró los siguientes productos:");
        for (Producto producto : productos) {
            System.out.println("- " + producto.getNombre() + ": $" + producto.getPrecio());
        }
        System.out.println("Total pagado: $" + total);
        System.out.println("Caja: " + caja.getNumero() + " atendida por " + caja.getEmpleado().getNombre());
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Ingresa tu nombre: ");
        String nombreCliente = scanner.nextLine();
        
        System.out.print("¿Eres mayorista? (si/no): ");
        boolean Mayorista = scanner.nextLine().equalsIgnoreCase("si");
        
        Cliente cliente = new Cliente(nombreCliente, Mayorista);

        // Arreglo de algunos productos disponibles en el supermercado.
        Producto[] inventario = {
            new Producto("Manzanas", 100, 100),
            new Producto("Pan", 1200, 50),
            new Producto("Leche", 1500, 30)
        };

        Empleado empleado1 = new Empleado("Pepe", 1300.0);
        Caja caja1 = new Caja(1, empleado1);

        ArrayList<Producto> productosComprados = new ArrayList<>();
        String productoElegido;
        int cantidadElegida;

        System.out.println("¡Bienvenido " + cliente.getNombre() + "!\n");
        System.out.println("Lista de productos disponibles:");

        while (true) {
            for (Producto producto : inventario) {
                System.out.println("- " + producto.getNombre() + ": $" + producto.getPrecio() + " (Cantidad disponible: " + producto.getCantidad() + ")");
            }

            System.out.print("\nIngresa el nombre del producto que deseas comprar (o escribe 'salir' para finalizar): ");
            productoElegido = scanner.nextLine();

            if (productoElegido.equalsIgnoreCase("salir")) {
                break;
            }

            // Busca el producto en el inventario
            Producto productoSeleccionado = null;
            for (Producto producto : inventario) {
                if (producto.getNombre().equalsIgnoreCase(productoElegido)) {
                    productoSeleccionado = producto;
                    break;
                }
            }

            if (productoSeleccionado == null) {
                System.out.println("Producto no encontrado. Por favor, intenta de nuevo.");
                continue;
            }

            System.out.print("Ingresa la cantidad que deseas comprar: ");
            cantidadElegida = scanner.nextInt();
            scanner.nextLine();

            if (cantidadElegida > productoSeleccionado.getCantidad()) {
                System.out.println("No tenemos suficiente cantidad. Por favor, intenta de nuevo.");
                continue;
            }

            // Reduce la cantidad disponible en el inventario y lo agrega a la lista de compras
            productoSeleccionado.reducirCantidad(cantidadElegida);
            for (int i = 0; i < cantidadElegida; i++) {
                productosComprados.add(new Producto(productoSeleccionado.getNombre(), productoSeleccionado.getPrecio(), 1));
            }

            System.out.println("\nProducto agregado a la compra.\n");
        }

        // Registro de la compra
        Transaccion transaccion = new Transaccion(cliente, productosComprados, caja1);

        transaccion.mostrarCompra();
        scanner.close();
    }
}
