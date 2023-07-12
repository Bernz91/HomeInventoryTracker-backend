package main.backend.models;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AddNewItem {
    public String userId;
    public String categoryName;
    public String itemName;
    public LocalDate purchaseDate;
    public String purchasedFrom;
    public LocalDate expiryDate;

    public int quantity;
    public BigDecimal price;
    public boolean lowInventoryAlert;
    public int inventoryThreshold;
    public boolean expiryDateAlert;
    public int daysBeforeExpiryDate;

    public boolean isValid(){
        return !itemName.trim().equals("") && quantity != 0;
    }
}
