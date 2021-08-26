# MVC 패턴 

<br>

- ### Controller Layer : 요청의 헤더, 바디 등을 분석하고 서비스로 넘겨줌

```java
package com.pinstagram.contents;


@RestController
@RequiredArgsConstructor
public class ContentsController {

    @NonNull
    private final ContentsService service;

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
}

```

<br>

- ### Service Layer : 컨트롤러에 요청에 따라 정해진 로직 실행후 반환
    - 일반적으로 Service 와 ServiceImpl로 구현함
    - 해당 프로젝트에서 서비스는 다음과 같은 경우 추상화 객체가 아닌 구체화된 클래스로 구현 
        - 컨트롤러와 서비스 관계가 1:1
        - 메소드가 많아져서 실제 컨트롤러에 제공해야할 메소드가 명확하게 가시적이지 않은 경우(private로 처리해도 코드량이 많은 경우)
    
```java
package com.pinstagram.contents.service;

public interface ContentsService {
    //Get
    RetrieveDto.Response getById(long id) throws ApiException;
    List<RetrieveDto.Response> getByDistance(double lat, double lng, double limit);
    
    /*...*/
}

```

```java
package com.pinstagram.contents.service;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentsServiceImpl implements ContentsService {
    
    /*...*/
    
    @Override
    public long create(Authentication authentication, CreateDto.Request request) throws ApiException {

        var id = authManager.getId(authentication);
        if (!authManager.isValidateToken(authentication) || id.isEmpty()) {
            throw new ApiException(ApiResponseCode.FORBIDDEN);
        }
        var account = accountRepository.findById(id.get());
        if (account.isEmpty()) {
            throw new ApiException(ApiResponseCode.USER_NOT_FOUNDED);
        }
        var geoResponse = template.exchange(
                addressConfig.getGeoServerAddress() + "/?latlng=" + request.getLat() + "," + request.getLng(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ApiResponse<ReverseGeoCodingResponse>>() {
                }
        );
        if (geoResponse.getStatusCode() != HttpStatus.OK) {
            throw new ApiException(ApiResponseCode.NOT_FOUND);
        }
        var geoData = geoResponse.getBody().getData();
        var entity = request.toEntity();
        entity.setAccount(account.get());
        entity.setFullAddress(geoData.getResults()[0].getFormattedAddress());
        entity.setCreateAt(LocalDateTime.now());
        var saved = contentsRepository.save(entity);
        return saved.getContentsId();
    }
    /*...*/
}


```


<br>

- ### Repository Layer : 서비스의 요청에따라 레파지토리에 접근 

```java

public interface ContentsRepository extends JpaRepository<ContentsEntity,Long> {
    @Query(value = "select * from contents c where st_distance_sphere(POINT(:lng,:lat),POINT(c.lng,c.lat)) < :limit",nativeQuery = true)
    List<ContentsEntity> findAllByDistance(
            @Param("lat") double lat,
            @Param("lng") double lng,
            @Param("limit") double limit
    );
}

```
