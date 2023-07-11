package main.backend.serializations;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import main.backend.entities.Item;
import main.backend.entities.User;

import java.io.IOException;
import java.util.List;

public class CustomUserSerializer extends JsonSerializer<User>{
    @Override
    public void serialize (User user, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException{
        if(user != null){
            jgen.writeStartObject();

            jgen.writeStringField("userId", user.getUserID().toString());
            jgen.writeStringField("email", user.getEmail());

            List<Item> itemList = user.getItemList();

            jgen.writeFieldName("items");
            jgen.writeStartArray();
            for (Item item:itemList) {
                jgen.writeStartObject();
                jgen.writeNumberField("itemNo", item.getItemNo());
                jgen.writeStringField("categoryName",item.getCategoryNo().getCategoryName());
                jgen.writeStringField("itemName", item.getItemName());
                jgen.writeNumberField("totalQuantity", item.getTotalQuantity());
                jgen.writeEndObject();
            }
            jgen.writeEndArray();

//            jgen.writeFieldName("groceryItems");
//            jgen.writeStartArray();
//            List<NewItem> newItemList = user.getNewItemList();
//            for(NewItem newItem: newItemList){
//                jgen.writeStartObject();
//                jgen.writeStringField("itemName", newItem.getItemName());
//                jgen.writeNumberField("quantity",newItem.getQuantity());
//                jgen.writeBooleanField("ticked", newItem.isTicked());
//                jgen.writeEndObject();
//            }
//
//            List<ExistingItem> existingItemList = user.getExistingItemList();
//            for(ExistingItem existingItem: existingItemList){
//                jgen.writeStartObject();
//                jgen.writeStringField("itemName", existingItem.getItemNo());
//                jgen.writeNumberField("quantity",existingItem.getQuantity());
//                jgen.writeBooleanField("ticked", existingItem.isTicked());
//                jgen.writeEndObject();
//            }
//            jgen.writeEndArray();

            jgen.writeEndObject();
        }
    }
}
