package com.pinstagram.geo.geocoder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ActiveProfiles("test")
public class GeoCoderControllerTests {
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
    void 리버스_지오코딩_테스트() throws Exception {
        var result = mockMvc.perform(get("/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("latlng","37.389420,127.122625"))
                .andDo(print());

        result
                .andExpect(status().isOk()).andDo(print())
                .andDo(
                        document("reverseGeocoding",
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("latlng").description("위도경도 (예:37.389420,127.122625)")
                                ),
                                responseFields(
                                        beneathPath("data").withSubsectionId("data"),
                                        fieldWithPath("plus_code").type(JsonFieldType.OBJECT).description("Plus Code"),
                                        fieldWithPath("plus_code.compound_code").type(JsonFieldType.STRING).description("Compound Code"),
                                        fieldWithPath("plus_code.global_code").type(JsonFieldType.STRING).description("Global Code"),
                                        fieldWithPath("results").type(JsonFieldType.ARRAY).description("결과"),
                                        fieldWithPath("results[].address_components").type(JsonFieldType.ARRAY).description("Address 구성 요소"),
                                        fieldWithPath("results[].address_components[].long_name").type(JsonFieldType.STRING).description("지명"),
                                        fieldWithPath("results[].address_components[].short_name").type(JsonFieldType.STRING).description("짧은 지명"),
                                        fieldWithPath("results[].address_components[].types").type(JsonFieldType.ARRAY).description("구성 요소 타입"),
                                        fieldWithPath("results[].formatted_address").type(JsonFieldType.STRING).description("전체 주소")
                                )
                        )
                );
    }

    @Test
    void 지오코딩_테스트() throws Exception {
        var result = mockMvc.perform(get("/geo")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("address","경기 성남시 분당구 서현로 170"))
                .andDo(print());

        result
                .andExpect(status().isOk()).andDo(print())
                .andDo(
                        document("Geocoding",
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("address").description("주소")
                                ),
                                responseFields(
                                        beneathPath("data").withSubsectionId("data"),
                                        fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("위도"),
                                        fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("경도"),
                                        fieldWithPath("province").type(JsonFieldType.STRING).description("주소")
                                )
                        )
                );
    }
}
