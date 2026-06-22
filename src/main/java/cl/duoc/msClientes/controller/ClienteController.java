package cl.duoc.msClientes.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cl.duoc.msClientes.dto.ClienteDTO;
import cl.duoc.msClientes.model.Cliente;
import cl.duoc.msClientes.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Vehiculos", description = "Operaciones relacionadas con los clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    @Operation(
        summary = "Listar todos los clientes",
        description = "Retorna una lista de todos los clientes registrados en la base de datos"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Hay clientes registrados"),
        @ApiResponse(responseCode = "204", description = "No hay clientes registrados")
    })
    public ResponseEntity<List<Cliente>> listarClientes(){
        List<Cliente> listaClientes = service.listarClientes();

        if (listaClientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaClientes);
        }
    }

    @GetMapping("/id/{id}")
    @Operation(
        summary = "Buscar un cliente por su ID",
        description = "Retorna un cliente según el ID proporcionado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Integer id){
        try {
            Cliente cliente = service.buscarPorId(id);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    @Operation(
        summary = "Buscar un cliente por su ID y retornar como DTO",
        description = "Retorna un cliente en formato DTO segun el ID proporcionado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<ClienteDTO> obtenerClienteDTOPorId(@PathVariable Integer id){
        try {
            ClienteDTO clienteDTO = service.buscarClienteDTOPorId(id);
            return ResponseEntity.ok(clienteDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rut/{rut}")
    @Operation(
        summary = "Buscar un cliente por su rut",
        description = "Retorna un cliente segun el rut proporcionado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<Cliente> obtenerPorRut(@PathVariable String rut){
        try {
            return ResponseEntity.ok(service.buscarPorRut(rut));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(
        summary = "Agregar un nuevo cliente",
        description = "Crea un nuevo cliente con los datos proporcionados en el cuerpo de la solicitud"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de cliente invalidos")
    })
    public ResponseEntity<Cliente> guardarCliente(@RequestBody Cliente cliente){
        Cliente nuevoCliente = service.guardarCliente(cliente);
        return ResponseEntity.ok(nuevoCliente);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar un cliente existente",
        description = "Actualiza los datos de un cliente existente segun el ID proporcionado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Integer id, @RequestBody Cliente cliente) {
        try {
            Cliente clienteActualizado = service.actualizarCliente(id, cliente);
            return ResponseEntity.ok(clienteActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar un Cliente",
        description = "Elimina un Cliente segun el ID proporcionado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id) {
        try {
            service.eliminarCliente(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}