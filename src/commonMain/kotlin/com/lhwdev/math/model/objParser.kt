package com.lhwdev.math.model

import com.lhwdev.math.vector.Vector3d


fun parseWavefrontObj(input: StringInput): Model3d = buildModel3d {
	val vertexList = mutableListOf<Vector3d>()
	val vertexNormalList = mutableListOf<Vector3d>()
	
	while(true) {
		val line = input.readLine()?.trim() ?: break
		val tokens = line.split(' ')
		when(tokens[0]) {
			"v" -> vertexList += Vector3d(tokens[1].toFloat(), tokens[2].toFloat(), tokens[3].toFloat())
			"vn" -> vertexNormalList += Vector3d(tokens[1].toFloat(), tokens[2].toFloat(), tokens[3].toFloat())
			
			"vt" -> Unit // uvs
			
			"f" -> face {
				for(token in tokens.drop(1)) {
					val vertex = token.split('/').first() // v1 v2 v3 ...
					val index = vertex.removePrefix("v").toInt()
					val realIndex = if(index >= 0) index - 1 else vertexList.size + index
					point(vertexList[realIndex])
				}
			}
			
			"l" -> line {
				for(token in tokens.drop(1)) {
					val index = token.removePrefix("v").toInt()
					val realIndex = if(index >= 0) index - 1 else vertexList.size + index
					point(vertexList[realIndex])
				}
			}
		}
	}
}
