package org.readingisgood.stockmicroservice.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookId;
    private Long quantity;

    public Stock() {

    }

    public Stock(Long bookId, Long quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }

}
