package com.food.winterfoodies2.service;

import com.food.winterfoodies2.entity.Snack;
import reactor.core.publisher.Mono;

import java.util.List;

public interface SnackService {
    Mono<List<Snack>> getNearbySnacks(double lat, double lon);
}
