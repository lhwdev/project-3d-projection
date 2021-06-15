package com.lhwdev.math.projection3d

import androidx.compose.runtime.*
import com.lhwdev.math.vector.Vector3d
import kotlinx.browser.document
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.Tag
import org.jetbrains.compose.web.css.CSSUnitValue
import org.jetbrains.compose.web.css.buildCSS
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.dom.ElementScope
import org.jetbrains.compose.web.dom.ElementScopeBase
import org.jetbrains.compose.web.dom.TagElement
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.Element
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent


object CanvasTag : Tag


@Composable
inline fun <reified E : Event> EventListener(type: String, crossinline callback: (E) -> Unit) {
	DisposableEffect(type) {
		val instance = { event: Event ->
			callback(event as E)
		}
		document.addEventListener(type, instance)
		onDispose { document.removeEventListener(type, instance) }
	}
}

@Composable
fun Canvas(width: Double? = null, height: Double? = null, onDraw: CanvasRenderingContext2D.() -> Unit) {
	TagElement<CanvasTag, HTMLCanvasElement>(
		tagName = "canvas",
		applyAttrs = {
			if(width != null) attr("width", width.toString())
			if(height != null) attr("height", height.toString())
		}
	) {
		DomLaunchedEffect(Unit) { canvas ->
			val context = canvas.getContext("2d") as CanvasRenderingContext2D
			
			snapshotFlow {
				context.onDraw()
			}.collect()
		}
	}
}


// @Composable
// fun <TElement : Element> ElementScope<TElement>.DomLaunchedEffect(
// 	vararg keys: Any,
// 	block: suspend CoroutineScope.(TElement) -> Unit
// ) {
// 	val scope = this as ElementScopeBase
//
// 	LaunchedEffect(scope.element, *keys) {
// 		block(scope.element)
// 	}
// }

@OptIn(InternalComposeApi::class)
@Composable
fun <TElement : Element> ElementScope<TElement>.DomLaunchedEffect(
	key: Any?,
	block: suspend CoroutineScope.(TElement) -> Unit
) {
	val applyContext = currentComposer.applyCoroutineContext
	
	DisposableRefEffect(key) { canvas ->
		val job = CoroutineScope(applyContext).launch { block(canvas) }
		onDispose { job.cancel("Old job was still running!") }
	}
}
