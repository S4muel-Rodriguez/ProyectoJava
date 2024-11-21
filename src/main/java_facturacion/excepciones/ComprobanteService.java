import exceptions.ClienteNoEncontradoException;
import exceptions.ProductoNoEncontradoException;
import exceptions.StockInsuficienteException;
import exceptions.ServicioExternoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class ComprobanteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private ComprobanteRepository comprobanteRepository;

    public Comprobante crearComprobante(Comprobante comprobante) {
        // Validación de cliente existente
        Optional<Cliente> cliente = clienteRepository.findById(comprobante.getCliente().getId());
        if (cliente.isEmpty()) {
            throw new ClienteNoEncontradoException("Cliente con ID " + comprobante.getCliente().getId() + " no existe.");
        }

        double total = 0;
        int cantidadProductos = 0;
        for (LineaFactura linea : comprobante.getLineas()) {
            // Validación de producto existente
            Producto producto = productoRepository.findById(linea.getProducto().getId())
                    .orElseThrow(() -> new ProductoNoEncontradoException("Producto con ID " + linea.getProducto().getId() + " no existe."));

            // Validación de stock suficiente
            if (linea.getCantidad() > producto.getStock()) {
                throw new StockInsuficienteException("Stock insuficiente para el producto " + producto.getNombre());
            }

            // Reducir stock y calcular subtotal
            producto.setStock(producto.getStock() - linea.getCantidad());
            productoRepository.save(producto);

            double subtotal = producto.getPrecio() * linea.getCantidad();
            total += subtotal;
            cantidadProductos += linea.getCantidad();
        }

        comprobante.setTotal(total);
        comprobante.setCantidadProductos(cantidadProductos);
        comprobante.setFecha(obtenerFechaComprobante());

        return comprobanteRepository.save(comprobante);
    }

    private LocalDate obtenerFechaComprobante() {
        try {
            // Realizar la solicitud al API de World Clock
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map<String, Object>> response = restTemplate.getForEntity(
                    "http://worldclockapi.com/api/json/utc/now", Map.class);
            Map<String, Object> jsonResponse = response.getBody();
            String fecha = (String) jsonResponse.get("currentDateTime");
            return LocalDate.parse(fecha);
        } catch (Exception e) {
            // Lanzar una excepción personalizada si falla el servicio externo
            throw new ServicioExternoException("Error al obtener la fecha del servicio externo. Se usará la fecha local.");
        }
    }
}
