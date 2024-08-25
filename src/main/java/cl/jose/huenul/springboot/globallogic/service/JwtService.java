package cl.jose.huenul.springboot.globallogic.service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cl.jose.huenul.springboot.globallogic.util.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

	@Value("${app.security.jwt.secret.key}")
	private String secretKey;

	@Value("${app.security.jwt.expiration-time}")
	private long jwtExpiration;

	private final DateUtil dateUtil;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		System.out.println();
		return claimsResolver.apply(claims);
	}

	public String generateToken(String email, Map<String, Object> extraClaims) {
		String token = Jwts.builder().setSubject(email).addClaims(extraClaims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(Date.from(dateUtil.getZonedActualDate().plusMinutes(jwtExpiration).toInstant()))
				.signWith(getSignInKey(), SignatureAlgorithm.HS512).compact();

		return token;
	}

	public boolean isTokenValid(String token) {
		return !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
