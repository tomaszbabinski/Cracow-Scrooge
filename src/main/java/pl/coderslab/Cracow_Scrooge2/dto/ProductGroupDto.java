package pl.coderslab.Cracow_Scrooge2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductGroupDto {

    private Long id;

    @NotEmpty(message = "*Please name the category")
    @Size(min = 2, max = 30)
    private String name;

}
