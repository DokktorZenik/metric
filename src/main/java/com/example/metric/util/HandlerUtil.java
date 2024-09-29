package com.example.metric.util;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.core.EmbeddedWrapper;
import org.springframework.hateoas.server.core.EmbeddedWrappers;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HandlerUtil {
    public static <T> Mono<ServerResponse> singleResponse(T response) {
        return ServerResponse.ok()
                .body(Mono.just(response), new ParameterizedTypeReference<>() {
                });
    }

    public static <T> Mono<ServerResponse> listResponse(List<T> response) {
        final CollectionModel<T> model = CollectionModel.of(response);
        return ServerResponse.ok()
                .bodyValue(model);
    }

    public static <T> Mono<ServerResponse> listResponse(List<T> response, Class<T> tClass) {
        if (response.isEmpty()) {
            EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
            EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(tClass);
            return ServerResponse.ok().bodyValue(CollectionModel.of(List.of(wrapper)));
        }
        final CollectionModel<T> model = CollectionModel.of(response);
        return ServerResponse.ok()
                .bodyValue(model);
    }

    public static <T> Mono<T> requestToMono(ServerRequest request, Class<T> tClass) {
        return request.bodyToMono(tClass)
                .map(userRequest -> {
                    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
                    Set<ConstraintViolation<T>> violations = validator.validate(userRequest);

                    if (!violations.isEmpty()) {
                        List<String> messages = violations.stream()
                                .map(ConstraintViolation::getMessage).collect(Collectors.toList());
                        throw new RuntimeException(messages.stream().reduce("", String::concat));
                    }

                    return userRequest;
                });
    }

    @SafeVarargs
    public static RouterFunction<ServerResponse> routers(RouterFunction<ServerResponse>... routes) {
        return Arrays.stream(routes).reduce(RouterFunction::and).orElseThrow();
    }
}
