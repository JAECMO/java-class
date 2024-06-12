/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.myblog.controller;

import com.jah.myblog.models.Tag;
import com.jah.myblog.repositories.PostRepository;
import com.jah.myblog.repositories.TagRepository;
import com.jah.myblog.service.ImageService;
import com.jah.myblog.service.MyUserDetailsService;
import com.jah.myblog.service.TagService;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author drjal
 */
@Controller
@SessionAttributes("user")
public class TagController {

    @Autowired
    PostRepository posts;

    @Autowired
    private TagRepository tags;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    private TagService tagService;

    @Autowired
    ImageService imageService;

    @RequestMapping("/tags")
    public String tags(Model model) {
        // Fetch the list of all tags
        List<Tag> tagList = tagService.getAllTags();
        Set<Tag> orderedTags = new TreeSet<>();

        for (Tag tag1 : tagList) {
            orderedTags.add(tag1);
        }
        model.addAttribute("tags", orderedTags);
        return "tags";
    }

    @PostMapping("/addTags")
    public String addTags(Tag tag, Model model) {
        tags.save(tag);
        return "redirect:/tags";
    }

    @RequestMapping("/updateTag")
    public String updateTag(int id, Model model) {
        // Fetch the user details by ID and pass them to the update form
        Tag tag = tagService.getTagById(id);
        model.addAttribute("tag", tag);
        return "updateTag";

    }

    @PostMapping("/updateTag")
    public String updateTag(@RequestParam int id, @RequestParam String name) {
        Tag tag = tagService.getTagById(id);
        tag.setName(name);
        tagRepository.save(tag);
        return "redirect:/tags";
    }

    @Transactional
    @RequestMapping("/removeTag")
    public String removeTag(@RequestParam int id) {
        tagRepository.deleteById(id);
        tagRepository.deletePostTagByTagId(id);

        return "redirect:/tags";
    }

}
