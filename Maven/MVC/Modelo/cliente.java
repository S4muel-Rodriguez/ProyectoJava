// Clase Cliente
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;

    // getters y setters
}

// Clase Producto
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private double precio;

    // getters y setters
}

// Clase LineaFactura
@Entity
public class LineaFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Producto producto;
    private int cantidad;
    private double subtotal;

    // getters y setters
}

// Clase Factura
@Entity
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Cliente cliente;
    private LocalDate fecha;
    private double total;

    @OneToMany(cascade = CascadeType.ALL)
    private List<LineaFactura> lineas;

    // getters y setters
}

