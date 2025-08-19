package com.sonht.e_commerce_webapp_spring_boot.controller.admin;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sonht.e_commerce_webapp_spring_boot.dto.ProductDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.Brand;
import com.sonht.e_commerce_webapp_spring_boot.entity.Category;
import com.sonht.e_commerce_webapp_spring_boot.entity.Product;
import com.sonht.e_commerce_webapp_spring_boot.entity.User;
import com.sonht.e_commerce_webapp_spring_boot.repository.BrandRepository;
import com.sonht.e_commerce_webapp_spring_boot.service.BrandService;
import com.sonht.e_commerce_webapp_spring_boot.service.CategoryService;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;
import com.sonht.e_commerce_webapp_spring_boot.service.impl.UploadService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("admin")
public class ProductController {

    private final UserService userService;

    private final ProductService productService;
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final List<String> statuses = List.of("Đang bán", "Ngừng bán");

    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

    public ProductController(ProductService productService, BrandService brandService,
            UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.productService = productService;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    /*
     * Handles the display of the products page.
     */
    @GetMapping("/products")
    public String productsData(Model model) {

        List<ProductDto> products = productService.findAllByIsDelete(false).stream()
                .map(product -> new ProductDto(product.getId(), product.getName(), product.getVersionName(),
                        product.getDescription(), product.getPrice(), false,
                        product.getStatus(), null, null, null))
                .toList();

        model.addAttribute("products", products);
        // get user from spring security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // get user from security context
        User user = userService.findByEmail(username);
        model.addAttribute("username", username);

        return "admin/products";
    }

    /*
     * Handles the display of the add new product page.
     */
    @GetMapping("/products/create")
    public String getAddNewProductPage(Model model) {
        model.addAttribute("newProduct", new ProductDto());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("statuses", statuses);

        return "admin/products/add-product";
    }

    /*
     * Handles the display of the update product page.
     */
    @GetMapping("/products/update/{productId}")
    public String getAddUpdateProductPage(Model model, @PathVariable("productId") Long productId) {
        Product product = productService.findById(productId);

        ProductDto productDto = new ProductDto(
                product.getId(),
                product.getName(),
                product.getVersionName(),
                product.getDescription(),
                product.getPrice(),
                false,
                product.getStatus(),
                product.getBrand() != null ? product.getBrand().getName() : null,
                product.getCategory() != null ? product.getCategory().getName() : null,
                product.primaryImage() != null ? product.primaryImage() : null
                );
        model.addAttribute("currentProduct", productDto);
        String urlImage = product.primaryImage() != null ? product.primaryImage() : null;
        model.addAttribute("urlImage", urlImage);
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("statuses", statuses);

        return "admin/products/update-product";
    }

    /*
     * Handles the creation of a new product.
     */
    @PostMapping("/products/create")
    public String createNewProduct(@Valid @ModelAttribute("newProduct") ProductDto newProduct,
            BindingResult result,
            Model model,
            @RequestParam("imageFile") MultipartFile file) throws IOException {
        List<FieldError> errors = result.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>" + error.getField() + " - " + error.getDefaultMessage());
        }
        if (result.hasErrors()) {
            model.addAttribute("newProduct", newProduct);
            model.addAttribute("brands", brandService.findAll());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("statuses", statuses);
            return "admin/products/add-product";
        }

        // Validate image
        if (file.isEmpty()) {
            model.addAttribute("imageError", "Vui lòng chọn ảnh sản phẩm");
            return "admin/products/add-product";
        }

        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("uploaded/images");
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        Path filepath = CURRENT_FOLDER.resolve(staticPath)
                .resolve(imagePath).resolve(file.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(file.getBytes());
        }

        newProduct.setImageUrl(file.getOriginalFilename());

        // Save product
        productService.createProduct(newProduct);
        return "redirect:/admin/products";
    }

    /*
     * Handles the update of an existing product.
     */
    @PostMapping("/products/update")
    public String handleUpdateProduct(@Valid @ModelAttribute("currentProduct") ProductDto currentProduct,
            BindingResult result,
            Model model,
            @RequestParam("imageFile") MultipartFile file) throws IOException {

        // Check if product is valid
        if (result.hasErrors()) {
            model.addAttribute("currentProduct", currentProduct);
            model.addAttribute("brands", brandService.findAll());
            model.addAttribute("statuses", statuses);
            model.addAttribute("urlImage", file.getOriginalFilename());

            return "admin/products/update-product";
        }

        // Validate image
        if (file.isEmpty()) {
            model.addAttribute("imageError", "Vui lòng chọn ảnh sản phẩm");
            model.addAttribute("currentProduct", currentProduct);
            model.addAttribute("urlImage", currentProduct.getImageUrl());
            model.addAttribute("brands", brandService.findAll());
            model.addAttribute("statuses", statuses);
            return "admin/products/update-product";
        }
        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("uploaded/images");
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        Path filepath = CURRENT_FOLDER.resolve(staticPath)
                .resolve(imagePath).resolve(file.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(file.getBytes());
        }
        currentProduct.setImageUrl(file.getOriginalFilename());

        // Save product
        productService.updateProduct(currentProduct);
        return "redirect:/admin/products";
    }

    @PostMapping("/products/stop-sale/{productId}")
    @ResponseBody
    public List<ProductDto> handleUpdateStatus(@PathVariable("productId") Long productId) {
        if (productId != null) {

            productService.updateStatusProduct(productId);

            return productService.findAllByIsDelete(false).stream()
                    .map(product -> new ProductDto(product.getId(), product.getName(), product.getVersionName(),
                            product.getDescription(), product.getPrice(), false,
                            product.getStatus(), null, null, null))
                    .toList();

        }
        return new ArrayList<ProductDto>(); // Trả về danh sách rỗng nếu productId không hợp lệ
    }

    @PostMapping("/products/{productId}")
    @ResponseBody
    public List<ProductDto> handleDeleteProduct(@PathVariable("productId") Long productId) {
        if (productId != null) {

            productService.deleteProductById(productId);
            List<ProductDto> products = productService.findAllByIsDelete(false).stream()
                    .map(product -> new ProductDto(product.getId(), product.getName(), product.getVersionName(),
                            product.getDescription(), product.getPrice(), false,
                            product.getStatus(), null, null, null))
                    .toList();
            return products;// Trả về trang danh sách sản phẩm sau khi xóa
            // return "redirect:/admin/products";
        }
        return new ArrayList<ProductDto>(); // Trả về danh sách rỗng nếu productId không hợp lệ
    }

}
