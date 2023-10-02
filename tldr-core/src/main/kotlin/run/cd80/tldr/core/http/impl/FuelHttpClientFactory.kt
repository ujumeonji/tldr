package run.cd80.tldr.core.http.impl

import run.cd80.tldr.core.http.HttpClient
import run.cd80.tldr.core.http.HttpClientFactory

class FuelHttpClientFactory : HttpClientFactory {

    override fun create(): HttpClient = FuelHttpClient()
}
