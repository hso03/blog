package com.nhnacademy.frontend.security.handler;

import com.nhnacademy.frontend.blog.adaptor.BlogAdaptor;
import com.nhnacademy.frontend.security.userdetail.MemberDetails;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final BlogAdaptor blogAdaptor;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = authentication.getName();

        //TODO#6-4 MemberLoginEvent 생성: 로그인 이벤트를 발생시키고 이메일을 포함시킵니다.

        //TODO#6-5 ApplicationEventPublisher 객체의 publishEvent()를 호출하여 이벤트를 발생시킴니다.


        //TODO#6-6 사용자의 메인 블로그 아이디 구하기
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();

        long mbNo = memberDetails.getMemberResponse().getMbNo();

        //TODO#6-7 blogRepository의 findBlogFidFromMainBlog()메서드를 호출해서 blogFid를 얻습니다.
        String blogFid = blogAdaptor.getMainBlogFid(mbNo).getBody();
        String redirectUrl;
        if(StringUtils.isEmpty(blogFid)) {
            redirectUrl = "/";
        }else{
            /**
             * TODO#6-8 blogFid가 존재하면 redirectUrl을 다음과 같이 설정 합니다.
             * - /blog/{blog-fid}/index.do
             */
            HttpSession session = request.getSession(false);
            session.setAttribute("mbNo", mbNo);
            redirectUrl = "/blog/%s/index.do".formatted(blogFid);
        }

        //TODO#6-9 로그인 성공  redirectUrl 페이지로 리다이렉션
        response.sendRedirect(redirectUrl);


    }
}
