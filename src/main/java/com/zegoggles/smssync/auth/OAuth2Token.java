package com.zegoggles.smssync.auth;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

public class OAuth2Token {
    public final String accessToken;
    public final String tokenType;
    public final String refreshToken;
    public final int expiresIn;
    public final String userName;

    public OAuth2Token(String accessToken, String tokenType, String refreshToken, int expiresIn, String userName) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.userName = userName;
    }

    public static OAuth2Token fromJSON(String string) throws IOException {
        try {
            Object value = new JSONTokener(string).nextValue();
            if (value instanceof JSONObject) {
                return fromJSON((JSONObject) value);
            } else {
                throw new IOException("Invalid JSON data: "+value);
            }
        } catch (JSONException e) {
            throw new IOException("Error parsing data: "+e.getMessage());
        }
    }

    public static OAuth2Token fromJSON(JSONObject object) throws IOException {
        try {
            return new OAuth2Token(
                object.getString("access_token"),
                object.getString("token_type"),
                object.getString("refresh_token"),
                object.getInt("expires_in"), null);
        } catch (JSONException e) {
            throw new IOException("parse error");
        }
    }

    @Override
    public String toString() {
        return "Token{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", userName='" + userName + '\'' +
                '}';
    }
}
