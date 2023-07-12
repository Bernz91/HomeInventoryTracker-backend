package main.backend.entities.idClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
public class NewItemId implements Serializable {
    @Getter @Setter private String userId;
    @Getter @Setter private String itemName;
}
