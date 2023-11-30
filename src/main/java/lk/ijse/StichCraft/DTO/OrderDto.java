package lk.ijse.StichCraft.DTO;

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
public class OrderDto {
    private String order_id;
    private LocalDate order_date;
    private String customer_id;
    private List<OrderTm> orderTmList = new ArrayList<>();
}
