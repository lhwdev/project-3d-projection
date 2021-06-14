@file:Suppress("NOTHING_TO_INLINE")

package com.lhwdev.math.vector


/**
 * This exists only for the optimization.
 * Do not use this in general cases.
 */
class MutableVector3d(var x: Float = 0f, var y: Float = 0f, var z: Float = 0f) {
	fun invert() {
		x = -x
		y = -y
		z = -z
	}
	
	fun translate(x: Float, y: Float, z: Float) {
		this.x += x
		this.y += y
		this.z += z
	}
	
	inline fun copyTo(other: MutableVector3d) {
		other.x = x
		other.y = y
		other.z = z
	}
	
	inline fun toVector3d(): Vector3d = Vector3d(x, y, z)
}
