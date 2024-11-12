import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Comprobante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private LocalDate fecha;
    private double total;
    private int cantidadProductos;

    @OneToMany(mappedBy = "comprobante", cascade = CascadeType.ALL)
    private List<LineaFactura> lineas;

    // Getters y Setters
}
