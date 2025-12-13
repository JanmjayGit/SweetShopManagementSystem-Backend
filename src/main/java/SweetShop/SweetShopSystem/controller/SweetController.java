package SweetShop.SweetShopSystem.controller;

import SweetShop.SweetShopSystem.dto.PurchaseRequestDTO;
import SweetShop.SweetShopSystem.dto.RestockRequestDTO;
import SweetShop.SweetShopSystem.dto.SweetRequestDTO;
import SweetShop.SweetShopSystem.dto.SweetUpdateRequestDTO;
import SweetShop.SweetShopSystem.entity.Sweet;
import SweetShop.SweetShopSystem.service.SweetService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/sweets")
@RequiredArgsConstructor
public class SweetController {

    private final SweetService sweetService;

    // ADD SWEET (Admin)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Sweet> addSweet(@RequestBody SweetRequestDTO dto) {
        return ResponseEntity.ok(sweetService.addSweet(dto));
    }

    // GET ALL SWEETS
    @GetMapping
    public ResponseEntity<List<Sweet>> getAll() {
        return ResponseEntity.ok(sweetService.getAllSweets());
    }

    // SEARCH SWEETS
    @GetMapping("/search")
    public ResponseEntity<List<Sweet>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return ResponseEntity.ok(
                sweetService.search(name, category, minPrice, maxPrice)
        );
    }

    // UPDATE SWEET (Admin)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Sweet> updateSweet(
            @PathVariable Long id,
            @RequestBody SweetUpdateRequestDTO dto
    ) {
        return ResponseEntity.ok(sweetService.updateSweet(id, dto));
    }

    // DELETE SWEET (Admin Only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteSweet(@PathVariable Long id) {
        sweetService.deleteSweet(id);
        return ResponseEntity.ok("Sweet deleted successfully!");
    }

    // PURCHASE SWEET (User)
    @PostMapping("/{id}/purchase")
    public ResponseEntity<Sweet> purchaseSweet(
            @PathVariable Long id,
            @RequestBody PurchaseRequestDTO dto
    ) {
        return ResponseEntity.ok(
                sweetService.purchaseSweet(id, dto.getQuantity())
        );
    }

    // RESTOCK SWEET (Admin Only)
    @PostMapping("/{id}/restock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Sweet> restockSweet(
            @PathVariable Long id,
            @RequestBody RestockRequestDTO dto
    ) {
        return ResponseEntity.ok(
                sweetService.restockSweet(id, dto.getQuantity())
        );
    }

    @PostMapping("/{id}/upload-image")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Sweet> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) {
        return ResponseEntity.ok(sweetService.uploadImage(id, file));
    }

}
