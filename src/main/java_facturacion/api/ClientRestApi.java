package com.tuempresa.facturacion.service;

import com.tuempresa.facturacion.model.Cliente;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClienteService {

    // URL base de la API externa
    private final String url = "https://jsonplaceholder.typicode.com/clients";

    private final RestTemplate restTemplate;

    public ClienteService() {
        this.restTemplate = new RestTemplate();
    }

    // Obtener todos los clientes de la API
    public ResponseEntity<Cliente[]> obtenerTodosLosClientes() {
        return restTemplate.getForEntity(
                this.url,
                Cliente[].class
        );
    }

    // Obtener un cliente por su ID desde la API
    public Cliente obtenerClientePorId(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        try {
            return restTemplate.getForObject(
                    this.url + "/{id}",
                    Cliente.class,
                    params
            );
        } catch (HttpClientErrorException.NotFound e) {
            return null; // Manejo de cliente no encontrado
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el cliente", e);
        }
    }

    // Guardar un cliente en la API
    public Cliente guardarCliente(Cliente cliente) {
        return restTemplate.postForObject(
                this.url,
                cliente,
                Cliente.class
        );
    }

    // Actualizar un cliente existente en la API
    public Cliente actualizarCliente(String id, Cliente cliente) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        restTemplate.put(
                this.url + "/{id}",
                cliente,
                params
        );
        return cliente; // Devuelve el cliente actualizado
    }

    // Eliminar un cliente por ID en la API
    public Cliente eliminarCliente(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        // Obtiene el cliente antes de eliminarlo
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
            throw new RuntimeException("Cliente no encontrado para eliminación");
        }
        return cliente; // Retorna el cliente eliminado
    }
}

