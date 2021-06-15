package com.lhwdev.math.projection3d

import androidx.compose.runtime.*
import com.lhwdev.math.model.Model3d
import com.lhwdev.math.model.parseWavefrontObj
import com.lhwdev.math.model.stringInput
import com.lhwdev.math.vector.MutableVector3d
import com.lhwdev.math.vector.Quaternion
import com.lhwdev.math.vector.Vector3d
import com.lhwdev.math.vector.eulerToQuaternionRotation
import org.jetbrains.compose.web.css.AlignSelf
import org.jetbrains.compose.web.css.alignSelf
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.events.MouseEvent
import org.w3c.dom.events.WheelEvent


@Stable
class ViewportState {
	var rotation: Quaternion by mutableStateOf(Quaternion(1f, 0f, 0f, 0f))
	var scale: Float by mutableStateOf(1f)
	var qualityFactor: Float by mutableStateOf(1f) // not used actually
	
	val backend = Viewport()
		get() {
			field.rotation = rotation
			field.scale = scale
			field.qualityFactor = qualityFactor
			return field
		}
}

fun main() {
	renderMain(
		parseWavefrontObj(stringInput(objString)),
		"root"
	)
}



fun renderMain(obj: Model3d, rootElementId: String): () -> Unit {
	val composition = renderComposable(rootElementId = rootElementId) {
		val viewport by remember { mutableStateOf(ViewportState()) }
		var message by remember { mutableStateOf<String?>(null) }
		
		var isShiftPressed by remember { mutableStateOf(false) }
		
		EventListener<KeyboardEvent>("keypress") { event ->
			isShiftPressed = event.shiftKey
		}
		
		EventListener<MouseEvent>("mousemove") { event ->
			viewport.rotation =
				eulerToQuaternionRotation(Vector3d(event.pageY.toFloat(), -event.pageX.toFloat(), 0f) / 20f)
		}
		
		EventListener<WheelEvent>("wheel") { event ->
			if(isShiftPressed) {
				// quality
				viewport.qualityFactor = (viewport.qualityFactor - event.deltaY.toFloat() * 0.005f).coerceIn(0.1f, 1f)
				message = "Quality factor: ${viewport.qualityFactor}"
			} else {
				// scale
				viewport.scale = (viewport.scale - event.deltaY.toFloat() * 0.015f).coerceIn(0.5f, 100f)
				message = "Scale: ${viewport.scale * 100f} %"
			}
		}
		
		
		Canvas(width = 1000.0, height = 800.0) {
			fillStyle = "#f5f5f5"
			fillRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
			fillStyle = "#00000033"
			val point = MutableVector3d()
			
			save()
			translate(canvas.width / 2.0, canvas.height / 2.0)
			
			renderModelJs(viewport.backend, obj)
			
			restore()
		}
		
		Span(attrs = {
			style {
				alignSelf(AlignSelf.SelfEnd)
			}
		}) {
			Text(message ?: "")
		}
	}
	
	return { composition.dispose() }
}
