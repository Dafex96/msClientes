package cl.duoc.msClientes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msClientes.dto.ClienteDTO;
import cl.duoc.msClientes.model.Cliente;
import cl.duoc.msClientes.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public List<Cliente> listarClientes(){
        return repo.findAll();
    }

    public Cliente buscarPorId(Integer id){
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public Cliente buscarPorRut(String rut){
        return repo.findByRut(rut).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public Cliente guardarCliente(Cliente cliente){
        return repo.save(cliente);
    }

    public Cliente actualizarCliente(Integer id, Cliente clienteActualizado){

        Cliente clienteAnt = repo.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        
        clienteAnt.setRut(clienteActualizado.getRut());
        clienteAnt.setNombre(clienteActualizado.getNombre());
        clienteAnt.setApellido(clienteActualizado.getApellido());
        clienteAnt.setTelefono(clienteActualizado.getTelefono());
        clienteAnt.setEmail(clienteActualizado.getEmail());
        clienteAnt.setDireccion(clienteActualizado.getDireccion());
        
        return repo.save(clienteAnt);
    }

    public void eliminarCliente(Integer id){
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }else{
            throw new RuntimeException("Cliente no encontrado");
        }
    }

    public ClienteDTO buscarClienteDTOPorId(Integer id){
        Cliente cliente = buscarPorId(id);
        return new ClienteDTO(cliente.getId(), cliente.getNombre());
    }
}
