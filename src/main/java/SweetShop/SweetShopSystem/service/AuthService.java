package SweetShop.SweetShopSystem.service;

import SweetShop.SweetShopSystem.dto.AuthRequestDTO;
import SweetShop.SweetShopSystem.dto.AuthResponseDTO;
import SweetShop.SweetShopSystem.dto.RegisterDTO;
import SweetShop.SweetShopSystem.entity.Role;
import SweetShop.SweetShopSystem.entity.User;
import SweetShop.SweetShopSystem.repository.UserRepository;
import SweetShop.SweetShopSystem.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // REGISTER USER
    public AuthResponseDTO register(RegisterDTO request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return AuthResponseDTO.builder()
                    .message("Email already exists!")
                    .build();
        }

        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return AuthResponseDTO.builder()
                .userId(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .role(user.getRole().name())
                .token(token)
                .message("Registered successfully!")
                .build();
    }

    // create first admin
    public AuthResponseDTO createFirstAdmin(RegisterDTO request) {

        // Check if an admin already exists
        boolean adminExists = userRepository.findAll().stream()
                .anyMatch(user -> user.getRole() == Role.ADMIN);

        if (adminExists) {
            return AuthResponseDTO.builder()
                    .message("First admin is already created!")
                    .build();
        }

        User admin = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();

        userRepository.save(admin);

        String token = jwtUtil.generateToken(admin.getEmail(), admin.getRole().name());

        return AuthResponseDTO.builder()
                .userId(admin.getId())
                .firstname(admin.getFirstname())
                .lastname(admin.getLastname())
                .email(admin.getEmail())
                .role(admin.getRole().name())
                .token(token)
                .message("First Admin created successfully!")
                .build();
    }


    // only admins can create admin
    public AuthResponseDTO createAdmin(RegisterDTO request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return AuthResponseDTO.builder()
                    .message("Email already exists!")
                    .build();
        }

        User admin = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();

        userRepository.save(admin);

        return AuthResponseDTO.builder()
                .userId(admin.getId())
                .firstname(admin.getFirstname())
                .lastname(admin.getLastname())
                .email(admin.getEmail())
                .role(admin.getRole().name())
                .message("Admin created successfully!")
                .build();
    }


    // LOGIN USER
    public AuthResponseDTO login(AuthRequestDTO request) {

        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null) {
            return AuthResponseDTO.builder()
                    .message("Invalid email!")
                    .build();
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return AuthResponseDTO.builder()
                    .message("Incorrect password!")
                    .build();
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return AuthResponseDTO.builder()
                .userId(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .role(user.getRole().name())
                .token(token)
                .message("Login successful!")
                .build();
    }
}
