@Servicio
public class FacturaService {
    @Autowired
    private FacturaRepository facturaRepository;

    public Factura crearFactura(Factura factura) {
        double total = factura.getLineas().stream()
                .mapToDouble(linea -> linea.getProducto().getPrecio() * linea.getCantidad())
                .sum();
        factura.setTotal(total);
        return facturaRepository.save(factura);
    }
}

