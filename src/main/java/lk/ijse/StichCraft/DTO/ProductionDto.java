package lk.ijse.StichCraft.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductionDto {
    private String production_id;
    private String production_type;
    private LocalDate StartDate;
    private LocalDate EndDate;
    private String Description;
}
