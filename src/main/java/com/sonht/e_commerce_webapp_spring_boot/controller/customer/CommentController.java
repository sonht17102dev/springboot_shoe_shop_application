package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sonht.e_commerce_webapp_spring_boot.dto.CommentDto;
import com.sonht.e_commerce_webapp_spring_boot.dto.ProductWishListDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.Comment;
import com.sonht.e_commerce_webapp_spring_boot.entity.Product;
import com.sonht.e_commerce_webapp_spring_boot.service.CommentService;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class CommentController {
    

    private ProductService productService;
    private CommentService commentService;
    private UserService userService;

    public CommentController(ProductService productService, CommentService commentService, UserService userService) {
        this.productService = productService;
        this.commentService = commentService;
        this.userService = userService;
    }
    /*
     * Xử lý lưu bình luận từ trang chi tiết sản phẩm
     */
    @PostMapping("/saveComment")
    public String saveCommentFromProductPage(@Valid @ModelAttribute CommentDto commentDto, BindingResult bindingResult,
            Model model, HttpServletRequest request) {
        // Validate dữ liệu từ form
        if (bindingResult.hasErrors()) {
            // Nếu có lỗi thì trả về trang product và hiển thị thông báo
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
            // Lấy danh sách bình luận theo id product
            List<Comment> comments = commentService.findByProductIdOrderByCreatedAtDesc(commentDto.getProductId());

            model.addAttribute("username", username);
            model.addAttribute("isSignedIn", true);
            model.addAttribute("isWishlist", productWishListDto.isWishlist());
            model.addAttribute("comments", comments);
            model.addAttribute("commentDto", commentDto);
       
            return "shopper/product";
        }
        // Mapping dữ liệu commentDto -> comment và lưu bình luận vào database
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
