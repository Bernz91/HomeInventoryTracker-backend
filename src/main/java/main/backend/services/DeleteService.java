package main.backend.services;

import jakarta.transaction.Transactional;
import main.backend.entities.CheckoutRecord;
import main.backend.entities.Item;
import main.backend.entities.Purchase;
import main.backend.exceptions.ResourceNotFoundException;
import main.backend.repositories.*;
import org.springframework.stereotype.Service;


@Service
public class DeleteService {

    private ItemRepo itemRepo;
    private ExistingItemRepo existingItemRepo;
    private NewItemRepo newItemRepo;
    private CheckoutRecordRepo checkoutRecordRepo;
    private PurchaseRepo purchaseRepo;

    public DeleteService(ItemRepo itemRepo, ExistingItemRepo existingItemRepo, NewItemRepo newItemRepo,
                         CheckoutRecordRepo checkoutRecordRepo, PurchaseRepo purchaseRepo) {
        this.itemRepo = itemRepo;
        this.existingItemRepo = existingItemRepo;
        this.newItemRepo = newItemRepo;
        this.checkoutRecordRepo = checkoutRecordRepo;
        this.purchaseRepo = purchaseRepo;
    }

    public void deleteExistingItem(int itemNo){
        this.existingItemRepo.deleteById(itemNo);
    }

    @Transactional
    public void deleteNewItem(String userId, String itemName){
        this.newItemRepo.deleteByItemNameAndUserId(userId, itemName);
    }

    @Transactional
    public void deleteCheckoutRecord(int checkoutNo){
        CheckoutRecord checkoutRecord = this.checkoutRecordRepo.findById(checkoutNo).orElseThrow(()->new ResourceNotFoundException("Check Out record not found!"));
        Purchase purchase = this.purchaseRepo.findById(checkoutRecord.getPurchaseNo().getPurchaseNo()).orElseThrow(()-> new ResourceNotFoundException("Purchase record not found!"));
        purchase.setRemainingQuantity(purchase.getRemainingQuantity() + checkoutRecord.getQuantity());

        this.purchaseRepo.save(purchase);

        Item item = this.itemRepo.findById(purchase.getItemNo().getItemNo()).orElseThrow(()-> new ResourceNotFoundException("Item not found!"));
        item.setTotalQuantity(item.getTotalQuantity()+checkoutRecord.getQuantity());

        this.itemRepo.save(item);

        this.checkoutRecordRepo.deleteById(checkoutNo);
    }
}
