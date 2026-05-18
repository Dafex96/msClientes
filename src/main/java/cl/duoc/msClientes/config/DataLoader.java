package cl.duoc.msClientes.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import cl.duoc.msClientes.model.Cliente;
import cl.duoc.msClientes.model.TipoCliente;
import cl.duoc.msClientes.repository.ClienteRepository;
import cl.duoc.msClientes.repository.TipoClienteRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(ClienteRepository clienteRepo, TipoClienteRepository tipoClienteRepo) {

        return args -> {

            if (clienteRepo.count() > 0 || tipoClienteRepo.count() > 0) {
                System.out.println("Datos ya existen, no se cargan nuevamente...");
                
            }else{

                TipoCliente tipo1 = new TipoCliente(null, "Particular");
                TipoCliente tipo2 = new TipoCliente(null, "Empresa");

                tipoClienteRepo.save(tipo1);
                tipoClienteRepo.save(tipo2);

                Cliente cliente1 = new Cliente(null, "12345678-9", "Juan", "Pérez", "987654321", "juanperez99@gmail.com", "Av. Matta 123", tipo1);
                Cliente cliente2 = new Cliente(null, "98765432-1", "María", "Gómez", "912345678", "mariagomez27@gmail.com", "Pasaje Los Platanos 803", tipo1);
                Cliente cliente3 = new Cliente(null, "70456732-9", "Marcopolo", "SPA", "267834684", "rhmp@marcopolo.cl", "Americo Vespucio 1367", tipo2);

                clienteRepo.save(cliente1);
                clienteRepo.save(cliente2);
                clienteRepo.save(cliente3);

                System.out.println("Datos cargados con exito...");

            };
        };
    }
}
