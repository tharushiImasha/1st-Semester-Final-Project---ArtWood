package lk.ijse.FianlArtWood.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor

public class EmployeeTm {
    private String emp_id;
    private String name;
    private String address;
    private int tel;
    private String job_role;
    private String status;
}
