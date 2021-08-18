# JWT 인증 

- ### JWT 관리 객체 생성 
    - createToken() : 토큰을 생성 email,name,id 세가지만 Claims로 저장
    - getClaims(String token) : 토큰을 인자로 받아 Claims을 파싱후 리턴 

```java
// com.pinstagram.common.jwt.JWTManager

public class JwtManager {
    private static final long EXPIRED_TIME = 1000 * 60L * 60L * 24L;
    private static final byte[] SECRET_KEY = "pinstagramsecret".getBytes(StandardCharsets.UTF_8);


    public String createToken(String email, String name, long id){
        Map<String, Object> heaaders = new HashMap<>();
        heaaders.put("type","JWT");
        heaaders.put("alg","HS256");

        Map<String,Object> payloads = new HashMap<>();
        payloads.put("email",email);
        payloads.put("name",name);
        payloads.put("id",id);

        Date ext = new Date();
        ext.setTime(ext.getTime() + EXPIRED_TIME);

        return Jwts
                .builder()
                .setHeader(heaaders)
                .setClaims(payloads)
                .setExpiration(ext)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            return null;
        }
    }
}
```

<br>

- ### Jwt Claims 를 이용해 Authentification 이 맞는지 확인하는 객체 생성

```java
// com.pinstagram.common.jwt;

public class AuthManager {
    public boolean isAdmin(Authentication authentication) {
        if (authentication == null) {
            return false;
        }

        var principal = authentication.getPrincipal();

        if (principal instanceof Claims) {
            return ((Claims) principal).get("email").equals("admin@admin.com");
        }
        return false;
    }

    public boolean isValidateToken(Authentication authentication) {
        if (authentication == null) {
            return false;
        }

        var principal = authentication.getPrincipal();

        if (principal instanceof Claims) {
            Claims claims = (Claims) principal;
            Date expiration = claims.get("exp", Date.class);
            return new Date().getTime() <= expiration.getTime();
        }

        return false;
    }

    public boolean isSameId(Authentication authentication, long id) {
        if (authentication == null) {
            return false;
        }

        var principal = authentication.getPrincipal();
        if (principal instanceof Claims) {
            Claims claims = (Claims) principal;
            var authId = claims.get("id", Long.class);
            return authId == id;
        }else{
            return false;
        }
    }

    public Optional<Long> getId(Authentication authentication){
        if (authentication == null) {
            return Optional.empty();
        }

        var principal = authentication.getPrincipal();
        if (principal instanceof Claims) {
            Claims claims = (Claims) principal;
            var id = claims.get("id", Long.class);
            return Optional.ofNullable(id);
        }else{
            return Optional.empty();
        }
    }
}
```

- ### Security Config로 Bean에 등록 

<br>

```java
// com.pinstagram.authentication.config;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JwtManager jwtManager(){
        return new JwtManager();
    }

    @Bean
    public AuthManager authManager(){
        return new AuthManager();
    }
    
    /*..*/
}
```

<br>

- ### JwtAuthenticationFilter를 이용해 Jwt 토큰 파싱 

```java
// com.pinstagram.authentication.config;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    /*..*/
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Filter filter =
                new JwtAuthenticationFilter(authenticationManager(), jwtManager());
        http.cors().disable()
                .csrf().disable()
                .formLogin().disable()
                .headers().frameOptions().disable()
                .and()
                .addFilter(filter)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

        private JwtManager jwtUtil;

        public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtManager jwtUtil) {
            super(authenticationManager);
            this.jwtUtil = jwtUtil;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain chain) throws IOException, ServletException {
            Authentication authentication = getAuthentication(request);
            if (authentication != null) {
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        }

        private Authentication getAuthentication(HttpServletRequest request) {
            String token = request.getHeader("Authorization");
            if (token == null) {
                return null;
            }
            Claims claims = jwtUtil.getClaims(token.substring("Bearer ".length()));
            return new UsernamePasswordAuthenticationToken(claims, null);
        }
    }
}
```

- ### 컨트롤러와 서비스에서 JWT 토큰인증이 필요한 경우 확인하고 아닌경우 예외발생 

```java
// com.pinstagram.contents;

@RestController
@RequiredArgsConstructor
public class ContentsController {
    
    /*...*/
    
    @PostMapping
    ApiResponse createContents(
            Authentication authentication,
            @RequestBody @Validated CreateDto.Request request
    ) {
        try {
            return ApiResponse.createOK(service.create(authentication, request));
        } catch (ApiException e) {
            return ApiResponse.createException(e);
        }
    }

    /*...*/
}
```

```java

// com.pinstagram.contents.service;

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
        
        /*...*/
    }
}

```