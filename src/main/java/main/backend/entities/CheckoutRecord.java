package main.backend.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import main.backend.serializations.CustomCheckoutRecordSerializer;

import java.time.LocalDate;

@Entity
@JsonSerialize(using= CustomCheckoutRecordSerializer.class)
@Table(name="checkoutRecords")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CheckoutRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private int checkoutNo;

    @Getter @Setter private int itemNo;
    @ManyToOne
    @JoinColumn(name="purchaseNo")
    @Getter @Setter private Purchase purchaseNo;
    @Getter @Setter private int quantity;
    @Getter @Setter private LocalDate checkoutDate;
}
