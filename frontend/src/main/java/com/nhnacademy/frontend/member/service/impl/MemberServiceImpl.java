package com.nhnacademy.frontend.member.service.impl;

import com.nhnacademy.frontend.common.exception.ErrorException;
import com.nhnacademy.frontend.member.adaptor.MemberAdaptor;
import com.nhnacademy.frontend.member.dto.MemberLoginResponse;
import com.nhnacademy.frontend.member.dto.MemberRegisterRequest;
import com.nhnacademy.frontend.member.dto.MemberResponse;
import com.nhnacademy.frontend.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberAdaptor memberAdaptor;

    public MemberServiceImpl(MemberAdaptor memberAdaptor) {
        this.memberAdaptor = memberAdaptor;
    }

    @Override
    public MemberResponse registerMember(@Validated MemberRegisterRequest memberRegisterRequest) {
        ResponseEntity<MemberResponse> memberAdaptorResponseEntity = memberAdaptor.createMember(memberRegisterRequest);
        if(!memberAdaptorResponseEntity.getStatusCode().is2xxSuccessful()){
            throw new ErrorException("not 2xx status code");
        }
        return memberAdaptorResponseEntity.getBody();
    }

    @Override
    public MemberResponse getMember(Long mbNo) {
        log.error("mbNo! : {}",mbNo);
        ResponseEntity<MemberResponse> memberResponseResponseEntity = memberAdaptor.getMember(mbNo);
        if(!memberResponseResponseEntity.getStatusCode().is2xxSuccessful()){
            throw new ErrorException("not 2xx status code");
        }

        return memberResponseResponseEntity.getBody();
    }

    @Override
    public MemberResponse getMemberByEmail(String mbEmail) {
        ResponseEntity<MemberLoginResponse> memberResponseResponseEntity = memberAdaptor.getMemberByEmail(mbEmail);
        if(!memberResponseResponseEntity.getStatusCode().is2xxSuccessful()){
            throw new ErrorException("not 2xx status code");
        }

        return new MemberResponse(
                memberResponseResponseEntity.getBody().getMbNo(),
                memberResponseResponseEntity.getBody().getMbEmail(),
                memberResponseResponseEntity.getBody().getMbName(),
                memberResponseResponseEntity.getBody().getMbMobile(),
                memberResponseResponseEntity.getBody().getCreatedAt(),
                memberResponseResponseEntity.getBody().getUpdatedAt(),
                memberResponseResponseEntity.getBody().getWithdrawalAt()
        );
    }
}
