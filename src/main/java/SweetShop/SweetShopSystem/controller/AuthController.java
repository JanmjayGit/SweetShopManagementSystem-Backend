package SweetShop.SweetShopSystem.controller;

import SweetShop.SweetShopSystem.dto.AuthRequestDTO;
import SweetShop.SweetShopSystem.dto.AuthResponseDTO;
import SweetShop.SweetShopSystem.dto.RegisterDTO;
import SweetShop.SweetShopSystem.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // register
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    // login
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/create-first-admin")
    public ResponseEntity<AuthResponseDTO> createFirstAdmin(@RequestBody RegisterDTO request) {
        return ResponseEntity.ok(authService.createFirstAdmin(request));
    }


}
