package com.example.metric.context;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.metric.context.ProjectContextHolder.setContext;

@Component
@RequiredArgsConstructor
public class OrgProjectFilter implements WebFilter {

    private static final Pattern URL_PATTERN = Pattern.compile("\\/organizations\\/([^\\/]+)\\/projects\\/([^\\/]+)");

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
                        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                        return exchange.getResponse().setComplete();
                    });
        }

        return chain.filter(exchange);
    }
}
