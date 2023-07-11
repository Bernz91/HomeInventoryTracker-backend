package main.backend.controller;

import main.backend.entities.*;
import main.backend.models.*;
import main.backend.services.AddService;
import main.backend.services.DeleteService;
import main.backend.services.EditService;
import main.backend.services.FindService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.UUID;

@RestController
public class Controller {
    private final AddService addService;
    private final FindService findService;
    private final EditService editService;
    private final DeleteService deleteService;
    public Controller(AddService addService, FindService findService, EditService editService, DeleteService deleteService) {
        this.addService = addService;
        this.findService = findService;
        this.editService = editService;
        this.deleteService = deleteService;
    }

    @CrossOrigin(origins="http://localhost:3000/")
    @GetMapping("/user")
    public ResponseEntity<User> getUserByEmail(@RequestParam(value="email") String email) throws IllegalArgumentException{
        User user = findService.findUserByEmail(email);
        return ResponseEntity.ok().body(user);
    }
    @CrossOrigin(origins="http://localhost:3000/")
    @GetMapping("/item/{itemNo}")
    public ResponseEntity<ItemDetailDisplay> getItemById(@PathVariable(value="itemNo") int itemNo) throws IllegalArgumentException{
        ItemDetailDisplay itemDisplay = this.findService.findItemById(itemNo);
        return ResponseEntity.ok().body(itemDisplay);
    }
    @CrossOrigin(origins="http://localhost:3000/")
    @GetMapping("/user/{userId}/grocery")
    public ResponseEntity<List<GroceryDisplay>> getGroceryByUserId(@PathVariable(value="userId") UUID userId) throws IllegalArgumentException{
        List<GroceryDisplay> groceryList = this.findService.findGroceryListByUserId(userId);
        return ResponseEntity.ok().body(groceryList);
    }
    @CrossOrigin(origins="http://localhost:3000/")
    @GetMapping("/user/{userId}/item")
    public ResponseEntity<List<ItemDisplay>> getItemsByUserId(@PathVariable(value="userId") UUID userId) throws IllegalArgumentException{
        List<ItemDisplay> itemDisplayList = this.findService.findItemsByUserId(userId);
        return ResponseEntity.ok().body(itemDisplayList);
    }
    @CrossOrigin(origins="http://localhost:3000/")
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() throws IllegalArgumentException{
        List<Category> categoryList = this.findService.findAllCategories();
        return ResponseEntity.ok().body(categoryList);
    }

    @CrossOrigin(origins="http://localhost:3000/")
    @GetMapping("/checkout/{itemNo}")
    public ResponseEntity<List<CheckoutRecord>> getCheckoutRecordByItemNo(@PathVariable(value="itemNo") int itemNo) throws IllegalArgumentException{
        List<CheckoutRecord> checkoutRecordList = this.findService.findCheckoutRecordByItemNo(itemNo);
        return ResponseEntity.ok().body(checkoutRecordList);
    }

    @CrossOrigin(origins="http://localhost:3000/")
    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody Email email) throws IllegalArgumentException{
        try{
            this.addService.addUser(email.email);
            return ResponseEntity.status(201).build();
        } catch(IllegalArgumentException e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid operation.");
    }

    @CrossOrigin(origins="http://localhost:3000/")
    @PostMapping("/item")
    public ResponseEntity<?> addNewItem(@RequestBody AddNewItem newItem){
        try{
            this.addService.addNewItem(newItem);
            return ResponseEntity.status(201).build();
        } catch(IllegalArgumentException e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid operation.");
    }
    @CrossOrigin(origins="http://localhost:3000/")
    @PostMapping("/item/purchase")
    public ResponseEntity<?> addStock(@RequestBody Purchase purchase){
        try{
            if(purchase.isValid()){
                this.addService.addStock(purchase);
                return ResponseEntity.status(201).build();
            }
        } catch(IllegalArgumentException e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantity must be at least 1.");
    }
    @CrossOrigin(origins="http://localhost:3000/")
    @PostMapping("/grocery")
    public ResponseEntity<?> addGrocery(@RequestBody AddGrocery data){
        try{
            if(data.isValid()){
                this.addService.addGroceryItem(data);
                return ResponseEntity.status(201).build();
            }
        } catch(IllegalArgumentException e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input.");
    }
    @CrossOrigin(origins="http://localhost:3000/")
    @PostMapping("/checkout")
    public ResponseEntity<?> addCheckout(@RequestBody AddCheckout data){
        try{
            if(data.isValid()){
                this.addService.addCheckoutRecord(data);
                return ResponseEntity.status(201).build();
            }
        } catch(IllegalArgumentException e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input.");
    }

    @CrossOrigin(origins="http://localhost:3000/")
    @PutMapping("/item")
    public ResponseEntity<?> changeItemDetail(@RequestBody ChangeItemDetail requestBody){
        try{
            if(requestBody.isValid()){
                this.editService.changeItemDetail(requestBody);
                return ResponseEntity.status(201).build();
            }
        } catch(IllegalArgumentException e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Item name cannot be empty.");
    }
    @CrossOrigin(origins="http://localhost:3000/")
    @PutMapping("/item/purchase")
    public ResponseEntity<?> changePurchaseDetail(@RequestBody ChangePurchaseDetail requestBody){
        try{
            if(requestBody.isValid()){
                this.editService.changePurchaseDetail(requestBody);
                return ResponseEntity.status(201).build();
            }
        } catch(IllegalArgumentException e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input.");
    }

    @CrossOrigin(origins="http://localhost:3000/")
    @PutMapping("/grocery")
    public ResponseEntity<?> changeGroceryQuantity(@RequestBody GroceryDisplay requestBody){
        try{
            if(requestBody.isValid()){
                this.editService.changeGroceryDetail(requestBody);
                return ResponseEntity.status(201).build();
            }
        } catch(IllegalArgumentException e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input.");
    }

    @CrossOrigin(origins="http://localhost:3000/")
    @DeleteMapping("/grocery/existing/{itemNo}")
    public ResponseEntity<?> deleteExistingItem(@PathVariable(value="itemNo") int itemNo ) throws IllegalArgumentException{
        this.deleteService.deleteExistingItem(itemNo);
        return ResponseEntity.status(204).build();
    }

    @CrossOrigin(origins="http://localhost:3000/")
    @DeleteMapping("/grocery/new/{userId}")
    public ResponseEntity<?> deleteNewItem(@PathVariable(value="userId") UUID userId, @RequestParam String itemName) throws IllegalArgumentException{
        this.deleteService.deleteNewItem(userId, itemName);
        return ResponseEntity.status(204).build();
    }

    @CrossOrigin(origins="http://localhost:3000/")
    @DeleteMapping("/checkout/{checkoutNo}")
    public ResponseEntity<?> deleteCheckoutRecord(@PathVariable(value="checkoutNo") int checkoutNo ) throws IllegalArgumentException{
        this.deleteService.deleteCheckoutRecord(checkoutNo);
        return ResponseEntity.status(204).build();
    }
}
