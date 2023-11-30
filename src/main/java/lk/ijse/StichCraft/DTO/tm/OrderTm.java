package lk.ijse.StichCraft.DTO.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderTm {
    private String productId;
    private String description;
    private int quantity;
    private double unitPrice;
    private double total;
}
