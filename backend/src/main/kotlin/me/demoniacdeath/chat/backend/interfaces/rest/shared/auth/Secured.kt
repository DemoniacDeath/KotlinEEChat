package me.demoniacdeath.chat.backend.interfaces.rest.shared.auth

import javax.ws.rs.NameBinding

@NameBinding
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class Secured