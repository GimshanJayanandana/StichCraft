package lk.ijse.StichCraft.Entity;

import lk.ijse.StichCraft.DTO.tm.OrderTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    private String order_id;
    private LocalDate order_date;
    private String customer_id;
}
