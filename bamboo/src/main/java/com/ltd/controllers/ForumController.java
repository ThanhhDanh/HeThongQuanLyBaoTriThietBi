/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.controllers;

import com.ltd.pojo.Comment;
import com.ltd.pojo.Forum;
import com.ltd.pojo.ForumDTO;
import com.ltd.pojo.User;
import com.ltd.service.CommentService;
import com.ltd.service.ForumService;
import com.ltd.service.ForumViewService;
import com.ltd.service.UserService;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Acer
 */
@Controller
public class ForumController {

    @Autowired
    private ForumService forumService;

    @Autowired
    private ForumViewService forumViewService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/forum/{forumId}", produces = "text/html; charset=UTF-8")
    public String showForumDetail(@PathVariable("forumId") int forumId, Model model, Principal principal) {

        // Lấy forum và user
        Forum forum = forumService.findById(forumId);
        User currentUser = userService.getUserByUsername(principal.getName());
        ForumDTO forumDTO = forumService.getForumWithComments(forumId);

        // Thêm người xem vào forum_view
        forumViewService.saveForumView(forum, currentUser);

        // Lấy danh sách người dùng đã xem forum
        List<User> usersViewed = forumViewService.getViewers(forumId);

        //Lấy danh sách diễn dàn khác
        List<Forum> otherForums = forumService.getAllPosts()
                .stream()
                .filter(f -> f.getId() != forumId) // Loại trừ diễn đàn hiện tại
                .collect(Collectors.toList());

        Comment comment = new Comment();
        model.addAttribute("comment", comment);

        // Truyền thông tin vào model
        model.addAttribute("forumDTO", new ForumDTO());
        model.addAttribute("forum", forumDTO);
        model.addAttribute("usersViewed", usersViewed);
        model.addAttribute("comments", forumDTO.getComments());
        model.addAttribute("otherForums", otherForums);

        return "forumDetail";
    }

    @PostMapping(value = "/forum/{forumId}", produces = "application/x-www-form-urlencoded;charset=UTF-8")
    public String addComment(Model model, @PathVariable("forumId") int forumId,
            @ModelAttribute("comment") Comment comment, BindingResult rs) {

        if (rs.hasErrors()) {
            System.out.println("Binding errors: " + rs.getAllErrors());
        }

        System.out.println("comment: " + comment.getContent());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User currentUser = this.userService.getUserByUsername(username);
        if (currentUser != null && !comment.getContent().isEmpty()) {
            comment.setForumId(forumService.findById(forumId));
            comment.setUserId(currentUser);
            comment.setCreatedDate(new Date());

            commentService.addComment(comment);

            // Thêm bình luận mới vào danh sách bình luận
            List<Comment> comments = forumService.getCommentsByForumId(forumId);
            comments.add(0, comment); // Thêm bình luận mới vào đầu danh sách
            model.addAttribute("comments", comments);
        }
        return "redirect:/forum/" + forumId;
    }

    @PostMapping(value = "/forum/new", produces = "text/html; charset=UTF-8")
    public String addForum(Model model, @ModelAttribute("forumDTO") ForumDTO forumDTO, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            // Nếu có lỗi ràng buộc dữ liệu, trả về lại form
            return "forumDetail";  // Giữ nguyên trang diễn đàn hiện tại
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User currentUser = this.userService.getUserByUsername(username);

        if (currentUser != null && !forumDTO.getTitle().isEmpty() && !forumDTO.getContent().isEmpty()) {
            // Tạo diễn đàn mới
            Forum newForum = new Forum();
            newForum.setTitle(forumDTO.getTitle());
            newForum.setContent(forumDTO.getContent());
            newForum.setUserId(currentUser);
            newForum.setCreatedDate(new Date());

            forumService.addForum(newForum);

            // Lấy danh sách các diễn đàn khác, bao gồm diễn đàn mới
            List<Forum> otherForums = forumService.getAllPosts();
            model.addAttribute("otherForums", otherForums);
            
             return "redirect:/forum/" + newForum.getId();
        }

        // Hiển thị lại trang chi tiết diễn đàn hiện tại
        return "forumDetail";
    }
}
