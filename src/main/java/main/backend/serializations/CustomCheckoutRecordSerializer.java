package main.backend.serializations;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import main.backend.entities.CheckoutRecord;
import main.backend.entities.Purchase;

import java.io.IOException;

public class CustomCheckoutRecordSerializer extends JsonSerializer<CheckoutRecord>{
    @Override
    public void serialize (CheckoutRecord checkoutRecord, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException{
        if(checkoutRecord != null){
            jgen.writeStartObject();

            Purchase purchase = checkoutRecord.getPurchaseNo();

            jgen.writeNumberField("checkoutNo", checkoutRecord.getCheckoutNo());
            jgen.writeNumberField("purchaseNo",purchase.getPurchaseNo());
            jgen.writeNumberField("quantity", checkoutRecord.getQuantity());
            jgen.writeStringField("purchaseDate",purchase.getPurchaseDate().toString());
            jgen.writeStringField("checkoutDate", checkoutRecord.getCheckoutDate().toString());

            jgen.writeEndObject();
        }
    }
}
