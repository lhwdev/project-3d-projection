package com.lhwdev.math.model


interface StringInput {
	fun readLine(): String?
}


fun stringInput(text: String): StringInput = object : StringInput {
	private val lines = text.splitToSequence('\n').iterator() // TODO: \n \r\n \r
	
	override fun readLine(): String? = if(lines.hasNext()) lines.next() else null
}
