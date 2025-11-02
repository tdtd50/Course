package com.zjsu.cxt.course.model;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private Integer code;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(Integer code, String message, T data) {
        this();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 成功响应静态方法
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data);
    }

    public static ApiResponse<?> success(String message) {
        return new ApiResponse<>(200, message, null);
    }

    public static ApiResponse<?> success() {
        return new ApiResponse<>(200, "Success", null);
    }

    // 错误响应静态方法
    public static ApiResponse<?> error(Integer code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    public static ApiResponse<?> error(String message) {
        return new ApiResponse<>(400, message, null);
    }

    // Getter和Setter方法
    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}