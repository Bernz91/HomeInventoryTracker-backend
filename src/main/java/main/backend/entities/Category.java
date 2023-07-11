package main.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="categories")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Category {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private int categoryNo;

    @Getter @Setter private String categoryName;
    @JsonIgnore
    @OneToMany(mappedBy = "categoryNo")
    @Getter @Setter private List<Item> itemList;
}
