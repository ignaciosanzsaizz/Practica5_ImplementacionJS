package edu.comillas.icai.gitt.pat.spring.mvc.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class LineaCarrito {

    // 1. AÑADIMOS EL ID QUE FALTABA
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long idLinea;

    @ManyToOne
    @JoinColumn(name = "id_carrito")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Carrito carrito;

    // 2. CAMBIADO A STRING (Porque desde el front mandas el nombre, ej: "RM 2006")
    @Column(nullable = false)
    public String idArticulo;

    // 3. CAMBIADO A DOUBLE (Porque los precios tienen decimales, ej: 49.99)
    @Column(nullable = false)
    public Double precioUnitario;

    @Column(nullable = false)
    public int unidades;

    // 4. CAMBIADO A DOUBLE
    @Column(nullable = false)
    public Double costeLinea;

    // Getters y Setters
    public Long getIdCarrito() {
        return (carrito == null) ? null : carrito.getIdCarrito();
    }

    public String getArticulo() {
        return this.idArticulo;
    }

    public void setArticulo(String idArticulo) {
        this.idArticulo = idArticulo;
    }
}