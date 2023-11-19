package lk.ijse.FianlArtWood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class EmployeeDto {
    private String emp_id;
    private String name;
    private String address;
    private int tel;
    private String status;
    private String job_role;

    public EmployeeDto(String emp_id ,String name, String address, int tel, String job_role){
        this.address = address;
        this.emp_id = emp_id;
        this.name = name;
        this.tel = tel;
        this.job_role = job_role;
    }
}
