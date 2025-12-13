package SweetShop.SweetShopSystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SweetRequestDTO {
    private String name;
    private String category;
    private double price;
    private int quantity;
}
