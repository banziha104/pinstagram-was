import com.pinstagram.jwt.JwtManager;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtManagerTest {

    String token;
    JwtManager jwtManager;
    @BeforeEach
    public void setUp(){
        jwtManager = new JwtManager();
        token = jwtManager.createToken("test@test.com",1L);

    }
    @Test
    public void 토큰생성(){
        System.out.println(token);
        assertNotNull(token);
        assertNotEquals(token,"");
    }

    @Test
    public void 토큰검사() {
        Claims claims = jwtManager.getClaims(token);
        System.out.println(claims);
        assertEquals(claims.get("id"),1);
        assertEquals(claims.get("email"),"test@test.com");
    }
}