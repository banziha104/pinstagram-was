package com.pinstagram.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinstagram.common.jwt.JwtManager;
import com.pinstagram.event.dto.CreateDto;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventControllerTest {
    String adminToken;

    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper;

    @BeforeEach
    public void setup(WebApplicationContext ctx,
                      RestDocumentationContextProvider restDocumentation) throws Exception {
        DelegatingFilterProxy delegateProxyFilter = new DelegatingFilterProxy();
        delegateProxyFilter.init(new MockFilterConfig(ctx.getServletContext(), BeanIds.SPRING_SECURITY_FILTER_CHAIN));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)

                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // ?????? ??????
                .addFilter(delegateProxyFilter)
                .apply(documentationConfiguration(restDocumentation))
                .build();
        objectMapper = new ObjectMapper();
        adminToken = "Bearer " + new JwtManager().createToken("test@test.com","????????????",1L);
    }

    @Test
    @Order(1)
    public void ?????????_??????() throws Exception {
        CreateDto.Request request = new CreateDto.Request();
        request.setTitle("??????????????????");
        request.setDescription("description");
        request.setPicture("picture");
        request.setLat(37.389420);
        request.setLng(127.122625);
        request.setFullAddress("????????? ?????????");
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
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("description").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("picture").type(JsonFieldType.STRING).optional().description("?????? ??????"),
                                        fieldWithPath("fullAddress").type(JsonFieldType.STRING).optional().description("?????? ??????"),
                                        fieldWithPath("lat").type(JsonFieldType.NUMBER).optional().description("??????"),
                                        fieldWithPath("lng").type(JsonFieldType.NUMBER).optional().description("??????")
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
    public void ?????????_??????() throws Exception {

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
                                        parameterWithName("id").description("Event Id")
                                ),
                                responseFields(
                                        beneathPath("data").withSubsectionId("data"),
                                        fieldWithPath("eventId").type(JsonFieldType.NUMBER).description("eventId"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("description").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("fullAddress").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("picture").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("lat").type(JsonFieldType.NUMBER).description("??????"),
                                        fieldWithPath("lng").type(JsonFieldType.NUMBER).description("??????")
                                )
                        )
                );
    }

    @Test
    @Order(3)
    public void ??????_??????_??????() throws Exception {

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
                                        parameterWithName("latlng").description("?????? ?????? (???: 37.382323,127.1231233)"),
                                        parameterWithName("limit").description("latlng??? ???????????? ??? meter ????????? ????????? ????????????")
                                ),
                                responseFields(
                                        beneathPath("data").withSubsectionId("data"),
                                        fieldWithPath("eventId").type(JsonFieldType.NUMBER).description("Event Id"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("description").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("fullAddress").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("picture").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("lat").type(JsonFieldType.NUMBER).description("??????"),
                                        fieldWithPath("lng").type(JsonFieldType.NUMBER).description("??????")
                                )
                        )
                );
    }

}