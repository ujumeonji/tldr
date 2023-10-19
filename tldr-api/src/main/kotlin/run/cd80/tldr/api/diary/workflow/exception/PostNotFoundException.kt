package run.cd80.tldr.api.diary.workflow.exception

import org.springframework.http.HttpStatusCode
import org.springframework.web.client.HttpClientErrorException

class PostNotFoundException : HttpClientErrorException(HttpStatusCode.valueOf(404))
