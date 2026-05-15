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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes(){
        List<Cliente> listaClientes = service.listarClientes();

        if (listaClientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaClientes);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Integer id){
        try {
            Cliente cliente = service.buscarPorId(id);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<ClienteDTO> obtenerClienteDTOPorId(@PathVariable Integer id){
        try {
            ClienteDTO clienteDTO = service.buscarClienteDTOPorId(id);
            return ResponseEntity.ok(clienteDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<Cliente> obtenerPorRut(@PathVariable String rut){
        try {
            return ResponseEntity.ok(service.buscarPorRut(rut));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Cliente> guardarCliente(@RequestBody Cliente cliente){
        Cliente nuevoCliente = service.guardarCliente(cliente);
        return ResponseEntity.ok(nuevoCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Integer id, @RequestBody Cliente cliente) {
        try {
            Cliente clienteActualizado = service.actualizarCliente(id, cliente);
            return ResponseEntity.ok(clienteActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id) {
        try {
            service.eliminarCliente(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}