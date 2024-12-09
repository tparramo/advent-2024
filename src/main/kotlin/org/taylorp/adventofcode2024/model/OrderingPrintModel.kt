package org.taylorp.adventofcode2024.model

data class OrderingPrintModel(
    val pageNumber: Int,
    val orderedBefore: MutableList<Int> = mutableListOf()
): Comparable<OrderingPrintModel>{
    override fun compareTo(other: OrderingPrintModel): Int {
        if(this.orderedBefore.contains(other.pageNumber)){
            return -1
        }else if(other.orderedBefore.contains(this.pageNumber)){
            return 1
        }else{
            return 0
        }
    }

}
