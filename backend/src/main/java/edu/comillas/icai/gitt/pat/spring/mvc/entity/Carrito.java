package edu.comillas.icai.gitt.pat.spring.mvc.entity;

import jakarta.persistence.*;

@Entity
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long idCarrito;

    // Quitamos temporalmente el nullable=false y unique=true para permitir carritos anónimos iniciales
    @Column
    public Long idUsuario;

    @Column
    public String correo;

    // Quitamos nullable=false o le damos un valor por defecto
    @Column
    public Double precioTotal = 0.0; // Cambiado a Double por los decimales

    public Long getIdCarrito() { return this.idCarrito; }
    public Long getUsuario() { return this.idUsuario; }
    public String getCorreo() { return this.correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public Double getPrecioTotal() { return this.precioTotal; }
    public void setPrecioTotal(Double precio_total) { this.precioTotal = precio_total; }
}