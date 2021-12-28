package com.khayrul.doauto.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AutoReplyRule(
    @PrimaryKey
    val id: Long? = null,
    val title: String,
    val message: String,
    val timeDelay: Long? = null,
    val phoneStatusConstraint: String? = null,
    @Embedded val messageFiltering: IncomingMessage = IncomingMessage(rule = IncomingMessage.ALL_MESSAGE),
    val isActive: Boolean = false
)
