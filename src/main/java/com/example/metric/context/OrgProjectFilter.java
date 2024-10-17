package com.example.metric.context;


import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.metric.context.ProjectContextHolder.setContext;

@Component
@RequiredArgsConstructor
public class OrgProjectFilter implements WebFilter {

    private static final Pattern URL_PATTERN = Pattern.compile("\\/v1/organizations\\/([^\\/]+)\\/projects\\/([^\\/]+)/[^\\/]+");

    private final SQLiteService sqLiteService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        Matcher matcher = URL_PATTERN.matcher(path);

        if (matcher.matches()) {
            String orgName = matcher.group(1);
            String projectName = matcher.group(2);

            return sqLiteService.getIds(orgName, projectName)
                    .flatMap(tuple -> {
                        setContext(new ProjectContext(tuple.getT1(), tuple.getT2()));
                        return chain.filter(exchange);
                    })
                    .onErrorResume(e -> {
                        exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);

                        DataBuffer buffer = exchange.getResponse()
                                .bufferFactory()
                                .wrap(e.getMessage().getBytes(StandardCharsets.UTF_8));
                        exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);

                        return exchange.getResponse().writeWith(Mono.just(buffer));
                    });
        }

        return chain.filter(exchange);
    }
}
