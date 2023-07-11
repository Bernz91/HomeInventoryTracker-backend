package main.backend.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ChangePurchaseDetail {
    public int purchaseNo;
    public LocalDate purchaseDate;
    public String purchasedFrom;
    public LocalDate expiryDate;
    public int originalQuantity;
    public BigDecimal price;

    public boolean isValid(){
        return purchaseDate != null || originalQuantity > 0 || price.compareTo(BigDecimal.ZERO) >= 0;
    }
}
