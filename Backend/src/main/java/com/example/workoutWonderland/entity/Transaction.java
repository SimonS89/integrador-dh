package com.example.workoutWonderland.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Transaction")
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY) // Muchos Pedidos a un Usuario
    @JoinColumn(name = "client_id", referencedColumnName = "id") // Columna de unión en la tabla Pedido
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UserEntity customer;
    @ManyToMany
    @JoinTable(name = "Transaction_product", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "Transaction_id"), // Columna que hace referencia a la tabla Pedido
            inverseJoinColumns = @JoinColumn(name = "product_id") // Columna que hace referencia a la tabla Producto
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Product> product;
    private float total;
}
