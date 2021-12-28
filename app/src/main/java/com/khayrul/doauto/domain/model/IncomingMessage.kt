package com.khayrul.doauto.domain.model
 
data class IncomingMessage(
    val rule: String,
    val text: String? = null
) {
    companion object {
        const val ALL_MESSAGE = "all_message"
        const val START_WITH = "start_with"
        const val END_WITH = "end_with"
        const val EXACT_MATCH = "exact_match"
    }
}
