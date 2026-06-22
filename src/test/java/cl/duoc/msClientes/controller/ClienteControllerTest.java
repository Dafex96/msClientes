package cl.duoc.msClientes.controller;

import cl.duoc.msClientes.dto.ClienteDTO;
import cl.duoc.msClientes.model.Cliente;
import cl.duoc.msClientes.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc llamadaFalsa;

    @MockitoBean
    private ClienteService clienteService;

    private Cliente clienteEjemplo;
    private ClienteDTO clienteDTOEjemplo;

    @BeforeEach
    void setUp() {
        clienteEjemplo = new Cliente();
        clienteEjemplo.setId(1);
        clienteEjemplo.setRut("12345678-9");
        clienteEjemplo.setNombre("Juan Perez");

        clienteDTOEjemplo = new ClienteDTO();
        clienteDTOEjemplo.setId(1);
        clienteDTOEjemplo.setNombre("Juan Perez");
    }

    
    @Test
    void listarClientes_retorna200() throws Exception {
        List<Cliente> listaFalsa = new ArrayList<>();
        listaFalsa.add(clienteEjemplo);

        when(clienteService.listarClientes()).thenReturn(listaFalsa);

        llamadaFalsa.perform(get("/api/v1/clientes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].rut").value("12345678-9"));
    }

    
    @Test
    void listarClientes_retorna204() throws Exception {
        when(clienteService.listarClientes()).thenReturn(new ArrayList<>());
        
        llamadaFalsa.perform(get("/api/v1/clientes"))
            .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorId_retorna200() throws Exception {
        when(clienteService.buscarPorId(1)).thenReturn(clienteEjemplo);

        llamadaFalsa.perform(get("/api/v1/clientes/id/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Juan Perez"));
    }

    @Test
    void buscarPorId_retorna404() throws Exception {
        when(clienteService.buscarPorId(99)).thenThrow(new RuntimeException("Cliente no encontrado"));

        llamadaFalsa.perform(get("/api/v1/clientes/id/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void buscarDTOPorId_retorna200() throws Exception {
        when(clienteService.buscarClienteDTOPorId(1)).thenReturn(clienteDTOEjemplo);

        llamadaFalsa.perform(get("/api/v1/clientes/dto/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Juan Perez"));
    }

    @Test
    void buscarDTOPorId_retorna404() throws Exception {
        when(clienteService.buscarClienteDTOPorId(99)).thenThrow(new RuntimeException("Cliente no encontrado"));

        llamadaFalsa.perform(get("/api/v1/clientes/dto/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void buscarPorRut_retorna200() throws Exception {
        when(clienteService.buscarPorRut("12345678-9")).thenReturn(clienteEjemplo);

        llamadaFalsa.perform(get("/api/v1/clientes/rut/12345678-9"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Juan Perez"));
    }

    @Test
    void buscarPorRut_retorna404() throws Exception {
        when(clienteService.buscarPorRut("00000000-0")).thenThrow(new RuntimeException("Cliente no encontrado"));

        llamadaFalsa.perform(get("/api/v1/clientes/rut/00000000-0"))
            .andExpect(status().isNotFound());
    }

    @Test
    void guardar_retorna200() throws Exception {
        when(clienteService.guardarCliente(any(Cliente.class))).thenReturn(clienteEjemplo);

        String jsonBody = "{\"rut\":\"12345678-9\",\"nombre\":\"Juan Perez\"}";

        llamadaFalsa.perform(post("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.rut").value("12345678-9"));
    }

    @Test
    void actualizar_retorna200() throws Exception {
        when(clienteService.actualizarCliente(any(Integer.class), any(Cliente.class))).thenReturn(clienteEjemplo);

        String jsonBody = "{\"rut\":\"12345678-9\",\"nombre\":\"Juan Perez\"}";

        llamadaFalsa.perform(put("/api/v1/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Juan Perez"));
    }

    @Test
    void actualizar_retorna404() throws Exception {
        when(clienteService.actualizarCliente(any(Integer.class), any(Cliente.class)))
            .thenThrow(new RuntimeException("Cliente no existe"));

        llamadaFalsa.perform(put("/api/v1/clientes/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"No existo\"}"))
            .andExpect(status().isNotFound());
    }

    @Test
    void eliminar_retorna204() throws Exception {
        doNothing().when(clienteService).eliminarCliente(1);

        llamadaFalsa.perform(delete("/api/v1/clientes/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_retorna404() throws Exception {
        doThrow(new RuntimeException("Cliente no existe")).when(clienteService).eliminarCliente(99);

        llamadaFalsa.perform(delete("/api/v1/clientes/99"))
            .andExpect(status().isNotFound());
    }
}