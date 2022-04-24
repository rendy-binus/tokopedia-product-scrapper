package com.example.brickscrapper.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "tokopedia_product")
public class TokopediaProduct extends BaseProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "product_name")
    private String name;

    @Lob
    @Column(name = "product_description")
    private String description;

    @Column(name = "product_price", precision = 19, scale = 2)
    private BigDecimal price;

    @ElementCollection
    @Column(name = "product_image_link")
    @CollectionTable(name = "tokopedia_product_product_image_links", joinColumns = @JoinColumn(name = "owner_id"))
    private List<String> imageLinks = new ArrayList<>();

    @Column(name = "product_rating")
    private Float rating;

    @Column(name = "product_merchant", length = 100)
    private String merchant;

    @Transient
    private String link;

    @Override
    public String toString() {
        return "TokopediaProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageLinks=" + imageLinks +
                ", rating=" + rating +
                ", merchant='" + merchant + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
