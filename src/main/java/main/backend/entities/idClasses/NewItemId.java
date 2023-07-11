package main.backend.entities.idClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
public class NewItemId implements Serializable {
    @Getter @Setter private UUID userId;
    @Getter @Setter private String itemName;
}
