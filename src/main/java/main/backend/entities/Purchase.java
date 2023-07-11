package main.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import main.backend.serializations.CustomPurchaseDeserializer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="purchases")
@JsonDeserialize(using = CustomPurchaseDeserializer.class)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private int purchaseNo;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="itemNo")
    @Getter @Setter private Item itemNo;

    @Getter @Setter private LocalDate purchaseDate;
    @Nullable
    @Getter @Setter private String purchasedFrom ;
    @Nullable
    @Getter @Setter private LocalDate expiryDate;

    @Getter @Setter private int remainingQuantity;

    @Getter @Setter private int originalQuantity;

    @Getter @Setter private BigDecimal price;
    
    @OneToMany(mappedBy= "purchaseNo")
    @JsonIgnore
    @Getter @Setter private List<CheckoutRecord> checkoutRecordList;

    @JsonIgnore
    public boolean isValid(){
        return !purchaseDate.toString().equals("") || remainingQuantity != 0 || originalQuantity != 0;
    }

}
