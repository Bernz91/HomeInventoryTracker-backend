package main.backend.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AddGrocery {
    public String userId;
    public int itemNo;
    public String itemName;
    public int quantity;
    public boolean isValid(){
        return !itemName.trim().equals("");
    }
}
