package com.pinstagram.contents;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinstagram.contents.dto.CreateDto;
import com.pinstagram.contents.dto.UpdateDto;
import io.jsonwebtoken.Header;
import org.junit.jupiter.api.*;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContentsControllerTest {
    String adminToken = "Bearer eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJpZCI6MSwiZXhwIjoxNjI3ODk1NTM2LCJlbWFpbCI6InRlc3RAdGVzdC5jb20ifQ.0K-sD7p_UqTk6GzExUM50L84k7gPSTYXWCzULMZEpz0";

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
    @Order(1)
    public void 콘텐츠_생성() throws Exception {
        CreateDto.Request request = new CreateDto.Request();
        request.setTitle("타이틀입니다");
        request.setDescription("description");
        request.setPicture("picture");
        request.setLat(37.389420);
        request.setLng(127.122625);
        request.setTag("FOOD");

        var result = mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        result
                .andExpect(status().isOk()).andDo(print())
                .andDo(
                        document("create",
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("Bearer Token")
                                ),
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("description").type(JsonFieldType.STRING).description("패스워드"),
                                        fieldWithPath("picture").type(JsonFieldType.STRING).optional().description("이름"),
                                        fieldWithPath("tag").type(JsonFieldType.STRING).optional().description("태그"),
                                        fieldWithPath("lat").type(JsonFieldType.NUMBER).optional().description("위도"),
                                        fieldWithPath("lng").type(JsonFieldType.NUMBER).optional().description("경도")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("code"),
                                        fieldWithPath("httpCode").type(JsonFieldType.NUMBER).description("httpCode"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("message"),
                                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("id")
                                )
                        )
                );
    }


    @Test
    @Order(2)
    public void 아이디_조회() throws Exception {

        var result = mockMvc.perform(get("/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(print());

        result
                .andExpect(status().isOk()).andDo(print())
                .andDo(
                        document("retrieve",
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("id").description("Content Id")
                                ),
                                responseFields(
                                        beneathPath("data").withSubsectionId("data"),
                                        fieldWithPath("contentsId").type(JsonFieldType.NUMBER).description("contents Id"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("description").type(JsonFieldType.STRING).description("설명"),
                                        fieldWithPath("fullAddress").type(JsonFieldType.STRING).description("주소"),
                                        fieldWithPath("picture").type(JsonFieldType.STRING).description("이미지 주소"),
                                        fieldWithPath("tag").type(JsonFieldType.STRING).description("태그"),
                                        fieldWithPath("lat").type(JsonFieldType.NUMBER).description("위도"),
                                        fieldWithPath("lng").type(JsonFieldType.NUMBER).description("경도"),
                                        fieldWithPath("account").type(JsonFieldType.OBJECT).description("작성자"),
                                        fieldWithPath("account.accountId").type(JsonFieldType.NUMBER).description("작성자 아이디"),
                                        fieldWithPath("account.name").type(JsonFieldType.STRING).description("작성자 이름"),
                                        fieldWithPath("account.email").type(JsonFieldType.STRING).description("작성자 이메일"),
                                        fieldWithPath("createAt").type(JsonFieldType.STRING).description("생성일"),
                                        fieldWithPath("updateAt").type(JsonFieldType.STRING).optional().description("수정일")
                                )
                        )
                );
    }

    @Test
    @Order(3)
    public void 위도_경도_조회() throws Exception {

        var result = mockMvc.perform(get("/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("latlng", "37.389420,127.122625")
                .param("limit", "1000")
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(print());

        result
                .andExpect(status().isOk()).andDo(print())
                .andDo(
                        document("retrieveByLatlng",
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("latlng").description("위도 경도 (예: 37.382323,127.1231233)"),
                                        parameterWithName("limit").description("latlng의 지점에서 몇 meter 이하의 지점만 가져올지")
                                ),
                                responseFields(
                                        beneathPath("data").withSubsectionId("data"),
                                        fieldWithPath("contentsId").type(JsonFieldType.NUMBER).description("contents Id"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("description").type(JsonFieldType.STRING).description("설명"),
                                        fieldWithPath("fullAddress").type(JsonFieldType.STRING).description("주소"),
                                        fieldWithPath("picture").type(JsonFieldType.STRING).description("이미지 주소"),
                                        fieldWithPath("tag").type(JsonFieldType.STRING).description("태그"),
                                        fieldWithPath("lat").type(JsonFieldType.NUMBER).description("위도"),
                                        fieldWithPath("lng").type(JsonFieldType.NUMBER).description("경도"),
                                        fieldWithPath("account").type(JsonFieldType.OBJECT).description("작성자"),
                                        fieldWithPath("account.accountId").type(JsonFieldType.NUMBER).description("작성자 아이디"),
                                        fieldWithPath("account.name").type(JsonFieldType.STRING).description("작성자 이름"),
                                        fieldWithPath("account.email").type(JsonFieldType.STRING).description("작성자 이메일"),
                                        fieldWithPath("createAt").type(JsonFieldType.STRING).description("생성일"),
                                        fieldWithPath("updateAt").type(JsonFieldType.STRING).optional().description("수정일")
                                )
                        )
                );
    }

    @Test
    @Order(4)
    public void 수정() throws Exception {
        UpdateDto.Request request = new UpdateDto.Request();
        request.setTitle("새 타이틀");
        request.setDescription("새 설명");
        request.setTag("새 태그");
        request.setPicture("새 이미지 주소");
        var result = mockMvc.perform(put("/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        result
                .andExpect(status().isOk()).andDo(print())
                .andDo(
                        document("update",
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                                ),
                                pathParameters(
                                        parameterWithName("id").description("수정될 Id")
                                ),
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("새 타이틀"),
                                        fieldWithPath("description").type(JsonFieldType.STRING).description("새 설명"),
                                        fieldWithPath("tag").type(JsonFieldType.STRING).description("새 태그"),
                                        fieldWithPath("picture").type(JsonFieldType.STRING).description("새 이미지")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("code"),
                                        fieldWithPath("httpCode").type(JsonFieldType.NUMBER).description("httpCode"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("message"),
                                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("수정된 id")
                                )
                        )
                );
    }

    @Test
    @Order(5)
    public void 삭제() throws Exception {

        var result = mockMvc.perform(delete("/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, adminToken)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(print());

        result
                .andExpect(status().isOk()).andDo(print())
                .andDo(
                        document("delete",
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                                ),
                                pathParameters(
                                        parameterWithName("id").description("삭제될 Id")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("code"),
                                        fieldWithPath("httpCode").type(JsonFieldType.NUMBER).description("httpCode"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("message"),
                                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("삭제된 id")
                                )
                        )
                );
    }
}