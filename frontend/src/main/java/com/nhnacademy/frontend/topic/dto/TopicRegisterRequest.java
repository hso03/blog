package com.nhnacademy.frontend.topic.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class TopicRegisterRequest {

    private final Integer parentTopicId;

    @NotBlank(message = "토픽 이름 설정은 필수 입니다.")
    private final String topicName;

    @NotBlank(message = "토픽 순서는 설정은 필수 입니다.")
    private final Integer topicSec;

}
