package com.lhwdev.math.vector

import kotlin.math.cos
import kotlin.math.sin


// cos(theta) + i sin(theta)
fun quaternionRotation(angleRadian: Float, axis: Vector3d): Quaternion =
	Quaternion(cos(angleRadian), sin(angleRadian) * axis)

// https://en.wikipedia.org/wiki/Conversion_between_quaternions_and_Euler_angles
fun eulerToQuaternionRotation(x: Float, y: Float, z: Float): Quaternion {
	val cy = cos(z * 0.5f)
	val sy = sin(z * 0.5f)
	val cp = cos(y * 0.5f)
	val sp = sin(y * 0.5f)
	val cr = cos(x * 0.5f)
	val sr = sin(x * 0.5f)
	
	return Quaternion(
		w = cr * cp * cy + sr * sp * sy,
		x = sr * cp * cy - cr * sp * sy,
		y = cr * sp * cy + sr * cp * sy,
		z = cr * cp * sy - sr * sp * cy
	)
}

fun eulerToQuaternionRotation(vector: Vector3d): Quaternion = eulerToQuaternionRotation(vector.x, vector.y, vector.z)
