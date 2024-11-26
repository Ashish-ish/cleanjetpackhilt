package com.ashish.cleanjetpackhilt.common.base

interface Mapper<F,T> {

    fun mapFrom(from:F):T

}