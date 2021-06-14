package com.lhwdev.math.projection3d

import androidx.compose.runtime.*
import com.lhwdev.math.vector.MutableVector3d
import com.lhwdev.math.vector.Vector3d
import com.lhwdev.math.vector.eulerToQuaternionRotation
import kotlinx.browser.document
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent


fun main() {
	renderComposable(rootElementId = "root") {
		var rotation by remember { mutableStateOf(Vector3d(0f, 0f, 0f)) }
		val viewport by remember { derivedStateOf { Viewport(eulerToQuaternionRotation(rotation / 20f)) } }
		
		DisposableEffect(Unit) {
			val callback = { event: Event ->
				event as MouseEvent
				println("mouse!")
				rotation = Vector3d(event.pageX.toFloat(), event.pageY.toFloat(), 0f)
			}
			document.addEventListener("mousemove", callback)
			onDispose { document.removeEventListener("mousemove", callback) }
		}
		
		val shape = remember {
			buildPolygon3d {
				val l = 80f
				point(-l, -l, l)
				point(-l, -l, -l)
				point(l, -l, -l)
				point(l, -l, l)
				point(l, l, l)
				point(l, l, -l)
				point(-l, l, -l)
				point(-l, l, l)
				point(-l, -l, l)
				close()
			}
		}
		
		Canvas(width = 1000.0, height = 800.0) {
			clearRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
			println("draw!")
			fillStyle = "#000000"
			val point = MutableVector3d()
			
			val iterator = shape.points.iterator()
			
			viewport.translate(iterator.next(), point)
			
			beginPath()
			
			val translateX = canvas.width / 2f
			val translateY = canvas.height / 2f
			moveTo((point.x + translateX).toDouble(), (point.y + translateY).toDouble())
			
			iterator.forEach {
				viewport.translate(it, point)
				lineTo((point.x + translateX).toDouble(), (point.y + translateY).toDouble())
			}
			
			closePath()
			stroke()
		}
	}
}
