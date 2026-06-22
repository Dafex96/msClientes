package cl.duoc.msClientes.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes")
@Schema(description = "Representa un cliente en el sistema")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del cliente")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Rut del cliente")
    private String rut;

    @Column(nullable = false)
    @Schema(description = "Nombre del cliente")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Apellido del cliente")
    private String apellido;

    @Column(nullable = false)
    @Schema(description = "Telefono del cliente")
    private String telefono;

    @Column(nullable = false)
    @Schema(description = "Correo del cliente")
    private String email;

    @Column(nullable = false)
    @Schema(description = "Direccion del cliente")
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "id_tipo_cliente")
    @Schema(description = "Tipo de cliente")
    private TipoCliente tipoCliente;
}
