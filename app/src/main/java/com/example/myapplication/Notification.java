package com.example.myapplication;

public class Notification {
    private String message;
    // 다른 필요한 데이터 필드를 추가할 수 있습니다.

    public Notification() {
    }

    public Notification(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    // 다른 필요한 getter 및 setter 메서드를 추가할 수 있습니다.
}