package main.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import main.backend.entities.idClasses.NewItemId;


@Entity
@Table(name="newItems")
@IdClass(NewItemId.class)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class NewItem extends GroceryItem{
    @Id
    @JsonIgnore
    @Getter @Setter private String userId;

    @Id
    @Getter @Setter private String itemName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="userId", referencedColumnName = "userId", insertable = false, updatable = false)
    @Getter @Setter private User user;

    public NewItem(int quantity, boolean ticked, String itemName, User user){
        super(quantity, ticked);
        this.itemName = itemName;
        this.userId = user.getUserID();
        this.user = user;
    }
}
