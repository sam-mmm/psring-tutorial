package io.github.sammmm.springtutorial.payloads;

public record Order(int id, Customer customer, Product product, int quantity, double totalPrice) {
}
