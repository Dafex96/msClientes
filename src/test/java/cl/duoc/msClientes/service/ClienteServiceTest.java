package cl.duoc.msClientes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import cl.duoc.msClientes.dto.ClienteDTO;
import cl.duoc.msClientes.model.Cliente;
import cl.duoc.msClientes.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository repo;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente clienteEjemplo;

    @BeforeEach
    void setup() {
        clienteEjemplo = new Cliente();
        clienteEjemplo.setId(1);
        clienteEjemplo.setRut("12345678-9");
        clienteEjemplo.setNombre("Juan");
        clienteEjemplo.setApellido("Perez");
        clienteEjemplo.setTelefono("+56912345678");
        clienteEjemplo.setEmail("juan.perez@correo.cl");
        clienteEjemplo.setDireccion("Avenida Siempre Viva 123");
    }

    @Test
    void listarClientes_retornaLista() {

        List<Cliente> listaFalsa = new ArrayList<>();

        listaFalsa.add(clienteEjemplo);
        when(repo.findAll()).thenReturn(listaFalsa);
        
        List<Cliente> resultado = clienteService.listarClientes();

        assertEquals(1, resultado.size());
        assertEquals("12345678-9", resultado.get(0).getRut());
    }

    @Test
    void buscarPorId_encontrado() {
        when(repo.findById(1)).thenReturn(Optional.of(clienteEjemplo));
        
        Cliente resultado = clienteService.buscarPorId(1);
        
        assertEquals(1, resultado.getId());
        assertEquals("Juan", resultado.getNombre());
    }

    @Test
    void buscarPorId_noEncontrado() {
        when(repo.findById(99)).thenReturn(Optional.empty());
        
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            clienteService.buscarPorId(99);
        });
        
        assertEquals("Cliente no encontrado", error.getMessage());
    }

    @Test
    void buscarPorRut_encontrado() {
        when(repo.findByRut("12345678-9")).thenReturn(Optional.of(clienteEjemplo));
        
        Cliente resultado = clienteService.buscarPorRut("12345678-9");
        
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
    }

    @Test
    void buscarPorRut_noEncontrado() {
        when(repo.findByRut("00000000-0")).thenReturn(Optional.empty());
        
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            clienteService.buscarPorRut("00000000-0");
        });
        
        assertEquals("Cliente no encontrado", error.getMessage());
    }

    @Test
    void guardarCliente_exitoso() {
        when(repo.save(any(Cliente.class))).thenReturn(clienteEjemplo);
        
        Cliente resultado = clienteService.guardarCliente(new Cliente());
        
        assertNotNull(resultado);
        assertEquals("12345678-9", resultado.getRut());
    }

    @Test
    void actualizarCliente_exitoso() {
        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setRut("98765432-1");
        clienteActualizado.setNombre("Carlos");
        clienteActualizado.setApellido("Tapia");
        clienteActualizado.setTelefono("+56987654321");
        clienteActualizado.setEmail("carlos@correo.cl");
        clienteActualizado.setDireccion("Calle Falsa 123");
        
        when(repo.findById(1)).thenReturn(Optional.of(clienteEjemplo));
        when(repo.save(any(Cliente.class))).thenReturn(clienteEjemplo);

        Cliente resultado = clienteService.actualizarCliente(1, clienteActualizado);

        assertEquals("Carlos", resultado.getNombre());
        assertEquals("98765432-1", resultado.getRut());
        assertEquals("carlos@correo.cl", resultado.getEmail());
    }

    @Test
    void actualizarCliente_noEncontrado() {
        when(repo.findById(99)).thenReturn(Optional.empty());
        
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            clienteService.actualizarCliente(99, new Cliente());
        });
        
        assertEquals("Cliente no encontrado", error.getMessage());
    }

    @Test
    void eliminarCliente_exitoso() {
        when(repo.existsById(1)).thenReturn(true);
        
        clienteService.eliminarCliente(1);
        
        verify(repo, times(1)).deleteById(1);
    }

    @Test
    void eliminarCliente_noEncontrado() {
        when(repo.existsById(99)).thenReturn(false);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            clienteService.eliminarCliente(99);
        });

        assertEquals("Cliente no encontrado", error.getMessage());
    }

    @Test
    void buscarClienteDTOPorId_exitoso() {
        when(repo.findById(1)).thenReturn(Optional.of(clienteEjemplo));

        ClienteDTO resultado = clienteService.buscarClienteDTOPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        
        assertEquals("Juan Perez", resultado.getNombre());
        assertEquals("juan.perez@correo.cl", resultado.getCorreo());
        assertEquals("+56912345678", resultado.getTelefono());
    }
}