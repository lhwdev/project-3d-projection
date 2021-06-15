import com.lhwdev.math.model.parseWavefrontObj
import com.lhwdev.math.model.stringInput

@OptIn(ExperimentalJsExport::class)
@JsExport
fun renderMain(obj: String, rootElementId: String): () -> Unit =
	com.lhwdev.math.projection3d.renderMain(parseWavefrontObj(stringInput(obj)), rootElementId)
