package lk.ijse.StichCraft.DTO.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductionTm {
    private String production_id;
    private String production_type;
    private String StartDate;
    private String EndDate;
    private String Description;
}
