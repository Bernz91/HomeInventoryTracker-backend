package main.backend.serializations;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import main.backend.entities.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CustomPurchaseDeserializer extends JsonDeserializer<Purchase> {
    @Override
    public Purchase deserialize(JsonParser parser, DeserializationContext deCtxt)
            throws IOException, JacksonException {
        Purchase purchase = null;
        JsonNode jsonNode = parser.getCodec().readTree(parser);

        try {
            purchase = new Purchase();

            Item item = new Item();
            item.setItemNo(jsonNode.get("itemNo").asInt());
            purchase.setItemNo(item);

            purchase.setPurchaseDate(LocalDate.parse(jsonNode.get("purchaseDate").asText()));
            purchase.setPurchasedFrom(jsonNode.get("purchasedFrom").asText().trim().equals("") ? null : jsonNode.get("purchasedFrom").asText().trim());
            purchase.setExpiryDate(jsonNode.get("expiryDate").asText().trim().equals("") ? null : LocalDate.parse(jsonNode.get("expiryDate").asText()));
            purchase.setRemainingQuantity(jsonNode.get("quantity").asInt());
            purchase.setOriginalQuantity(jsonNode.get("quantity").asInt());
            purchase.setPrice(BigDecimal.valueOf(jsonNode.get("price").asDouble()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return purchase;
    }
}
