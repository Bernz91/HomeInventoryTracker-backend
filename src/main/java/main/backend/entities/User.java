package main.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import main.backend.serializations.CustomUserSerializer;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;


@Entity
//@JsonSerialize(using= CustomUserSerializer.class)
@Table(name="users")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class User {
    @Id
    @UuidGenerator
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Getter @Setter private UUID userID;
    @Getter @Setter private String userID;
    @JsonIgnore
    @Getter @Setter private String email;
    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    @Getter @Setter private List<Item> itemList;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    @Getter @Setter private List<NewItem> newItemList;
    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    @Getter @Setter private List<ExistingItem> existingItemList;
}
