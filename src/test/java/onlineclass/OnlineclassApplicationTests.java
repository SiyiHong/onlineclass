package onlineclass;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Assert;
import onlineclass.model.entity.User;
import onlineclass.utils.JWTUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OnlineclassApplicationTests {

	@Test
	public void testGenJwt(){
		User user = new User();
		user.setId(20);
		user.setName("Colum");
		user.setHeadImg("png");
		String token = JWTUtils.genToken(user);
		System.out.println(token);
		Claims claims = JWTUtils.checkJWT(token);
		System.out.println(claims.get("name"));
	}


}
