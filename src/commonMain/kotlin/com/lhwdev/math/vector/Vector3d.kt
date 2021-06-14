@file:Suppress("NOTHING_TO_INLINE")

package com.lhwdev.math.vector

import kotlin.math.sqrt


operator fun Float.times(other: Vector3d): Vector3d = other * this


class Vector3d(val x: Float, val y: Float, val z: Float): Vector {
	override val dimensions: Int get() = 3
	override val components: FloatArray get() = floatArrayOf(x, y, z)
	
	override val magnitude: Float get() = sqrt(x * x + y * y + z * z)
	
	fun distance(other: Vector3d): Float = (this - other).magnitude
	
	operator fun plus(other: Vector3d): Vector3d = Vector3d(x + other.x, y + other.y, z + other.z)
	
	operator fun minus(other: Vector3d): Vector3d = Vector3d(x - other.x, y - other.y, z - other.z)
	
	operator fun unaryMinus(): Vector3d = Vector3d(-x, -y, -z)
	
	operator fun times(other: Float): Vector3d = Vector3d(x * other, y * other, z * other)
	
	operator fun div(other: Float): Vector3d = Vector3d(x / other, y / other, z / other)
	
	infix fun dotProduct(other: Vector3d): Float = x * other.x + y * other.y + z * other.z
	
	infix fun crossProduct(other: Vector3d): Vector3d = Vector3d(
		x = y * other.z - z * other.y,
		y = z * other.x - x - other.z,
		z = x * other.y - y * other.x
	)
	
	inline operator fun component1(): Float = x
	inline operator fun component2(): Float = y
	inline operator fun component3(): Float = z
	
	
	override fun equals(other: Any?): Boolean = when {
		this === other -> true
		other !is Vector3d -> false
		else -> x == other.x && y == other.y && z == other.z
	}
	
	override fun hashCode(): Int = 31 * (31 * x.hashCode() + y.hashCode()) + z.hashCode()
	
	override fun toString(): String = "($x, $y, $z)"
}
