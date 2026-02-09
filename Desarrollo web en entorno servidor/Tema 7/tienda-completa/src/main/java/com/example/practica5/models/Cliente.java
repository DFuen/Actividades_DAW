package com.example.practica5.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @NotBlank(message = "El DNI no puede estar vacío")
    @Size(min = 9, max = 9, message = "El DNI debe tener exactamente 9 caracteres")
    @Pattern(regexp = "^[0-9]{8}[A-Z]$", message = "El DNI debe tener el formato: 8 números seguidos de una letra mayúscula")
    @Column(name = "dni", length = 9, nullable = false)
    private String dni;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @NotBlank(message = "Los apellidos no pueden estar vacíos")
    @Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
    @Column(name = "apellidos", length = 100, nullable = false)
    private String apellidos;

    @NotNull(message = "El correo no puede ser nulo")
    @Email(message = "El correo debe tener un formato válido")
    @Column(name = "correo", unique = true, nullable = false)
    private String correo;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 255, message = "La dirección no puede exceder 255 caracteres")
    @Column(name = "direccion", length = 255, nullable = false)
    private String direccion;

    // Relación One-to-Many con Pedido
    @OneToMany(mappedBy = "cliente")
    @JsonIgnoreProperties("cliente")
    private List<Pedido> pedidos;

    //Marca un constructor para que Jackson lo use al deserializar
    @JsonCreator
    public Cliente(@JsonProperty("dni") String dni) {
        this.dni = dni;
    }
}