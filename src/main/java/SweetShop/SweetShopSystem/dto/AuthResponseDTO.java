package SweetShop.SweetShopSystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {

    private Long userId;
    private String firstname;
    private String lastname;
    private String email;
    private String role;

    private String token;
    private String message;
}
