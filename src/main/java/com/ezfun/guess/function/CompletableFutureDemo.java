package com.ezfun.guess.function;

import java.util.concurrent.CompletableFuture;

/**
 * @author SoySauce
 * @date 2019/11/29
 */
public class CompletableFutureDemo {
    public static Integer calc(Integer para) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return para * para;
    }

    public static Integer calcError(Integer para) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return para * para / 0;
    }

    public static void main(String[] args) throws Exception {
        CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> calc(50)).thenApply((i) -> Integer
                .toString(i)).thenApply(s -> "\"" + s + "\"");

        System.out.println("start");
        System.out.println(future.get());
        System.out.println("end");



        CompletableFuture<Void> futureError = CompletableFuture.supplyAsync(() -> calcError(50)).thenApply((i) ->
                Integer.toString(i)).thenApply(s -> "\"|" + s + "|\"").exceptionally(e -> "error").thenAccept(System.out::println);

        CompletableFuture<Void> futureCompose = CompletableFuture.supplyAsync(() -> calc(50))
                .thenCompose(i->CompletableFuture.supplyAsync(() -> calc(i)))
                .thenApply((i) -> Integer
                        .toString(i)).thenApply(s -> "\"|" + s + "|\"").exceptionally(e -> "error").thenAccept(System.out::println);

        CompletableFuture<Void> futureCombine = CompletableFuture.supplyAsync(() -> calc(50))
                .thenCombine(CompletableFuture.supplyAsync(() -> calc(50)),(a,b)->a-b)
                .thenApply((i) -> Integer
                        .toString(i)).thenApply(s -> "\"***" + s + "***\"").exceptionally(e -> "error").thenAccept(System
                        .out::println);

        futureCompose.get();
        futureError.get();
        futureError.get();
        futureCompose.get();
        futureCombine.get();
    }
}
