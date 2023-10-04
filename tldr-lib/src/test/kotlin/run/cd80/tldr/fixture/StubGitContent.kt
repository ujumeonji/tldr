package run.cd80.tldr.fixture

import run.cd80.tldr.lib.github.type.EncodeType
import run.cd80.tldr.lib.github.vo.GitContent

class StubGitContent : GitContent {

    private var _path: EncodeType = EncodeType.BASE64

    private var _content: String = ""

    override fun contentData(): String = _content

    override fun encodeType(): EncodeType = EncodeType.BASE64
}
