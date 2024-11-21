@Service
public class ComprobanteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private ComprobanteRepository comprobanteRepository;

    public Comprobante crearComprobante(Comprobante comprobante) {
        // 1. Validación de cliente existente
        Optional<Cliente> cliente = clienteRepository.findById(comprobante.getCliente().getId());
        if (cliente.isEmpty()) {
            throw new IllegalArgumentException("Cliente no existe.");
        }

        double total = 0;
        int cantidadProductos = 0;
        for (LineaFactura linea : comprobante.getLineas()) {
            // 2. Validación de producto existente
            Producto producto = productoRepository.findById(linea.getProducto().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no existe."));

            // 3. Validación de stock suficiente
            if (linea.getCantidad() > producto.getStock()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto " + producto.getNombre());
            }

            // 4. Reducir stock y calcular subtotal
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
            // Usar la fecha local si el servicio falla
            return LocalDate.now();
        }
    }
}
