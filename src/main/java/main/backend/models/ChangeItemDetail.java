package main.backend.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ChangeItemDetail {
    public int itemNo;
    public String itemName;
    public String categoryName;
    public boolean isValid(){
        return !itemName.trim().equals("");
    }
}
