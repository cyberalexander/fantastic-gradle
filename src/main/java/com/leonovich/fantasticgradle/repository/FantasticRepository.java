package com.leonovich.fantasticgradle.repository;

public interface FantasticRepository<T, K> {

    T get(K fantasticGradleId);
}
