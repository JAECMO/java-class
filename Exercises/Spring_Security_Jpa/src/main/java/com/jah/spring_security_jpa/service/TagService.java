/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.service;

import com.jah.spring_security_jpa.models.Tag;
import com.jah.spring_security_jpa.repositories.TagRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author drjal
 */
@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Tag getTagById(int tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found with ID: " + tagId));
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public List<Tag> getTagsById(List<String> intList) {
        List<Tag> tagList = new ArrayList();

        for (String index : intList) {
            Tag tag = tagRepository.getOne(Integer.parseInt(index));
            tagList.add(tag);
        }

        return tagList;
    }

    public List<Tag> getSortedTags(String field, String sortOrder) {

        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(Sort.Direction.ASC, field) : Sort.by(Sort.Direction.DESC, field);
        return tagRepository.findAll(sort);

    }

}
