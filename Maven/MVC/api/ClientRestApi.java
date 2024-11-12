package com.tusistema.facturacion.api;

import com.tusistema.facturacion.entities.Cliente;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClienteRestApi {

    final String url = "https://jsonplaceholder.typicode.com/clients";  // URL de ejemplo, reemplázala con la real si tienes una

    // Obtener todos los clientes
    public ResponseEntity<Cliente[]> getClientes() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(
                this.url,
                Cliente[].class
        );
    }

    // Obtener un cliente por ID
    public Cliente getClienteById(String id) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        try {
            return restTemplate.getForObject(
                    this.url + "/{id}",
                    Cliente.class,
                    params
            );
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener un cliente", e);
        }
    }

    // Guardar un nuevo cliente
    public Cliente saveCliente(Cliente cliente) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(
                this.url,
                cliente,
                Cliente.class
        );
    }

    // Actualizar un cliente existente por ID
    public Cliente updateCliente(String id, Cliente cliente) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        restTemplate.put(
                this.url + "/{id}",
                cliente,
                params
        );
        return cliente;
    }

    // Eliminar un cliente por ID
    public Cliente deleteCliente(String id) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Cliente cliente = restTemplate.getForObject(
                this.url + "/{id}",
                Cliente.class,
                params
        );
        if (cliente != null) {
            restTemplate.delete(
                    this.url + "/{id}",
                    params
            );
        } else {
            throw new RuntimeException("Cliente no encontrado para la eliminación");
        }
        return cliente;
    }
}
