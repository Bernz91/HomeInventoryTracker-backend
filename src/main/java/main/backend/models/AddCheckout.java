package main.backend.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
public class AddCheckout {
    public int purchaseNo;
    public LocalDate checkoutDate;
    public int quantity;

    public boolean isValid(){
        return checkoutDate != null || quantity > 0;
    }
}
