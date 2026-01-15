package com.example.Tarea1.model;

import jakarta.persistence.*;

@Entity @Table(name ="libros")

public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String titulo;

    @Column()
    private String autor;

    @Column(unique = true)
    private String isbn;

    @Column()
    private int paginas;

    public Libro() {
    }

    public Libro(int paginas, String isbn, String autor, String titulo, Long id) {
        this.paginas = paginas;
        this.isbn = isbn;
        this.autor = autor;
        this.titulo = titulo;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }
}
