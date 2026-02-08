package com.web.web.utils;
//version introductoria:Jwts.parser() está deprecated.
//Esta clase se encarga de Crear, firmar, validar y leer JWTs
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;//cambiado a java 11 o 17
import java.security.Key;
import java.util.Date;

/**
 * @author Mahesh
 */
@Component// permite su inyección de dependencias (DI) mediante @Autowired en cualquier parte de la aplicación, facilitando la creación de componentes reutilizables sin instanciarlos manualmente.
public class JWTUtil {
    @Value("${security.jwt.secret}")
    private String key;//le carga lo de  application.properties a key(Clave secreta del server).valida tokens

    @Value("${security.jwt.issuer}")
    private String issuer;//Identifica quién emitió el token.es para validar que el token viene de tu backend

    @Value("${security.jwt.ttlMillis}")
    private long ttlMillis;//tiempo de vida del token,Seguridad contra robo de tokens

    private final Logger log = LoggerFactory
            .getLogger(JWTUtil.class);

    /**
     * Create a new token.
     *
     * @param id
     * @param subject
     * @return
     */
    public String create(String id, String subject) {//crea el JWT,Token

        // The JWT signature algorithm used to sign the token(algoritmo simétrico)
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);//fecha actual

        //  sign JWT with our ApiKey secret
        byte[] apiKeySecretBytes = Base64.getDecoder().decode(key);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());//Key criptográfica

        //  set the JWT Claims(el corazón del JWT)NO es secreto, cualquiera puede leerlos
        JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);//setear la expiracion del token
        }

        // Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    /**
     * Method to validate and read the JWT
     *
     * @param jwt
     * @return
     */
    public String getValue(String jwt) {//devuelve informacion del token.Permisos,roles de usuario.LOS LEE
        // This line will throw an exception if it is not a signed JWS (as
        // expected)Veridica token
        Claims claims = Jwts.parser().setSigningKey(Base64.getDecoder().decode(key))
                .parseClaimsJws(jwt).getBody();

        return claims.getSubject();
    }

    /**
     * Method to validate and read the JWT
     *
     * @param jwt
     * @return
     */
    public String getKey(String jwt) {
        // This line will throw an exception if it is not a signed JWS (as
        // expected)
        Claims claims = Jwts.parser().setSigningKey(Base64.getDecoder().decode(key))
                .parseClaimsJws(jwt).getBody();

        return claims.getId();//id del usuario
    }
}
