package main.backend.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import main.backend.entities.Alert;
import main.backend.entities.CheckoutRecord;
import main.backend.entities.Purchase;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class ItemDetailDisplay {
    public int itemNo;
    public String itemName;
    public String categoryName;
    public int totalQuantity;
    public List<Purchase> purchases;
    public List<CheckoutRecord> checkoutRecords;
    public Alert alert;

}
