package main.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="items")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemNo")
    @Getter @Setter private int itemNo;
    @Getter @Setter private String itemName;
    @Getter @Setter private int totalQuantity;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="userId")
    @Getter @Setter private User userId;
    @ManyToOne
    @JoinColumn(name="categoryNo")
    @Getter @Setter private Category categoryNo;

//    @OneToOne(mappedBy="item", cascade = CascadeType.ALL)
//    @PrimaryKeyJoinColumn
//    @Getter @Setter private ExistingItem existingItem;

    @OneToOne(mappedBy="item", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @Getter @Setter private Alert alert;

//    @OneToMany
//    @JoinColumn(name="item")
//    @Getter @Setter private List<ExistingItem> existingItemList;

    @OneToMany(mappedBy = "itemNo")
    @Getter @Setter private List<Purchase> purchaseList;

//    @OneToMany(mappedBy="itemNo")
//    @Getter @Setter private List<CheckoutRecord> checkoutRecordList;
}
