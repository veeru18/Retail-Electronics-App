package com.ecommerce.retail_electronicsapp.utility;

import java.util.Map;

public class TokenResponseStructure<T> {
    private boolean success;
    private T data;
    private Map<String, String> errors;

    public TokenResponseStructure(boolean success, T data, Map<String, String> errors) {
        this.success = success;
        this.data = data;
        this.errors = errors;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public Map<String, String> getMessage() {
        return errors;
    }

    public static <T> TokenResponseStructure<T> success(T data) {
        return new TokenResponseStructure<>(true, data, null);
    }

    public static <T> TokenResponseStructure<T> failure(Map<String, String> errors) {
        return new TokenResponseStructure<>(false, null, errors);
    }
}
