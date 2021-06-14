package com.lhwdev.math.projection3d

import com.lhwdev.math.pow2
import com.lhwdev.math.vector.MutableVector3d
import com.lhwdev.math.vector.Quaternion
import com.lhwdev.math.vector.Vector3d


class Viewport(rotation: Quaternion) {
	var rotation: Quaternion = rotation.toUnitQuaternion()
		set(value) {
			field = value.toUnitQuaternion()
		}
	
	fun translate(vector: Vector3d, output: MutableVector3d) {
		translate(vector.x, vector.y, vector.z, output)
	}
	
	fun translate(x: Float, y: Float, z: Float, output: MutableVector3d) {
		// val result = (rotation hamiltonProduct Quaternion(0f, x, y, z) hamiltonProduct rotation.reciprocal)
		// output.x = result.x
		// output.y = result.y
		// output.z = result.z
		// return
		
		// no any allocation
		
		// val point = Vector3d(x, y, z)
		// val rot = rotation
		val (rotW, rotX, rotY, rotZ) = rotation
		// val original = Quaternion(0f, point)
		
		// val trs = rotation hamiltonProduct point
		val trsW = /*    */  -rotX * x - rotY * y - rotZ * z
		val trsX = rotW * x /*      */ + rotY * z - rotZ * y
		val trsY = rotW * y - rotX * z /*      */ + rotZ * x
		val trsZ = rotW * z + rotX * y - rotY * x /*      */
		
		// reciprocal of rotation
		// val rot2 = rotation.reciprocal
		val rotPow2 = rotation.norm.pow2()
		val rot2W = rotW / rotPow2
		val rot2X = -rotX / rotPow2
		val rot2Y = -rotY / rotPow2
		val rot2Z = -rotZ / rotPow2
		
		// trs2 = trs hamiltonProduct rot2
		// val trs2W = trsW * rot2W - trsX * rot2X - trsY * rot2Y - trsZ * rot2Z
		val trs2X = trsW * rot2X + trsX * rot2W + trsY * rot2Z - trsZ * rot2Y
		val trs2Y = trsW * rot2Y - trsX * rot2Z + trsY * rot2W + trsZ * rot2X
		val trs2Z = trsW * rot2Z + trsX * rot2Y - trsY * rot2X + trsZ * rot2W
		
		// return (rotation hamiltonProduct original hamiltonProduct rotation.reciprocal).vectorPart
		output.x = trs2X
		output.y = trs2Y
		output.z = trs2Z
	}
}
