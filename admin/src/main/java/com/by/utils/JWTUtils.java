package com.by.utils;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.by.model.Member;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {
    private final static String secrect = "crm";

    public static String encode(Member member) {
        JWTSigner signer = new JWTSigner(secrect);
        Map<String, Object> u = new HashMap<>();
        u.put("member", new MemberJWT(member));
        return signer.sign(u);
    }

    public static Map<Object, Object> decode(String token) throws InvalidKeyException, NoSuchAlgorithmException,
            IllegalStateException, SignatureException, IOException, JWTVerifyException {
        Map<String, Object> decodedPayload = new JWTVerifier(secrect).verify(token);
        Map<Object, Object> map = (Map<Object, Object>) decodedPayload.get("member");
        return map;
    }

    public static Member extractUser(HttpServletRequest request) throws InvalidKeyException, NoSuchAlgorithmException,
            IllegalStateException, SignatureException, IOException, JWTVerifyException {
        String authorization = request.getHeader("Authorization");
        if (authorization == null)
            throw new SecurityException();
        String[] tokens = authorization.split(" ");
        if (tokens.length != 2 && !tokens[0].equals("Bearer"))
            throw new SecurityException();
        Map<Object, Object> map = JWTUtils.decode(tokens[1]);
        String name = (String) map.get("name");
        Integer id = (Integer) map.get("id");
        return new Member(id.longValue(), name);
    }

    private static class MemberJWT {
        private Long id;
        private String name;

        public MemberJWT() {
        }

        public MemberJWT(Member member) {
            this.id = member.getId();
            this.name = member.getName();
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
