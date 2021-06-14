package com.lhwdev.math.projection3d

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp


fun Modifier.grid(margin: Dp, color: Color): Modifier = drawBehind {
	val m = margin.toPx()
	val w = size.width
	val h = size.height
	
	for(x in 1 until (w / m).toInt()) {
		val xPos = x * m
		drawLine(color, Offset(xPos, 0f), Offset(xPos, h))
	}
	
	for(y in 1 until (h / m).toInt()) {
		val yPos = y * m
		drawLine(color, Offset(0f, yPos), Offset(w, yPos))
	}
}
