package com.lhwdev.math.projection3d

import com.lhwdev.math.vector.Vector3d


data class Polygon3d(val points: Sequence<Vector3d>)


inline fun buildPolygon3d(block: Polygon3dBuilder.() -> Unit): Polygon3d = Polygon3dBuilder().apply(block).build()

class Polygon3dBuilder {
	val points: MutableList<Vector3d> = mutableListOf()
	
	fun point(x: Float, y: Float, z: Float) {
		point(Vector3d(x, y, z))
	}
	
	fun point(vector: Vector3d) {
		points += vector
	}
	
	fun close() {
		point(points[0])
	}
	
	
	fun build(): Polygon3d = Polygon3d(points.asSequence())
}
