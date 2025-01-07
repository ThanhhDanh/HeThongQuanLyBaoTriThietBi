/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.converters;

import com.ltd.pojo.CommentDTO;
import com.ltd.pojo.Forum;
import com.ltd.pojo.ForumDTO;
import com.ltd.pojo.UserDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Acer
 */
@Transactional
public class ForumConverter {

    public static ForumDTO toDTO(Forum forum) {
        ForumDTO dto = new ForumDTO();
        dto.setId(forum.getId());
        dto.setTitle(forum.getTitle());
        dto.setContent(forum.getContent());
        dto.setCreatedDate(forum.getCreatedDate());

        // Chuyển đổi User sang UserDTO
        UserDTO userDTO = new UserDTO();
        userDTO.setId(forum.getUserId().getId());
        userDTO.setUsername(forum.getUserId().getUsername());
        userDTO.setFirstName(forum.getUserId().getFirstName());
        userDTO.setLastName(forum.getUserId().getLastName());
        userDTO.setAvatar(forum.getUserId().getAvatar());
        dto.setUser(userDTO);

        // Chuyển đổi commentCollection sang List<CommentDTO>
        List<CommentDTO> commentDTOs = forum.getCommentCollection()
                .stream()
                .map(comment -> {
                    CommentDTO commentDTO = new CommentDTO();
                    commentDTO.setId(comment.getId());
                    commentDTO.setContent(comment.getContent());
                    commentDTO.setCreatedDate(comment.getCreatedDate());

                    // Chuyển đổi User trong Comment sang UserDTO
                    UserDTO commentUserDTO = new UserDTO();
                    commentUserDTO.setId(comment.getUserId().getId());
                    commentUserDTO.setUsername(comment.getUserId().getUsername());
                    commentUserDTO.setFirstName(comment.getUserId().getFirstName());
                    commentUserDTO.setLastName(comment.getUserId().getLastName());
                    commentUserDTO.setAvatar(comment.getUserId().getAvatar());
                    commentDTO.setUser(commentUserDTO);

                    return commentDTO;
                })
                .collect(Collectors.toList());

        dto.setComments(commentDTOs);
        return dto;
    }
}
