package com.spring.cloud;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Configuration
public class FilterConfig {


    /**
     * 根据IP地址来限制流量
     *
     * @return
     */
    @Bean
    KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    @Bean
    @Order(-1)
    public GlobalFilter a() {
        return (exchange, chain) -> {
            System.err.println("前置处理");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                System.err.println("后置处理");
            }));
        };
    }

    public static void main(String[] args) throws IOException, InterruptedException {
//        Flux.merge(Flux.range(0,5),Flux.range(2,5)).subscribe(System.out::println);
//        Flux.range(1, 10).reduceWith(() -> 10, (x, y) -> x + y).subscribe(System.out::println);
//        List<String> df = Flux.just("df").toStream().collect(Collectors.toList());
//        List<Object> t = Flux.empty().concatWith(Mono.just("t")).toStream().collect(Collectors.toList());
//        t.stream().forEach(System.out::println);
//        Mono.empty().concatWith(Mono.just("t")).then();
//        Mono.just("t").map(t -> t + "tt").flatMap(t -> {
//            System.out.println(t);
//            return Mono.empty().then(Mono.fromRunnable(() -> {
//                System.err.println("first post filter");
//            }));
//        }).subscribe();
//        Mono.just("dd").map(d->{
//            return d + "dfas";
//        }).switchIfEmpty(Mono.just("d")).subscribe(System.out::println);
        Mono<String> stringMono = Mono.just("t").flatMap(s -> Mono.subscriberContext().map(c -> s + c.get("test"))).subscriberContext(ctx -> ctx.put("test", "World"));
        stringMono.subscribe(System.out::println);
    }


}
