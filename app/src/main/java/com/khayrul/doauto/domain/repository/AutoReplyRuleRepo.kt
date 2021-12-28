package com.khayrul.doauto.domain.repository

import com.khayrul.doauto.domain.model.AutoReplyRule

interface AutoReplyRuleRepo {
    suspend fun getRules(): List<AutoReplyRule>
    suspend fun addRule(rule: AutoReplyRule)
    suspend fun deleteRule(rule: AutoReplyRule)
}