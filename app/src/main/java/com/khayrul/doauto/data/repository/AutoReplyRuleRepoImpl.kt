package com.khayrul.doauto.data.repository

import com.khayrul.doauto.data.data_source.AutoReplyRuleDao
import com.khayrul.doauto.domain.model.AutoReplyRule
import com.khayrul.doauto.domain.repository.AutoReplyRuleRepo

class AutoReplyRuleRepoImpl(
    private val dao: AutoReplyRuleDao
): AutoReplyRuleRepo {
    override suspend fun getRules(): List<AutoReplyRule> {
        return dao.getRules()
    }

    override suspend fun addRule(rule: AutoReplyRule) {
        dao.addRule(rule)
    }

    override suspend fun deleteRule(rule: AutoReplyRule) {
        dao.deleteRule(rule)
    }
}