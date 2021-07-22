package com.iamshekhargh.jsonplaceholder.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by <<-- iamShekharGH -->>
 * on 22 July 2021, Thursday
 * at 2:07 AM
 */

fun <ResultType, RequestType> makeApiCall(
    makeApiCall: suspend () -> Flow<ResultType>,
    shouldFetch: (ResultType) -> Boolean = { true }


) = flow {

    val data = makeApiCall
    emit(Resource.Loading(data))
//    val resultFlow = if (shouldFetch(data)){
//
//    }

}