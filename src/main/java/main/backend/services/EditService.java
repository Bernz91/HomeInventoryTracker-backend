package main.backend.services;

import jakarta.transaction.Transactional;
import main.backend.entities.*;
import main.backend.exceptions.InvalidDataException;
import main.backend.exceptions.ResourceNotFoundException;
import main.backend.models.ChangeItemDetail;
import main.backend.models.ChangePurchaseDetail;
import main.backend.models.GroceryDisplay;
import main.backend.repositories.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EditService {
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    private final ItemRepo itemRepo;
    private final AlertRepo alertRepo;
    private final PurchaseRepo purchaseRepo;
    private final CheckoutRecordRepo checkoutRecordRepo;
    private final NewItemRepo newItemRepo;
    private final ExistingItemRepo existingItemRepo;

    public EditService(UserRepo userRepo, CategoryRepo categoryRepo, ItemRepo itemRepo, AlertRepo alertRepo,
                       PurchaseRepo purchaseRepo, CheckoutRecordRepo checkoutRecordRepo, NewItemRepo newItemRepo,
                       ExistingItemRepo existingItemRepo) {
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
        this.itemRepo = itemRepo;
        this.alertRepo = alertRepo;
        this.purchaseRepo = purchaseRepo;
        this.checkoutRecordRepo = checkoutRecordRepo;
        this.newItemRepo = newItemRepo;
        this.existingItemRepo = existingItemRepo;
    }

    @Transactional
    public void changeItemDetail(ChangeItemDetail data){
        Item item = this.itemRepo.findById(data.itemNo).orElseThrow(()->new ResourceNotFoundException("Item is not found for this itemNo: "+ data.itemNo));

        if(!item.getItemName().equals(data.itemName)){
            this.itemRepo.changeItemName(data.itemName, item.getItemNo());
        }

        if(!item.getCategoryNo().getCategoryName().equals(data.categoryName)){
            Category category = this.categoryRepo.findByCategoryName(data.categoryName);
            if(category == null) throw new ResourceNotFoundException("Category: "+ data.categoryName +" is invalid.");
            this.itemRepo.changeCategory(category.getCategoryNo(), item.getItemNo());
        }
    }

    @Transactional
    public void changePurchaseDetail(ChangePurchaseDetail data){
        Purchase purchase = this.purchaseRepo.findById(data.purchaseNo).orElseThrow(()->new ResourceNotFoundException("Purchase detail is not found for this purchaseNo: "+ data.purchaseNo));

        if(!purchase.getPurchaseDate().equals(data.purchaseDate))
            this.purchaseRepo.changePurchaseDate(data.purchaseDate, purchase.getPurchaseNo());

        if(purchase.getPurchasedFrom() == null || !purchase.getPurchasedFrom().equals(data.purchasedFrom)){
           this.purchaseRepo.changePurchasedFrom(data.purchasedFrom, purchase.getPurchaseNo());
        }

        if(purchase.getExpiryDate() == null || !purchase.getExpiryDate().equals(data.expiryDate)) {
           this.purchaseRepo.changeExpiryDate(data.expiryDate, purchase.getPurchaseNo());
        }

        if(purchase.getOriginalQuantity() != data.originalQuantity) {
            if(purchase.getRemainingQuantity() == 0) throw new InvalidDataException("Remaining stock is 0, please delete checkout history if you wish to amend the quantity purchased.");

            List<CheckoutRecord> checkoutRecordList = this.checkoutRecordRepo.findCheckoutRecordsByPurchaseNo(purchase.getPurchaseNo());
            int totalStockCheckedOut = 0;
            if(checkoutRecordList.size() > 0){
                for(CheckoutRecord checkoutRecord:checkoutRecordList){
                    totalStockCheckedOut += checkoutRecord.getQuantity();
                }
                if(data.originalQuantity - totalStockCheckedOut < 0)
                    throw new InvalidDataException("Items have been checked out more times than the revised quantity.") ;
            }
            this.purchaseRepo.changeQuantity(data.originalQuantity - totalStockCheckedOut, purchase.getPurchaseNo());
            this.purchaseRepo.changeOriginalQuantity(data.originalQuantity, purchase.getPurchaseNo());

            Item item = this.itemRepo.findById(purchase.getItemNo().getItemNo()).orElseThrow(()-> new ResourceNotFoundException("Item is not found for this itemNo: "+ purchase.getItemNo().getItemNo()));
            int totalQuantity = item.getTotalQuantity() + (data.originalQuantity - purchase.getOriginalQuantity());
            this.itemRepo.changeTotalQuantity(totalQuantity, item.getItemNo());
        }

        if(purchase.getPrice().compareTo(data.price) != 0) {
            if (data.price.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidDataException("Price cannot be negative or zero.");
            this.purchaseRepo.changePrice(data.price, purchase.getPurchaseNo());
        }
    }

    @Transactional
    public void changeGroceryDetail(GroceryDisplay data){
        boolean quantityChange, tickChange;
        if(data.itemNo == 0){
            NewItem newItem = this.newItemRepo.findByItemNameAndUserId(data.itemName, data.userId);
            if(newItem == null) throw new ResourceNotFoundException("Item not found!");
            quantityChange = newItem.getQuantity() != data.quantity;
            tickChange = Boolean.compare(newItem.isTicked(), data.ticked) != 0;
            if(quantityChange) newItem.setQuantity(data.quantity);
            if(tickChange) newItem.setTicked(data.ticked);
            if(quantityChange || tickChange)this.newItemRepo.save(newItem);
        } else {
            ExistingItem existingItem = this.existingItemRepo.findById(data.itemNo).orElseThrow(() -> new ResourceNotFoundException("Item not found!"));
            quantityChange = existingItem.getQuantity() != data.quantity;
            tickChange = Boolean.compare(existingItem.isTicked(), data.ticked) != 0;
            if(quantityChange) existingItem.setQuantity(data.quantity);
            if(tickChange) existingItem.setTicked(data.ticked);
            if(quantityChange || tickChange)this.existingItemRepo.save(existingItem);
        }
    }
}
