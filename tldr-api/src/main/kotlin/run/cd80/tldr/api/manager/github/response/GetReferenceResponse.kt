package run.cd80.tldr.api.manager.github.response

import com.google.gson.annotations.SerializedName

class GetReferenceResponse {

    val ref: String = ""

    val url: String = ""

    val `object`: GetReferenceObjectResponse = GetReferenceObjectResponse()

    @SerializedName("node_id")
    val nodeId: String = ""
}
