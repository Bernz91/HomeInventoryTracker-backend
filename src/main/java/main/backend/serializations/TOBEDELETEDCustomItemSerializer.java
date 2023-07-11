package main.backend.serializations;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import main.backend.entities.Alert;
import main.backend.entities.Item;
import main.backend.entities.Purchase;

import java.io.IOException;
import java.util.List;

public class TOBEDELETEDCustomItemSerializer extends JsonSerializer<Item>{
    @Override
    public void serialize (Item item, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException{
        if(item != null){
            jgen.writeStartObject();

            jgen.writeNumberField("itemNo", item.getItemNo());
            jgen.writeStringField("categoryNo",item.getCategoryNo().getCategoryName());
            jgen.writeStringField("itemName", item.getItemName());
            jgen.writeNumberField("totalQuantity", item.getTotalQuantity());

            List<Purchase> purchaseList = item.getPurchaseList();

            jgen.writeFieldName("purchases");
            jgen.writeStartArray();
            for (Purchase purchase:purchaseList) {
                jgen.writeStartObject();
                jgen.writeNumberField("purchaseNo", purchase.getPurchaseNo());
                jgen.writeStringField("purchaseDate",purchase.getPurchaseDate().toString());
                jgen.writeStringField("purchasedFrom", purchase.getPurchasedFrom());
                jgen.writeStringField("expiryDate", purchase.getExpiryDate() == null ? null : purchase.getExpiryDate().toString());
                jgen.writeNumberField("remainingQuantity", purchase.getRemainingQuantity());
                jgen.writeNumberField("originalQuantity", purchase.getOriginalQuantity());
                jgen.writeStringField("price", purchase.getPrice().toString());
                jgen.writeEndObject();
            }
            jgen.writeEndArray();

            jgen.writeFieldName("alert");
            Alert alert = item.getAlert();
            jgen.writeStartObject();
            jgen.writeBooleanField("lowInventoryAlert", alert.isLowInventoryAlert());
            jgen.writeNumberField("inventoryThreshold", alert.getInventoryThreshold());
            jgen.writeBooleanField("expiryDateAlert",alert.isExpiryDateAlert());
            jgen.writeNumberField("daysBeforeExpiryDate", alert.getDaysBeforeExpiryDate());
            jgen.writeEndObject();

            jgen.writeEndObject();
        }
    }
}
