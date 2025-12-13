package SweetShop.SweetShopSystem.service;

import SweetShop.SweetShopSystem.dto.SweetRequestDTO;
import SweetShop.SweetShopSystem.dto.SweetUpdateRequestDTO;
import SweetShop.SweetShopSystem.entity.Sweet;
import SweetShop.SweetShopSystem.repository.SweetRepository;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SweetService {

    private final SweetRepository sweetRepository;
    private final Cloudinary cloudinary;

    // ADMIN ADDS SWEET
    public Sweet addSweet(SweetRequestDTO dto) {
        Sweet sweet = Sweet.builder()
                .name(dto.getName())
                .category(dto.getCategory())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .build();
        return sweetRepository.save(sweet);
    }

    // GET ALL SWEETS
    public List<Sweet> getAllSweets() {
        return sweetRepository.findAll();
    }

    // SEARCH SWEETS
    public List<Sweet> search(String name, String category, Double minPrice, Double maxPrice) {
        return sweetRepository.search(name, category, minPrice, maxPrice);
    }

    // UPDATE SWEET
    public Sweet updateSweet(Long id, SweetUpdateRequestDTO dto) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        sweet.setName(dto.getName());
        sweet.setCategory(dto.getCategory());
        sweet.setPrice(dto.getPrice());
        sweet.setQuantity(dto.getQuantity());

        return sweetRepository.save(sweet);
    }

    // DELETE SWEET
    public void deleteSweet(Long id) {
        sweetRepository.deleteById(id);
    }

    // USER PURCHASE SWEET
    public Sweet purchaseSweet(Long id, int quantity) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        if (sweet.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock");
        }

        sweet.setQuantity(sweet.getQuantity() - quantity);

        return sweetRepository.save(sweet);
    }

    // ADMIN RESTOCK SWEET
    public Sweet restockSweet(Long id, int quantity) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        sweet.setQuantity(sweet.getQuantity() + quantity);

        return sweetRepository.save(sweet);
    }


    public Sweet uploadImage(Long id, MultipartFile file) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        try {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap("folder", "sweetshop")
            );

            String imageUrl = uploadResult.get("secure_url").toString();

            sweet.setImageUrl(imageUrl);

            return sweetRepository.save(sweet);

        } catch (Exception e) {
            throw new RuntimeException("Error uploading to Cloudinary: " + e.getMessage());
        }
    }

}
