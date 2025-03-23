package com.nhnacademy.frontend.topic.service.impl;

import com.nhnacademy.frontend.common.exception.ErrorException;
import com.nhnacademy.frontend.topic.adaptor.TopicAdaptor;
import com.nhnacademy.frontend.topic.dto.TopicResponse;
import com.nhnacademy.frontend.topic.service.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicAdaptor topicAdaptor;

    @Override
    public List<TopicResponse> subTopicList(int topicPid) {
        ResponseEntity<List<TopicResponse>> topicResponseEntity = topicAdaptor.getSubTopicList(topicPid);
        if(!topicResponseEntity.getStatusCode().is2xxSuccessful()){
            throw new ErrorException("not 2xx status code");
        }

        return topicResponseEntity.getBody();
    }

    @Override
    public List<TopicResponse> rootTopicList() {
        ResponseEntity<List<TopicResponse>> topicResponseEntity = topicAdaptor.getRootTopicList();
        if(!topicResponseEntity.getStatusCode().is2xxSuccessful()){
            throw new ErrorException("not 2xx status code");
        }
        return topicResponseEntity.getBody();
    }
}
