package onlineclass.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import onlineclass.model.entity.User;

import java.util.Date;

public class JWTUtils {
    private static final long EXPIRE = 60000 * 60 * 24 * 7;//定义过期时间为一周
    private static final String TOKEN_PRE = "love";//定义令牌前缀
    private static final String SECRET = "alin.920";//定义密钥
    private static final String SUBJECT = "hong";//定义谁颁布的

    public static String genToken(User user){
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("id",user.getId())//设置payload
                .claim("name",user.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE))//设置过期时间
                .signWith(SignatureAlgorithm.HS256,SECRET).compact();//设置签名
        token = TOKEN_PRE+token;
        return token;
    }
    public static Claims checkJWT(String token){
        try {
            final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PRE,"")).getBody();
            return claims;
        }catch (Exception e){
            return null;
        }

    }
}
