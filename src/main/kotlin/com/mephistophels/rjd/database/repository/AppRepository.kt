package com.mephistophels.rjd.database.repository

import com.mephistophels.rjd.database.entity.AbstractEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface AppRepository<T : AbstractEntity> : CrudRepository<T, Long>