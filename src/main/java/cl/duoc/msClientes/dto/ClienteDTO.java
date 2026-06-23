package cl.duoc.msClientes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa un clienteDTO en el sistema")
public class ClienteDTO {

    @Schema(description = "ID del cliente")
    private Integer id;
    
    @Schema(description = "Nombre del cliente")
    private String nombre;
    
    @Schema(description = "Correo electrónico del cliente")
    private String correo;

    @Schema(description = "Teléfono del cliente")
    private String telefono;

}
