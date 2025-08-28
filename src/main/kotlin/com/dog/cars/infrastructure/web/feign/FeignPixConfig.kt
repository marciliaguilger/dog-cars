package com.dog.cars.infrastructure.web.feign

import com.dog.cars.infrastructure.web.auth.TokenService
import feign.Request
import feign.RequestInterceptor
import feign.Response
import feign.codec.ErrorDecoder
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignPixConfig {

    @Bean
    fun authInterceptor(tokenServiceProvider: ObjectProvider<TokenService>): RequestInterceptor =
        RequestInterceptor { template ->
            val path = template.request().url()
            if (path.contains("/oauth/token")) {
                return@RequestInterceptor
            }
            val tokenService = tokenServiceProvider.ifAvailable
                ?: throw IllegalStateException("TokenService not available")
            val token = tokenService.getApplicationAccessToken()
            template.header("Authorization", "Bearer $token")
        }

    @Bean
    fun feignPixOptions(): Request.Options = Request.Options(
        5000, // connect timeout millis
        15000, // read timeout millis
        true // follow redirects
    )

    @Bean
    fun feignPixErrorDecoder(): ErrorDecoder = MercadoPagoErrorDecoder()
}

class MercadoPagoErrorDecoder : ErrorDecoder {
    private val logger = LoggerFactory.getLogger(MercadoPagoErrorDecoder::class.java)

    override fun decode(methodKey: String, response: Response): Exception {
        val status = response.status()
        val reason = response.reason() ?: ""
        val bodySnippet = try {
            response.body()?.asInputStream()?.bufferedReader()?.use { it.readText() }?.take(200)
        } catch (e: Exception) {
            null
        }
        val message = "Feign call failed ($methodKey) status=$status reason=$reason body=${bodySnippet ?: "<empty>"}"
        logger.warn(message)

        return when (status) {
            in 400..499 -> MercadoPagoClientException(message)
            in 500..599 -> MercadoPagoServerException(message)
            else -> Exception(message)
        }
    }
}

class MercadoPagoClientException(message: String) : RuntimeException(message)
class MercadoPagoServerException(message: String) : RuntimeException(message)


