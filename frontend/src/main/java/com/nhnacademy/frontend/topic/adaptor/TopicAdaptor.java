package com.nhnacademy.frontend.topic.adaptor;

import com.nhnacademy.frontend.topic.dto.TopicRegisterRequest;
import com.nhnacademy.frontend.topic.dto.TopicResponse;
import com.nhnacademy.frontend.topic.dto.TopicUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "topicAdaptor" , url = "http://localhost:8080/api/v1/topics")
public interface TopicAdaptor {

    @PostMapping
    public ResponseEntity<TopicResponse> createTopic(@Validated @RequestBody TopicRegisterRequest topicRegisterRequest);

    @GetMapping
    public ResponseEntity<List<TopicResponse>> getRootTopicList();

    @GetMapping("/{topic-pid}")
    public ResponseEntity<List<TopicResponse>> getSubTopicList(@PathVariable("topic-pid") Integer topicPid);

    @PutMapping("/{topic-id}")
    public ResponseEntity<TopicResponse> updateTopic(@PathVariable("topic-id") Integer topicId, @Validated @RequestBody TopicUpdateRequest topicUpdateRequest);

    @DeleteMapping("/{topic-id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable("topic-id")Integer topicId);

}
