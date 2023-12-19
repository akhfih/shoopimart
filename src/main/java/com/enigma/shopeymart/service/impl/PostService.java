package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.entity.Posts;
import com.enigma.shopeymart.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    @Value("${api.enpoint.url.post}")
    private String BASE_URL; //tidak perlu pakai final karena itu variable yg gak butuh diinject
    private final RestTemplate restTemplate;

    private final PostRepository postRepository;
    public ResponseEntity<String> getAllPost(){
        String apiUrl = BASE_URL;
        return responseMethod(restTemplate.getForEntity(apiUrl,String.class),"Failed to load data");

    }

    public ResponseEntity<String> getPostById(Long id){
        String apiUrl = BASE_URL + id;
        return responseMethod(restTemplate.getForEntity(apiUrl,String.class),"Failed to load data");

    }


    //get all local and other api
    public ResponseEntity<List<Posts>> getAllPostLocal(){
        ResponseEntity<Posts[]> apiResponse = restTemplate.getForEntity(BASE_URL, Posts[].class);

        List<Posts> externalPosts = List.of(apiResponse.getBody());

        List<Posts> dbPosts = postRepository.findAll();

        dbPosts.addAll(externalPosts);
        return ResponseEntity.ok(dbPosts);

    }

    public ResponseEntity<String> createPosts(Posts posts){
        String apiUrl = BASE_URL;
        //Mengatur header permintaan
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //Membungkus data permintaan dalam HttpEntity
        HttpEntity<Posts> requestEntity = new HttpEntity<>(posts,headers);
        postRepository.save(posts);
        //response
        return  responseMethod(restTemplate.postForEntity(apiUrl,requestEntity,String.class), "Failed to create data");

    }

    private ResponseEntity<String> responseMethod(ResponseEntity<String> restTemplate, String message){
        ResponseEntity<String> responseEntity = restTemplate;
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            String responseBody = responseEntity.getBody();
            return ResponseEntity.ok(responseBody);
        }
        return ResponseEntity.status(responseEntity.getStatusCode()).body(message);
    }


}
