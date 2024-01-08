package lk.ijse.StichCraft.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {
    private String employee_id;
    private String name;
    private String address;
    private String contact;
}
