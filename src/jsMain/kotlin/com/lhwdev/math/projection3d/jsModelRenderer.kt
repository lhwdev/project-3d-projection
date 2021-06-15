package com.lhwdev.math.projection3d

import com.lhwdev.math.model.Face3d
import com.lhwdev.math.model.Line3d
import com.lhwdev.math.model.Model3d
import com.lhwdev.math.vector.MutableVector3d
import org.w3c.dom.CanvasRenderingContext2D


fun CanvasRenderingContext2D.renderModelJs(viewport: Viewport, model3d: Model3d) {
	val cache = MutableVector3d()
	
	for(element in model3d.elements) when(element) {
		is Line3d -> {
			beginPath()
			viewport.translate(element.points.first(), cache)
			moveTo(cache.x.toDouble(), cache.y.toDouble())
			for(point in element.points.drop(1)) {
				viewport.translate(point, cache)
				lineTo(cache.x.toDouble(), cache.y.toDouble())
			}
			stroke()
		}
		is Face3d -> {
			beginPath()
			viewport.translate(element.points.first(), cache)
			moveTo(cache.x.toDouble(), cache.y.toDouble())
			for(point in element.points.drop(1)) {
				viewport.translate(point, cache)
				lineTo(cache.x.toDouble(), cache.y.toDouble())
			}
			fill()
		}
	}
}
