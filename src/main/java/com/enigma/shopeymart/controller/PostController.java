package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.entity.Posts;
import com.enigma.shopeymart.service.impl.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)//produces biar bentukannya json outputnya
    public ResponseEntity<String> getAllPost(){
        return postService.getAllPost();
    }

    //get all local and other api
    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)//produces biar bentukannya json outputnya
    public ResponseEntity<List<Posts>> getAllPost2(){
        return postService.getAllPostLocal();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPostById(@PathVariable Long id){
        return postService.getPostById(id);
    }

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody Posts post){
        return postService.createPosts(post);
    }

}
