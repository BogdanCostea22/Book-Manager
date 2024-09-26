package ro.bcostea.book_manager

import com.github.tomakehurst.wiremock.common.ConsoleNotifier
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.cloud.contract.wiremock.WireMockConfigurationCustomizer
import org.springframework.context.annotation.Bean

@TestConfiguration
class WireMockConfig {
    @Bean
    fun optionsCustomizer(): WireMockConfigurationCustomizer {
        return WireMockConfigurationCustomizer { options ->
            // Enable console notifier
            options.notifier(ConsoleNotifier(true))
        }
    }
}