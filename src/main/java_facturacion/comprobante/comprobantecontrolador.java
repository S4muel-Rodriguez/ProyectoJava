@RestController
@RequestMapping("/comprobantes")
public class ComprobanteController {

    private final ComprobanteService comprobanteService;

    public ComprobanteController(ComprobanteService comprobanteService) {
        this.comprobanteService = comprobanteService;
    }

    @PostMapping
    public ResponseEntity<Comprobante> generarComprobante(@RequestBody Comprobante comprobante) {
        Comprobante nuevoComprobante = comprobanteService.generarComprobante(comprobante);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoComprobante);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comprobante> obtenerComprobante(@PathVariable Long id) {
        return ResponseEntity.ok(comprobanteService.obtenerPorId(id));
    }
}
