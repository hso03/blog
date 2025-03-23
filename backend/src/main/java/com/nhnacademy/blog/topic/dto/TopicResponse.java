package com.nhnacademy.blog.topic.dto;

import com.nhnacademy.blog.topic.domain.Topic;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Value
@RequiredArgsConstructor
public class TopicResponse {
    private final Integer topicId;
    private final Integer topicPid;
    private final String topicName;
    private final Integer topicSec;
    private final List<TopicResponse> childrenTopics = new ArrayList<>();
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}