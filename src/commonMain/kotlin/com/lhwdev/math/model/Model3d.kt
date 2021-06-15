package com.lhwdev.math.model

import com.lhwdev.math.vector.Vector3d


data class Model3d(val elements: List<ModelElement3d>)

sealed interface ModelElement3d

data class Line3d(val points: List<Vector3d>) : ModelElement3d

data class Face3d(val points: List<Vector3d>) : ModelElement3d


inline fun buildModel3d(block: Model3dBuilder.() -> Unit): Model3d = Model3dBuilder().apply(block).build()

class Model3dBuilder {
	val elements: MutableList<ModelElement3d> = mutableListOf()
	
	class ModelPointsBuilder {
		val line = mutableListOf<Vector3d>()
		
		fun point(vector: Vector3d) {
			line += vector
		}
		
		fun point(x: Float, y: Float, z: Float) {
			point(Vector3d(x, y, z))
		}
	}
	
	inline fun line(block: ModelPointsBuilder.() -> Unit) {
		val line = ModelPointsBuilder().apply(block).line
		elements += Line3d(line)
	}
	
	inline fun polygon(block: ModelPointsBuilder.() -> Unit) {
		val line = ModelPointsBuilder().apply(block).line
		if(line.last() != line.first()) line += line[0] // close shape
		elements += Line3d(line)
	}
	
	inline fun face(block: ModelPointsBuilder.() -> Unit) {
		val line = ModelPointsBuilder().apply(block).line
		if(line.last() != line.first()) line += line[0] // close shape
		elements += Face3d(line)
	}
	
	
	fun build(): Model3d = Model3d(elements)
}
