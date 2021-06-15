package com.lhwdev.math.projection3d

import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp
import com.lhwdev.math.model.buildModel3d
import com.lhwdev.math.vector.MutableVector3d
import com.lhwdev.math.vector.Vector3d
import com.lhwdev.math.vector.eulerToQuaternionRotation


fun main() = Window {
	var text by remember { mutableStateOf("Hello, World!") }
	var rotation by remember { mutableStateOf(Vector3d(0f, 0f, 0f)) }
	
	MaterialTheme {
		Box(
			Modifier
				.projectionMain(rotation)
				.pointerMoveFilter(onMove = {
					rotation = Vector3d(it.y, it.x, 0f)
					false
				})
				.fillMaxSize()
		)
	}
}


fun Modifier.projectionMain(rotation: Vector3d): Modifier = composed {
	val viewport = remember(rotation) { Viewport(eulerToQuaternionRotation(rotation / 20f)) }
	
	val square = remember {
		buildModel3d {
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
	
	grid(margin = 20.dp, color = Color.LightGray).drawBehind {
		// allocation #1, #2
		val thisPoint = MutableVector3d()
		val nextPoint = MutableVector3d()
		
		// allocation #3
		val iterator = square.points.iterator()
		
		viewport.translate(iterator.next(), nextPoint)
		
		val translateX = size.width / 2
		val translateY = size.height / 2
		nextPoint.translate(translateX, translateY, 0f)
		
		iterator.forEach {
			nextPoint.copyTo(thisPoint)
			viewport.translate(it, nextPoint)
			nextPoint.translate(translateX, translateY, 0f)
			
			drawLine(
				color = Color.Black,
				// allocation #3+n, #3+2n
				start = Offset(thisPoint.x, thisPoint.y), end = Offset(nextPoint.x, nextPoint.y),
				strokeWidth = 3f
			)
		}
	}
}
