package run.cd80.tldr.api.manager.github.response

import com.google.gson.annotations.SerializedName

class UpdateHeadResponse {

    val ref: String = ""

    val url: String = ""

    @SerializedName("node_id")
    val nodeId: String = ""

    val `object`: UpdateHeadObjectResponse = UpdateHeadObjectResponse()
}
