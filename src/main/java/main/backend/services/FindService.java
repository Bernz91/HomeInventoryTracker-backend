package main.backend.services;

import main.backend.entities.*;
import main.backend.exceptions.ResourceNotFoundException;
import main.backend.models.GroceryDisplay;
import main.backend.models.ItemDetailDisplay;
import main.backend.models.ItemDisplay;
import main.backend.repositories.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FindService {
    private final UserRepo userRepo;
    private final ItemRepo itemRepo;
    private final CategoryRepo categoryRepo;
    private final CheckoutRecordRepo checkoutRecordRepo;
    private final ExistingItemRepo existingItemRepo;
    private final NewItemRepo newItemRepo;

    public FindService(UserRepo userRepo, ItemRepo itemRepo, CategoryRepo categoryRepo,
                       CheckoutRecordRepo checkoutRecordRepo, ExistingItemRepo existingItemRepo, NewItemRepo newItemRepo) {
        this.userRepo = userRepo;
        this.itemRepo = itemRepo;
        this.categoryRepo = categoryRepo;
        this.checkoutRecordRepo = checkoutRecordRepo;
        this.existingItemRepo = existingItemRepo;
        this.newItemRepo = newItemRepo;
    }

    public User findUserByEmail(String email){
        User user = this.userRepo.findUserByEmail(email);
        if(user == null) throw new ResourceNotFoundException("User not found.");
        return user;
    }

    public List<ItemDisplay> findItemsByUserId(String userId){
        List<Item> itemList = this.itemRepo.findByUserId(userId);
        List<ItemDisplay> itemDisplayList = new ArrayList<>();
        for(Item item: itemList){
            itemDisplayList.add(new ItemDisplay(item.getItemNo(), item.getCategoryNo().getCategoryName(), item.getItemName(), item.getTotalQuantity()));
        }
        return itemDisplayList;
    }

    public ItemDetailDisplay findItemById(int itemNo){
        Item item = this.itemRepo.findById(itemNo).orElseThrow(()->new ResourceNotFoundException("Item is not found for this itemNo: "+ itemNo));
        List<CheckoutRecord> checkoutRecordList = this.checkoutRecordRepo.findCheckoutRecordsByItemNo(itemNo);
        return new ItemDetailDisplay(item.getItemNo(), item.getItemName(), item.getCategoryNo().getCategoryName(), item.getTotalQuantity(),
               item.getPurchaseList(), checkoutRecordList, item.getAlert());
    }

    public List<GroceryDisplay> findGroceryListByUserId(String userId){

        List<GroceryDisplay> groceryDisplayList = new ArrayList<>();

        List<ExistingItem> existingItemList = this.existingItemRepo.findByUserId(userId);
        for(ExistingItem existingItem : existingItemList){
            Item item = this.itemRepo.findById(existingItem.getItemNo()).orElseThrow(()->new ResourceNotFoundException("item not found."));
            GroceryDisplay groceryDisplay = new GroceryDisplay(existingItem.getQuantity(), existingItem.isTicked(), existingItem.getItemNo(), item.getItemName(),existingItem.getUserId().getUserID());
            groceryDisplayList.add(groceryDisplay);
        }
        List<NewItem> newItemList = this.newItemRepo.findByUserId(userId);
        for(NewItem newItem : newItemList){
            GroceryDisplay groceryDisplay = new GroceryDisplay(newItem.getQuantity(), newItem.isTicked(), newItem.getItemName(),newItem.getUserId());
            groceryDisplayList.add(groceryDisplay);
        }
        return  groceryDisplayList;
    }

    public List<Category> findAllCategories(){
        return this.categoryRepo.findAll();
    }

    public List<CheckoutRecord> findCheckoutRecordByItemNo(int itemNo) {
        return this.checkoutRecordRepo.findCheckoutRecordsByItemNo(itemNo);
    }

}
