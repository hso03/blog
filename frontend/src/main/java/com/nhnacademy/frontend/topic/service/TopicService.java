package com.nhnacademy.frontend.topic.service;

import com.nhnacademy.frontend.topic.dto.TopicResponse;

import java.util.List;

public interface TopicService {
    List<TopicResponse> rootTopicList();
    List<TopicResponse> subTopicList(int topicPid);
}
