# 테스트 및 자동화

> 현재는 컨트롤러와 유틸만 테스팅 중, 서비스 레이어는 추후 테스트 예정 <br>
> 테스트는 springmvc-test, JUnit5, 문서화는 RestDocs, asciidoctor 사용

<br>

- ### 컨트롤러 구현 

```java
package com.pinstagram.contents;

@RestController
@RequiredArgsConstructor
public class ContentsController {

    /*...*/

    @PostMapping
    ApiResponse createContents(
          Authentication authentication,
          @RequestBody @Validated CreateDto.Request request
    ){
        try{
            return ApiResponse.createOK(service.create(authentication,request));
        }catch (ApiException e){
            return ApiResponse.createException(e);
        }
    }

    /*...*/
}

```

<br>

- ### 테스트 클래스 구현 및 문서화 명시 

```java
package com.pinstagram.contents;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContentsControllerTest {

    /*...*/

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
        adminToken = "Bearer " + new JwtManager().createToken("test@test.com", "김테스트", 1L);
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

        // 아래 부분에서 문서화
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

    /*...*/
}
```

<br>

- ### 만들어진 문서를 바탕으로 asciidoctor에 명시

```asciidoc
/*...*/
== 생성

=== 요청

include::{snippets}/create/http-request.adoc[]
include::{snippets}/create/request-fields.adoc[]

=== 응답

include::{snippets}/create/http-response.adoc[]
include::{snippets}/create/response-fields.adoc[]

/*...*/
```

<br>

- ### Gradle 에서 asciidoc을 html로 변환 

```groovy

asciidoctor {
    inputs.dir snippetsDir
    dependsOn test
}

asciidoctor.doFirst {
    delete file('src/main/resources/static/docs')
}

task copyDocument(type: Copy) {
    dependsOn asciidoctor
    from file("build/docs/asciidoc")
    into file("src/main/resources/static/docs")
}

build {
    dependsOn copyDocument
}

```