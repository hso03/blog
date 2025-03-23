package com.nhnacademy.blog.topic.service.impl;

import com.nhnacademy.blog.common.exception.ConflictException;
import com.nhnacademy.blog.common.exception.NotFoundException;
import com.nhnacademy.blog.topic.domain.Topic;
import com.nhnacademy.blog.topic.dto.TopicRegisterRequest;
import com.nhnacademy.blog.topic.dto.TopicResponse;
import com.nhnacademy.blog.topic.dto.TopicUpdateRequest;
import com.nhnacademy.blog.topic.repository.TopicRepository;
import com.nhnacademy.blog.topic.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;
    
    @Override
    public List<TopicResponse> getRootTopics() {
        List<Topic> topics = topicRepository.findTopicsByParentTopicIsNullOrderByTopicSecAsc();
        return convertToTopicResponse(topics);
    }

    @Override
    public List<TopicResponse> getSubTopics(int topicId) {
        List<Topic> topics = topicRepository.findTopicsByParentTopic_topicIdOrderByTopicSecAsc(topicId);
        return convertToTopicResponse(topics);
    }

    @Override
    public TopicResponse createTopic(TopicRegisterRequest topicRegisterRequest) {

        if(topicRepository.existsByTopicName(topicRegisterRequest.getTopicName())){
            throw new ConflictException("%s is already".formatted(topicRegisterRequest.getTopicName()));
        }

        Topic topic;

        if(Objects.isNull(topicRegisterRequest.getParentTopicId())){
            topic = Topic.ofNewRootTopic(topicRegisterRequest.getTopicName(), topicRegisterRequest.getTopicSec());
        }else {
            topic = Topic.ofNewSubTopic(
                    topicRepository.findById(topicRegisterRequest.getParentTopicId()).orElseThrow(()-> new NotFoundException("%s topic parent Id not found")),
                    topicRegisterRequest.getTopicName(),
                    topicRegisterRequest.getTopicSec());
        }

        Topic savedTopic = topicRepository.save(topic);

        return new TopicResponse(
                savedTopic.getTopicId(),
                Objects.isNull(savedTopic.getParentTopic())?null:savedTopic.getParentTopic().getTopicId(),
                savedTopic.getTopicName(),
                savedTopic.getTopicSec(),
                savedTopic.getCreatedAt(),
                savedTopic.getUpdatedAt()
        );
    }

    private List<TopicResponse> convertToTopicResponse(List<Topic> topics) {
        List<TopicResponse> topicResponses = new ArrayList<>();
        for (Topic topic : topics) {
            topicResponses.add(
                    new TopicResponse(
                            topic.getTopicId(),
                            null,
                            topic.getTopicName(),
                            topic.getTopicSec(),
                            topic.getCreatedAt(),
                            topic.getUpdatedAt()
                    )
            );
        }
        return topicResponses;
    }

    @Override
    public TopicResponse updateTopic(int topicId, TopicUpdateRequest topicUpdateRequest) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(()->new ConflictException("%s topicId is already".formatted(topicId)));

        topic.update(
                topicRepository.findById(topicUpdateRequest.getParentTopicId()).orElseThrow(()-> new NotFoundException("%s topic parent Id not found")),
                topicUpdateRequest.getTopicName(),
                topicUpdateRequest.getTopicSec()
        );

        return new TopicResponse(
                topic.getTopicId(),
                Objects.isNull(topic.getParentTopic())?null:topic.getParentTopic().getTopicId(),
                topic.getTopicName(),
                topic.getTopicSec(),
                topic.getCreatedAt(),
                topic.getUpdatedAt()
        );
    }

    @Override
    public void deleteTopic(int topicId) {
        if(!topicRepository.existsById(topicId)){
            throw new ConflictException("%s topicId is not already".formatted(topicId));
        }

        topicRepository.deleteById(topicId);
    }
}
