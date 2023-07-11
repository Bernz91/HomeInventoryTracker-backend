package main.backend.entities;

import jakarta.persistence.*;
import lombok.*;


@MappedSuperclass
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class GroceryItem {
    @Getter @Setter private int quantity;
    @Getter @Setter private boolean ticked;

}
