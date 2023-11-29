package lk.ijse.StichCraft.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {
    private String order_id;
    private LocalDate order_date;
    private double price;
    private String description;
    private String customer_id;
}
