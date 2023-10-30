package io.github.sammmm.springtutorial.orderservice;

public record Response<T>(int code, String status, T data) {
}
