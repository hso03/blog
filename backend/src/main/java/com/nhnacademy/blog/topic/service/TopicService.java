package com.nhnacademy.blog.topic.service;

import com.nhnacademy.blog.topic.dto.TopicRegisterRequest;
import com.nhnacademy.blog.topic.dto.TopicResponse;
import com.nhnacademy.blog.topic.dto.TopicUpdateRequest;

import java.util.List;

public interface TopicService {
    List<TopicResponse> getRootTopics();
    List<TopicResponse> getSubTopics(int parentTopicId);
    TopicResponse createTopic(TopicRegisterRequest topicRegisterRequest);
    TopicResponse updateTopic(int topicId, TopicUpdateRequest topicUpdateRequest);
    void deleteTopic(int topicId);
}
