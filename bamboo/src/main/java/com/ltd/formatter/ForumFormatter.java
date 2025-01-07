package com.ltd.formatter;

import com.ltd.pojo.Forum;
import com.ltd.service.ForumService;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;
import org.springframework.beans.factory.annotation.Autowired;

public class ForumFormatter  implements Formatter<Forum>{
    @Autowired
    private ForumService forumService;

    @Override
    public String print(Forum forum, Locale locale) {
        return String.valueOf(forum.getId());
    }

    @Override
    public Forum parse(String forumId, Locale locale) throws ParseException {
        try {
            int id = Integer.parseInt(forumId);
            return forumService.findById(id);
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid forum ID: " + forumId, 0);
        }
    }
}
