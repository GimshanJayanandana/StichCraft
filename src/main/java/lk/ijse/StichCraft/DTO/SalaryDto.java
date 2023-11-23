package lk.ijse.StichCraft.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SalaryDto {
    private String salary_id;
    private double amount;
    private LocalDate date;
    private String employee_id;
    private String name;
}
