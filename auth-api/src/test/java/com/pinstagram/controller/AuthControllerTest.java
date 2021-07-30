package com.pinstagram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinstagram.authentication.dto.SignInDto;
import com.pinstagram.authentication.dto.SignUpDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.config.BeanIds;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ActiveProfiles("test")
public class AuthControllerTest {

    String adminToken = "Bearer eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJpZCI6MSwiZXhwIjoxNjI3NzEzNjcxLCJlbWFpbCI6ImFkbWluQGFkbWluLmNvbSJ9.Yu0NABW0QLMd1jbMO8si9BHlgvTqtrUo7UkfVdYC3Cg";
    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper;


    @BeforeEach
    public void setup(WebApplicationContext ctx,
                      RestDocumentationContextProvider restDocumentation) throws Exception {
        DelegatingFilterProxy delegateProxyFilter = new DelegatingFilterProxy();
        delegateProxyFilter.init(new MockFilterConfig(ctx.getServletContext(), BeanIds.SPRING_SECURITY_FILTER_CHAIN));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)

                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .addFilter(delegateProxyFilter)
                .apply(documentationConfiguration(restDocumentation))
                .build();
        objectMapper = new ObjectMapper();
    }


    @Test
    void 로그인() throws Exception {
        SignInDto.SignInRequest request = new SignInDto.SignInRequest();
        request.setEmail("test@test.com");
        request.setPassword("test");
        request.setName("닉네임");

        var result = mockMvc.perform(post("/signin", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", adminToken)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        result
                .andExpect(status().isOk()).andDo(print())
                .andDo(
                        document("signIn",
                                requestHeaders(
                                        headerWithName(HttpHeaders.AUTHORIZATION).description("JWT ADMIN 토큰")
                                ),
                                requestFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드")
                                ),
                                responseFields(
                                        beneathPath("data").withSubsectionId("data"),
                                        fieldWithPath("token").type(JsonFieldType.STRING).description("JWT 토큰")
                                )
                        )
                );
    }

    @Test
    void 회원가입() throws Exception {
        SignUpDto.CreateRequest request = new SignUpDto.CreateRequest();
        request.setEmail("test2@test.com");
        request.setPassword("test2");
        request.setName("이름입니다");


        var result = mockMvc.perform(post("/signup", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", adminToken)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        result
                .andExpect(status().isOk()).andDo(print())
                .andDo(
                        document("signUp",
                                requestHeaders(
                                        headerWithName(HttpHeaders.AUTHORIZATION).description("JWT ADMIN 토큰")
                                ),
                                requestFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).optional().description("이름")
                                ),
                                responseFields(
                                        beneathPath("data").withSubsectionId("data"),
                                        fieldWithPath("token").type(JsonFieldType.STRING).description("JWT 토큰")
                                )
                        )
                );
    }
}
