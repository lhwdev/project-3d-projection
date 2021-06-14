@file:Suppress("NOTHING_TO_INLINE")

package com.lhwdev.math.vector

import com.lhwdev.math.pow2
import kotlin.math.sqrt


operator fun Float.times(other: Quaternion): Quaternion = other * this


class Quaternion(val w: Float, val x: Float, val y: Float, val z: Float) : Vector {
	constructor(scalarPart: Float, vectorPart: Vector3d) : this(scalarPart, vectorPart.x, vectorPart.y, vectorPart.z)
	
	
	companion object {
		val identity: Quaternion = Quaternion(1f, 0f, 0f, 0f)
	}
	
	override val dimensions: Int get() = 4
	override val components: FloatArray get() = floatArrayOf(w, x, y, z)
	override val magnitude: Float get() = norm
	
	val norm: Float get() = sqrt(w * w + x * x + y * y + z * z)
	
	val scalarPart: Float get() = w
	val vectorPart: Vector3d get() = Vector3d(x, y, z)
	
	val conjugate: Quaternion get() = Quaternion(w, -x, -y, -z)
	
	val reciprocal: Quaternion get() = conjugate / norm.pow2()
	
	fun distance(other: Quaternion): Float = (this - other).norm
	
	operator fun plus(other: Quaternion): Quaternion = Quaternion(w + other.w, x + other.x, y + other.y, z + other.z)
	
	operator fun minus(other: Quaternion): Quaternion = Quaternion(w - other.w, x - other.x, y - other.y, z - other.z)
	
	operator fun unaryMinus(): Quaternion = Quaternion(-w, -x, -y, -z)
	
	operator fun times(other: Float): Quaternion = Quaternion(w * other, x * other, y * other, z * other)
	
	operator fun div(other: Float): Quaternion = Quaternion(w / other, x / other, y / other, z / other)
	
	infix fun dotProduct(other: Quaternion): Float = w * other.w + x * other.x + y * other.y + z * other.z
	
	infix fun hamiltonProduct(other: Quaternion): Quaternion = Quaternion(
		w = w * other.w - x * other.x - y * other.y - z * other.z,
		x = w * other.x + x * other.w + y * other.z - z * other.y,
		y = w * other.y - x * other.z + y * other.w + z * other.x,
		z = w * other.z + x * other.y - y * other.x + z * other.w
	)
	
	fun toUnitQuaternion(): Quaternion = this / norm
	
	inline operator fun component1(): Float = w
	inline operator fun component2(): Float = x
	inline operator fun component3(): Float = y
	inline operator fun component4(): Float = z
	
	
	override fun equals(other: Any?): Boolean = when {
		this === other -> true
		other !is Quaternion -> false
		else -> w == other.w && x == other.x && y == other.y && z == other.z
	}
	
	override fun hashCode(): Int = 31 * (31 * (31 * w.hashCode() + x.hashCode()) + y.hashCode()) + z.hashCode()
	
	override fun toString(): String = "$w+${x}i+${y}j+${z}k"
}

