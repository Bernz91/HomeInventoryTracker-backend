package main.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="alerts")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Alert {
    @Id
    @Column(name = "itemNo")
    @Getter @Setter private int itemNo;
    @OneToOne
    @MapsId
    @JoinColumn(name="itemNo")
    @JsonIgnore
    @Getter @Setter private Item item;
    @Getter @Setter private boolean lowInventoryAlert;
    @Getter @Setter private int inventoryThreshold;
    @Getter @Setter private boolean expiryDateAlert;
    @Getter @Setter private int daysBeforeExpiryDate;

}
