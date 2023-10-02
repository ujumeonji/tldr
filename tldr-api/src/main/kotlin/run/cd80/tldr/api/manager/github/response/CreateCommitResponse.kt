package run.cd80.tldr.api.manager.github.response

import com.google.gson.annotations.SerializedName

class CreateCommitResponse {

    val sha: String = ""

    val url: String = ""

    @SerializedName("html_url")
    val htmlUrl: String = ""

    val author: Author = Author()

    val committer: Author = Author()

    val message: String = ""

    val tree: Tree = Tree()

    val parents: List<Parent> = listOf()

    val verification: Verification = Verification()

    class Author {

        val name: String = ""

        val email: String = ""

        val date: String = ""
    }

    class Tree {

        val url: String = ""

        val sha: String = ""
    }

    class Parent {

        val url: String = ""

        val sha: String = ""

        @SerializedName("html_url")
        val htmlUrl: String = ""
    }

    class Verification {

        val verified: Boolean = false

        val reason: String = ""

        val signature: String? = null

        val payload: String? = null
    }
}
