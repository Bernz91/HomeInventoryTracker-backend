package main.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="existingItems")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ExistingItem extends GroceryItem{

    @Id
    @Getter @Setter private int itemNo;

//    @Id
//    @Column(name = "itemNo")
//    @Getter @Setter private int itemNo;
//
//    @OneToOne
//    @MapsId
//    @JoinColumn(name="itemNo")
//    @Getter @Setter private Item item;

//    @ManyToOne
//    @JoinColumn(name="itemNo", insertable=false, updatable=false)
//    @Getter @Setter private Item item;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="userId")
    @Getter @Setter private User userId;

    public ExistingItem(int quantity,boolean ticked, User user,  int itemNo){
        super(quantity, ticked);
        this.userId = user;
        this.itemNo = itemNo;
    }

}
