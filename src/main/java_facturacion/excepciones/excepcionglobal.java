import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleClienteNoEncontradoException(ClienteNoEncontradoException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ProductoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleProductoNoEncontradoException(ProductoNoEncontradoException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(StockInsuficienteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleStockInsuficienteException(StockInsuficienteException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ServicioExternoException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public String handleServicioExternoException(ServicioExternoException ex) {
        return ex.getMessage();
    }
}
