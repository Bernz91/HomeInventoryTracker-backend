package main.backend.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ItemDisplay {
    public int itemNo;
    public String categoryName;
    public String itemName;
    public int totalQuantity;
}
