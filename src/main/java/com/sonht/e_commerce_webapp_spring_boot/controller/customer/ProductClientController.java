package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.sonht.e_commerce_webapp_spring_boot.dto.CommentDto;
import com.sonht.e_commerce_webapp_spring_boot.dto.ProductWishListDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.Comment;
import com.sonht.e_commerce_webapp_spring_boot.entity.Product;
import com.sonht.e_commerce_webapp_spring_boot.service.BrandService;
import com.sonht.e_commerce_webapp_spring_boot.service.CategoryService;
import com.sonht.e_commerce_webapp_spring_boot.service.ColorService;
import com.sonht.e_commerce_webapp_spring_boot.service.CommentService;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductClientController {
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ColorService colorService;
    private final ProductService productService;
    private final UserService userService;
    private final CommentService commentService;
    private final List<Integer> listSize = List.of(35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45);

    
    public ProductClientController(CategoryService categoryService, BrandService brandService,
            ColorService colorService, ProductService productService, UserService userService,
            CommentService commentService) {
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.colorService = colorService;
        this.productService = productService;
        this.userService = userService;
        this.commentService = commentService;
    }

    /*
     * Xử lý trang danh sách sản phẩm theo thương hiệu
     */
    @GetMapping("/product-listing/{brandName}")
    public String getProductListingPageByBrandName(@PathVariable String brandName, Model model) {
        // Lấy danh sách sản phẩm theo thương hiệu
        List<Product> products = brandService.findProductsByBrandName(brandName);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("sizes", listSize);
        model.addAttribute("colors", colorService.findAll());

        return "shopper/product-listing";
    }
    
    /*
     * Xử lý trang danh sách sản phẩm với các bộ lọc
     */
    @GetMapping("/product-listing")
    public String getProductListingPage(Model model,
            @RequestParam(required = false) Long categoryId, // id của category
            @RequestParam(required = false) Long brandId, // id của brand
            @RequestParam(required = false) Long priceMin, // giá min
            @RequestParam(required = false) Long priceMax, // giá max
            @RequestParam(required = false) Integer size,  // size giày
            @RequestParam(required = false) Long colorId // id của color
            ) {
        // Truyền dữ liệu cho các bộ lọc
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("sizes", listSize);
        model.addAttribute("colors", colorService.findAll());

        // Lấy danh sách sản phẩm theo tiêu chí của bộ lọc
        List<Product> products = null;
        if (categoryId == null && brandId == null && priceMin == null && priceMax == null && size == null
                && colorId == null) {
            products = productService.findAllByStatus("Đang bán");
        }
        else if (categoryId != null) {
            products = productService.filterProducts(categoryId, null, null, null, null, null);
        }
        else if (brandId != null) {
            products = brandService.findProductsByBrandId(brandId);
        }
        else if (priceMax != null && priceMin != null) {
            products = productService.filterProducts(null, null, priceMin, priceMax, null, null);
        }
        else if (size != null) {
            products = productService.filterProducts(null, null, null, null, size, null);
        }

        else if (colorId != null) {
            products = productService.filterProducts(null, null, null, null, null, colorId);
        }

        model.addAttribute("products", products); // danh sách sản phẩm sau khi lọc

        return "shopper/product-listing";
    }

    /*
     * Xử lý trang chi tiết sản phẩm
     */
    @GetMapping("/product/{id}")
    public String getProductDetailPage(@PathVariable Long id, Model model, HttpServletRequest request) {
        // Lấy thông tin sản phẩm theo id
        Product product = productService.findById(id);
        // Tạo đối tượng ProductWishListDto để kiểm tra sản phẩm đã được yêu thích hay chưa
        ProductWishListDto productWishListDto = new ProductWishListDto();
        productWishListDto.setProduct(product);
        productWishListDto.setWishlist(false);
        model.addAttribute("product", productWishListDto.getProduct());
        // Lấy thông tin người dùng đã đăng nhập
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        // Nếu chưa đăng nhập thì không cần lấy thông tin bình luận và trạng thái yêu thích
        if (username == null) {
            model.addAttribute("isSignedIn", false);
            return "shopper/product";
        }

        // Lấy danh sách bình luận của sản phẩm
        List<Comment> comments = commentService.findByProductIdOrderByCreatedAtDesc(id);
        // Thêm dữ liệu cho form bình luận
        CommentDto commentDto = new CommentDto();
        commentDto.setProductId(id);
        commentDto.setUsername(username);

        model.addAttribute("username", username);
        model.addAttribute("isSignedIn", true);
        model.addAttribute("isWishlist", productWishListDto.isWishlist());
        model.addAttribute("comments", comments);
        model.addAttribute("commentDto", commentDto);
        model.addAttribute("brands", brandService.findAll());

        return "shopper/product";
    }

    
    
}
