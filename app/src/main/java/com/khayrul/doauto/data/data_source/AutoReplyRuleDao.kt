package com.khayrul.doauto.data.data_source

import androidx.room.*
import com.khayrul.doauto.domain.model.AutoReplyRule

@Dao
interface AutoReplyRuleDao {

    @Query("SELECT * from autoReplyRule")
    suspend fun getRules(): List<AutoReplyRule>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRule(rule: AutoReplyRule)

    @Delete
    suspend fun deleteRule(rule: AutoReplyRule)
}