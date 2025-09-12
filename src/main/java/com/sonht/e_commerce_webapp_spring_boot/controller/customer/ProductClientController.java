package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import java.util.List;

import org.hibernate.annotations.Comments;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sonht.e_commerce_webapp_spring_boot.dto.CommentDto;
import com.sonht.e_commerce_webapp_spring_boot.dto.ProductWishListDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.Comment;
import com.sonht.e_commerce_webapp_spring_boot.entity.Product;
import com.sonht.e_commerce_webapp_spring_boot.entity.User;
import com.sonht.e_commerce_webapp_spring_boot.service.BrandService;
import com.sonht.e_commerce_webapp_spring_boot.service.CategoryService;
import com.sonht.e_commerce_webapp_spring_boot.service.ColorService;
import com.sonht.e_commerce_webapp_spring_boot.service.CommentService;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductSizeService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserWishListService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ProductClientController {
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ProductSizeService productSizeService;
    private final ColorService colorService;
    private final ProductService productService;
    private final UserWishListService userWishlistService;
    private final UserService userService;
    private final CommentService commentService;

    public ProductClientController(CategoryService categoryService, BrandService brandService,
            ProductSizeService productSizeService, ColorService colorService, ProductService productService,
            UserWishListService userWishlistService, UserService userService, CommentService commentService) {
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.productSizeService = productSizeService;
        this.colorService = colorService;
        this.productService = productService;
        this.userWishlistService = userWishlistService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping("/product-listing")
    public String getProductListingPage(Model model,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long priceMin,
            @RequestParam(required = false) Long priceMax,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Long colorId) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("sizes", productSizeService.findAll());
        model.addAttribute("colors", colorService.findAll());
        List<Product> products = null;
        if (categoryId == null && brandId == null && priceMin == null && priceMax == null && size == null
                && colorId == null) {
            products = productService.findAllByStatus("Đang bán");
        }
        if (categoryId != null) {
            products = productService.filterProducts(categoryId, null, null, null, null, null);
        }
        if (brandId != null) {
            products = brandService.findProductsByBrandId(brandId);
        }
        if (priceMax != null) {
            products = productService.filterProducts(null, null, null, priceMax, null, null);
        }
        if (priceMax != null && priceMin != null) {
            products = productService.filterProducts(null, null, priceMin, priceMax, null, null);
        }
        if (size != null) {
            products = productService.filterProducts(null, null, null, null, size, null);
        }

        if (colorId != null) {
            products = productService.filterProducts(null, null, null, null, null, colorId);
        }

        model.addAttribute("products", products);

        return "shopper/product-listing";
    }

    @GetMapping("/product/{id}")
    public String getProductDetailPage(@PathVariable Long id, Model model, HttpServletRequest request) {
        Product product = productService.findById(id);
        ProductWishListDto productWishListDto = new ProductWishListDto();
        productWishListDto.setProduct(product);
        productWishListDto.setWishlist(false);
        model.addAttribute("product", productWishListDto.getProduct());
        // Lấy thông tin người dùng đã đăng nhập
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username == null) {
            model.addAttribute("isSignedIn", false);
            return "shopper/product";
        }
        List<Comment> comments = commentService.findByLastCreatedAt(id);
        // Thêm dữ liệu cho form bình luận
        CommentDto commentDto = new CommentDto();
        commentDto.setProductId(id);
        commentDto.setUsername(username);

        model.addAttribute("username", username);
        model.addAttribute("isSignedIn", true);
        model.addAttribute("isWishlist", productWishListDto.isWishlist());
        model.addAttribute("comments", comments);
        model.addAttribute("commentDto", commentDto);

        return "shopper/product";
    }

    @PostMapping("/saveComment")
    public String saveCommentFromProductPage(@Valid @ModelAttribute CommentDto commentDto, BindingResult bindingResult,
            Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            // Nếu có lỗi thì trả về trang cũ và hiển thị thông báo
            Product product = productService.findById(commentDto.getProductId());
            ProductWishListDto productWishListDto = new ProductWishListDto();
            productWishListDto.setProduct(product);
            productWishListDto.setWishlist(false);
            model.addAttribute("product", productWishListDto.getProduct());
            // Lấy thông tin người dùng đã đăng nhập
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");
            if (username == null) {
                model.addAttribute("isSignedIn", false);
                return "shopper/product";
            }
            List<Comment> comments = commentService.findByLastCreatedAt(commentDto.getProductId());

            model.addAttribute("username", username);
            model.addAttribute("isSignedIn", true);
            model.addAttribute("isWishlist", productWishListDto.isWishlist());
            model.addAttribute("comments", comments);
            model.addAttribute("commentDto", commentDto);
            return "shopper/product";
        }
        Comment comment = new Comment();
        comment.setCommentMessage(commentDto.getCommentMessage());
        comment.setRate(commentDto.getRate());
        comment.setProduct(productService.findById(commentDto.getProductId()));
        comment.setUser(userService.findByEmail(commentDto.getUsername()));
        comment.setCreatedAt(java.time.LocalDateTime.now());
        comment.setUpdatedAt(java.time.LocalDateTime.now());
        commentService.saveComment(comment);
        

        return "redirect:/product/" + commentDto.getProductId();
    }
    
}
