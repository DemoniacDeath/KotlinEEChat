package me.demoniacdeath.chat.backend.interfaces.rest.shared.auth

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.FIELD,
        AnnotationTarget.VALUE_PARAMETER)
annotation class AuthenticatedUser
