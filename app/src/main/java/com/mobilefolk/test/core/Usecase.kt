package com.mobilefolk.test.core

abstract class BaseUseCase<T> {}

abstract class UseCase<T, P> : BaseUseCase<T>() {
    abstract suspend fun execute(params: P): T
}

abstract class NoParamsUseCase<T> : BaseUseCase<T>() {
    abstract suspend fun execute(): T
}