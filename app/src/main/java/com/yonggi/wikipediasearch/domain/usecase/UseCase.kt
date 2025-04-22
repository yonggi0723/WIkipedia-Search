package com.yonggi.wikipediasearch.domain.usecase

abstract class UseCase<Params, T> {

    abstract suspend fun execute(params : Params): T
}