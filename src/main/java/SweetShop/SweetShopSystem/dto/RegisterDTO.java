package SweetShop.SweetShopSystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
