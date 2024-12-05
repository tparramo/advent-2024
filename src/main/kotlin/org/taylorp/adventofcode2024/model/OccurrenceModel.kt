package org.taylorp.adventofcode2024.model

data class OccurrenceModel(
    val value: Int,
    var occurrences: Int = 1
){

    fun increment(){
        this.occurrences++
    }
    override fun equals(other: Any?): Boolean {
        if(other is OccurrenceModel){
            val otherMapped = other as OccurrenceModel
            return otherMapped.value == this.value
        }else return false
    }
}
