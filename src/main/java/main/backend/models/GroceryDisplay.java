package main.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class GroceryDisplay {
    public int quantity;
    public boolean ticked;
    public int itemNo;
    public String itemName;
    public UUID userId;

    public GroceryDisplay(int quantity, boolean ticked, String itemName, UUID userId) {
        this.quantity = quantity;
        this.ticked = ticked;
        this.itemName = itemName;
        this.userId = userId;
    }
    @JsonIgnore
    public boolean isValid(){
        return quantity > 0 || !itemName.trim().equals("") ;
    }
}

