package main.backend.services;

import jakarta.transaction.Transactional;

import main.backend.entities.*;
import main.backend.exceptions.InvalidDataException;
import main.backend.exceptions.ResourceNotFoundException;
import main.backend.models.AddCheckout;
import main.backend.models.AddGrocery;
import main.backend.models.AddNewItem;
import main.backend.repositories.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AddService {
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    private final ItemRepo itemRepo;
    private final AlertRepo alertRepo;
    private final PurchaseRepo purchaseRepo;
    private final ExistingItemRepo existingItemRepo;
    private final NewItemRepo newItemRepo;
    private final CheckoutRecordRepo checkoutRecordRepo;

    public AddService(UserRepo userRepo, CategoryRepo categoryRepo, ItemRepo itemRepo, AlertRepo alertRepo,
                      PurchaseRepo purchaseRepo, ExistingItemRepo existingItemRepo, NewItemRepo newItemRepo,
                      CheckoutRecordRepo checkoutRecordRepo) {
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
        this.itemRepo = itemRepo;
        this.alertRepo = alertRepo;
        this.purchaseRepo = purchaseRepo;
        this.existingItemRepo = existingItemRepo;
        this.newItemRepo = newItemRepo;
        this.checkoutRecordRepo = checkoutRecordRepo;
    }

    @Transactional
    public void addUser(String email){
        User user = this.userRepo.findUserByEmail(email);
        if(user != null) throw new InvalidDataException("This email address has already been registered.");
        user = new User();
        user.setEmail(email);
        this.userRepo.save(user);
    }

    @Transactional
    public void addNewItem(AddNewItem data){
        Item item = this.itemRepo.findItemByItemName(data.itemName, data.userId);
        if(item != null) throw new InvalidDataException("This item already exists in your inventory list.");

        if(data.itemName.trim().equals("")) throw new InvalidDataException("Item name cannot be blank.");
        if(data.quantity <= 0) throw new InvalidDataException("Item quantity must be greater than 0");
        if(data.purchaseDate.toString().equals("")) throw new InvalidDataException("Purchase date cannot be empty.");
        if(data.price.compareTo(BigDecimal.ZERO)<0) throw new InvalidDataException("Price cannot be negative.");

        User user = this.userRepo.findById(data.userId).orElseThrow(()-> new ResourceNotFoundException("User is not found for this id: " +data.userId));
        Category category = this.categoryRepo.findByCategoryName(data.categoryName);

        item = new Item();
        item.setUserId(user);
        item.setCategoryNo(category);
        item.setItemName(data.itemName); // required data
        item.setTotalQuantity(data.quantity); // required data

        Alert alert = new Alert();
        alert.setItem(item);
        alert.setLowInventoryAlert(data.lowInventoryAlert);
        alert.setInventoryThreshold(data.inventoryThreshold);
        alert.setExpiryDateAlert(data.expiryDateAlert);
        alert.setDaysBeforeExpiryDate(data.daysBeforeExpiryDate);
        item.setAlert(alert);

        Item savedItem = this.itemRepo.save(item);

        Purchase purchase = new Purchase();
        purchase.setItemNo(savedItem);
        purchase.setPurchaseDate(data.purchaseDate); // required data
        purchase.setPurchasedFrom(data.purchasedFrom.trim().equals("") ? null : data.purchasedFrom); // can be null
        purchase.setExpiryDate(data.expiryDate);
        purchase.setRemainingQuantity(data.quantity); // required data
        purchase.setOriginalQuantity(data.quantity); // required data
        purchase.setPrice(data.price); // will be zero if not set

        this.purchaseRepo.save(purchase);
    }
    @Transactional
    public void addStock(Purchase purchase){
        Item item = itemRepo.findById(purchase.getItemNo().getItemNo()).orElseThrow(()-> new ResourceNotFoundException("Item is not found for this itemNo: "+ purchase.getItemNo().getItemNo()));
        purchase.setItemNo(item);
        this.purchaseRepo.save(purchase);

        int totalQuantity = item.getTotalQuantity() + purchase.getRemainingQuantity();
        this.itemRepo.changeTotalQuantity(totalQuantity, item.getItemNo());
    }

    @Transactional
    public void addGroceryItem(AddGrocery data){
        User user = this.userRepo.findById(data.userId).orElseThrow(()->new ResourceNotFoundException("User is not found for this id: " +data.userId));
        if(data.itemNo != 0){
            ExistingItem existingItem = this.existingItemRepo.findById(data.itemNo).orElse(null);
            if(existingItem == null) {
                existingItem = new ExistingItem(1, false, user, data.itemNo);
                this.existingItemRepo.save(existingItem);
            } else {
                this.existingItemRepo.changeQuantity(existingItem.getQuantity() + 1, existingItem.getItemNo());
            }
        } else {
            Item item = this.itemRepo.findItemByItemName(data.itemName.trim(), data.userId);
            if(item == null){
                NewItem newItem = this.newItemRepo.findByItemNameAndUserId(data.itemName.trim(), data.userId);
                if(newItem != null) throw new InvalidDataException("Item name already exists on grocery list.");
                newItem = new NewItem(data.quantity, false, data.itemName.trim(), user);
                this.newItemRepo.save(newItem);
            } else {
                ExistingItem existingItem = this.existingItemRepo.findById(item.getItemNo()).orElse(null);
                if(existingItem != null) throw new InvalidDataException("Item name already exists on grocery list.");
                existingItem = new ExistingItem(data.quantity, false, user, item.getItemNo());
                this.existingItemRepo.save(existingItem);

            }
        }
    }

    @Transactional
    public void addCheckoutRecord(AddCheckout data){
        Purchase purchase = this.purchaseRepo.findById(data.purchaseNo).orElseThrow(()-> new ResourceNotFoundException("Purchase record not found."));

        if(data.quantity > purchase.getOriginalQuantity()) throw new InvalidDataException("Quantity exceeded remaining stock");

        CheckoutRecord checkoutRecord = new CheckoutRecord();
        checkoutRecord.setItemNo(purchase.getItemNo().getItemNo());
        checkoutRecord.setPurchaseNo(purchase);
        checkoutRecord.setQuantity(data.quantity);
        checkoutRecord.setCheckoutDate(data.checkoutDate);
        this.checkoutRecordRepo.save(checkoutRecord);

        int remainingQuantity = purchase.getRemainingQuantity() - data.quantity;
        purchase.setRemainingQuantity(remainingQuantity);
        int totalQuantity = purchase.getItemNo().getTotalQuantity() - data.quantity;
        purchase.getItemNo().setTotalQuantity(totalQuantity);
        this.purchaseRepo.save(purchase);
    }



}
