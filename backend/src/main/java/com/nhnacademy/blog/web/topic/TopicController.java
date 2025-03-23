package com.nhnacademy.blog.web.topic;

import com.nhnacademy.blog.topic.dto.TopicRegisterRequest;
import com.nhnacademy.blog.topic.dto.TopicResponse;
import com.nhnacademy.blog.topic.dto.TopicUpdateRequest;
import com.nhnacademy.blog.topic.service.TopicService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/topics")
@AllArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @PostMapping
    public ResponseEntity<TopicResponse> createTopic(@Validated @RequestBody TopicRegisterRequest topicRegisterRequest){
        TopicResponse topicResponse = topicService.createTopic(topicRegisterRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(topicResponse);
    }

    @GetMapping
    public ResponseEntity<List<TopicResponse>> getRootTopicList(){
        List<TopicResponse> rootTopicList = topicService.getRootTopics();
        return ResponseEntity
                .ok(rootTopicList);
    }

    @GetMapping("/{topic-pid}")
    public ResponseEntity<List<TopicResponse>> getSubTopicList(@PathVariable("topic-pid") Integer topicPid){
        List<TopicResponse> subTopicList = topicService.getSubTopics(topicPid);
        return ResponseEntity
                .ok(subTopicList);
    }

    @GetMapping("/{topic-pid}/{topic-id}")
    public ResponseEntity<List<TopicResponse>> getTopicPostList(@PathVariable("topic-pid") Integer topicPid){
        List<TopicResponse> subTopicList = topicService.getSubTopics(topicPid);
        return ResponseEntity
                .ok(subTopicList);
    }

    @PutMapping("/{topic-id}")
    public ResponseEntity<TopicResponse> updateTopic(@PathVariable("topic-id") Integer topicId, @Validated @RequestBody TopicUpdateRequest topicUpdateRequest){
        TopicResponse topicResponse = topicService.updateTopic(topicId, topicUpdateRequest);
        return ResponseEntity
                .ok(topicResponse);
    }

    @DeleteMapping("/{topic-id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable("topic-id")Integer topicId){
        topicService.deleteTopic(topicId);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }
}
