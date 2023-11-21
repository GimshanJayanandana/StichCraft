package lk.ijse.StichCraft.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDto {
    private String employee_id;
    private String name;
    private String address;
    private String contact;
}
