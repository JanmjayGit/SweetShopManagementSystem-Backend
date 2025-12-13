package SweetShop.SweetShopSystem.controller;

import SweetShop.SweetShopSystem.dto.AuthResponseDTO;
import SweetShop.SweetShopSystem.dto.RegisterDTO;
import SweetShop.SweetShopSystem.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuthService authService;

    // ADMIN Creates Another Admin
    @PostMapping("/create-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthResponseDTO> createAdmin(@RequestBody RegisterDTO request) {
        return ResponseEntity.ok(authService.createAdmin(request));
    }
}
