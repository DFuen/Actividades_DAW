package com.example.Actividad9.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMapping;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@RequestMapping("/usuario")
@Table(name= "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @ManyToOne

    @JoinTable(name="perfil")
    private Perfil perfil;
}
