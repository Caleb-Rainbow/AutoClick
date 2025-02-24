package com.auto.click.`data`.script

import android.database.Cursor
import androidx.paging.PagingSource
import androidx.room.CoroutinesRoom
import androidx.room.EntityDeletionOrUpdateAdapter
import androidx.room.EntityInsertionAdapter
import androidx.room.EntityUpsertionAdapter
import androidx.room.RoomDatabase
import androidx.room.RoomSQLiteQuery
import androidx.room.RoomSQLiteQuery.Companion.acquire
import androidx.room.paging.LimitOffsetPagingSource
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.query
import androidx.sqlite.db.SupportSQLiteStatement
import com.auto.click.`data`.script.mode.Script
import java.lang.Class
import java.util.ArrayList
import java.util.concurrent.Callable
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.jvm.JvmStatic
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION"])
public class ScriptDao_Impl(
  __db: RoomDatabase,
) : ScriptDao {
  private val __db: RoomDatabase

  private val __deletionAdapterOfScript: EntityDeletionOrUpdateAdapter<Script>

  private val __upsertionAdapterOfScript: EntityUpsertionAdapter<Script>
  init {
    this.__db = __db
    this.__deletionAdapterOfScript = object : EntityDeletionOrUpdateAdapter<Script>(__db) {
      protected override fun createQuery(): String = "DELETE FROM `Script` WHERE `id` = ?"

      protected override fun bind(statement: SupportSQLiteStatement, entity: Script) {
        val _tmpId: Int? = entity.id
        if (_tmpId == null) {
          statement.bindNull(1)
        } else {
          statement.bindLong(1, _tmpId.toLong())
        }
      }
    }
    this.__upsertionAdapterOfScript = EntityUpsertionAdapter<Script>(object :
        EntityInsertionAdapter<Script>(__db) {
      protected override fun createQuery(): String =
          "INSERT INTO `Script` (`id`,`name`,`points`,`delay`,`duration`,`numberOfRuns`,`isRandomLocation`,`isRandomDelay`,`isRandomDuration`) VALUES (?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SupportSQLiteStatement, entity: Script) {
        val _tmpId: Int? = entity.id
        if (_tmpId == null) {
          statement.bindNull(1)
        } else {
          statement.bindLong(1, _tmpId.toLong())
        }
        statement.bindString(2, entity.name)
        statement.bindString(3, entity.points)
        statement.bindLong(4, entity.delay)
        statement.bindLong(5, entity.duration)
        statement.bindLong(6, entity.numberOfRuns.toLong())
        val _tmp: Int = if (entity.isRandomLocation) 1 else 0
        statement.bindLong(7, _tmp.toLong())
        val _tmp_1: Int = if (entity.isRandomDelay) 1 else 0
        statement.bindLong(8, _tmp_1.toLong())
        val _tmp_2: Int = if (entity.isRandomDuration) 1 else 0
        statement.bindLong(9, _tmp_2.toLong())
      }
    }, object : EntityDeletionOrUpdateAdapter<Script>(__db) {
      protected override fun createQuery(): String =
          "UPDATE `Script` SET `id` = ?,`name` = ?,`points` = ?,`delay` = ?,`duration` = ?,`numberOfRuns` = ?,`isRandomLocation` = ?,`isRandomDelay` = ?,`isRandomDuration` = ? WHERE `id` = ?"

      protected override fun bind(statement: SupportSQLiteStatement, entity: Script) {
        val _tmpId: Int? = entity.id
        if (_tmpId == null) {
          statement.bindNull(1)
        } else {
          statement.bindLong(1, _tmpId.toLong())
        }
        statement.bindString(2, entity.name)
        statement.bindString(3, entity.points)
        statement.bindLong(4, entity.delay)
        statement.bindLong(5, entity.duration)
        statement.bindLong(6, entity.numberOfRuns.toLong())
        val _tmp: Int = if (entity.isRandomLocation) 1 else 0
        statement.bindLong(7, _tmp.toLong())
        val _tmp_1: Int = if (entity.isRandomDelay) 1 else 0
        statement.bindLong(8, _tmp_1.toLong())
        val _tmp_2: Int = if (entity.isRandomDuration) 1 else 0
        statement.bindLong(9, _tmp_2.toLong())
        val _tmpId_1: Int? = entity.id
        if (_tmpId_1 == null) {
          statement.bindNull(10)
        } else {
          statement.bindLong(10, _tmpId_1.toLong())
        }
      }
    })
  }

  public override suspend fun delete(script: Script): Unit = CoroutinesRoom.execute(__db, true,
      object : Callable<Unit> {
    public override fun call() {
      __db.beginTransaction()
      try {
        __deletionAdapterOfScript.handle(script)
        __db.setTransactionSuccessful()
      } finally {
        __db.endTransaction()
      }
    }
  })

  public override suspend fun upsert(script: Script): Unit = CoroutinesRoom.execute(__db, true,
      object : Callable<Unit> {
    public override fun call() {
      __db.beginTransaction()
      try {
        __upsertionAdapterOfScript.upsert(script)
        __db.setTransactionSuccessful()
      } finally {
        __db.endTransaction()
      }
    }
  })

  public override fun getAllFlow(): Flow<List<Script>> {
    val _sql: String = "SELECT * FROM Script"
    val _statement: RoomSQLiteQuery = acquire(_sql, 0)
    return CoroutinesRoom.createFlow(__db, false, arrayOf("Script"), object : Callable<List<Script>>
        {
      public override fun call(): List<Script> {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _cursorIndexOfId: Int = getColumnIndexOrThrow(_cursor, "id")
          val _cursorIndexOfName: Int = getColumnIndexOrThrow(_cursor, "name")
          val _cursorIndexOfPoints: Int = getColumnIndexOrThrow(_cursor, "points")
          val _cursorIndexOfDelay: Int = getColumnIndexOrThrow(_cursor, "delay")
          val _cursorIndexOfDuration: Int = getColumnIndexOrThrow(_cursor, "duration")
          val _cursorIndexOfNumberOfRuns: Int = getColumnIndexOrThrow(_cursor, "numberOfRuns")
          val _cursorIndexOfIsRandomLocation: Int = getColumnIndexOrThrow(_cursor,
              "isRandomLocation")
          val _cursorIndexOfIsRandomDelay: Int = getColumnIndexOrThrow(_cursor, "isRandomDelay")
          val _cursorIndexOfIsRandomDuration: Int = getColumnIndexOrThrow(_cursor,
              "isRandomDuration")
          val _result: MutableList<Script> = ArrayList<Script>(_cursor.getCount())
          while (_cursor.moveToNext()) {
            val _item: Script
            val _tmpId: Int?
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null
            } else {
              _tmpId = _cursor.getInt(_cursorIndexOfId)
            }
            val _tmpName: String
            _tmpName = _cursor.getString(_cursorIndexOfName)
            val _tmpPoints: String
            _tmpPoints = _cursor.getString(_cursorIndexOfPoints)
            val _tmpDelay: Long
            _tmpDelay = _cursor.getLong(_cursorIndexOfDelay)
            val _tmpDuration: Long
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration)
            val _tmpNumberOfRuns: Int
            _tmpNumberOfRuns = _cursor.getInt(_cursorIndexOfNumberOfRuns)
            val _tmpIsRandomLocation: Boolean
            val _tmp: Int
            _tmp = _cursor.getInt(_cursorIndexOfIsRandomLocation)
            _tmpIsRandomLocation = _tmp != 0
            val _tmpIsRandomDelay: Boolean
            val _tmp_1: Int
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsRandomDelay)
            _tmpIsRandomDelay = _tmp_1 != 0
            val _tmpIsRandomDuration: Boolean
            val _tmp_2: Int
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsRandomDuration)
            _tmpIsRandomDuration = _tmp_2 != 0
            _item =
                Script(_tmpId,_tmpName,_tmpPoints,_tmpDelay,_tmpDuration,_tmpNumberOfRuns,_tmpIsRandomLocation,_tmpIsRandomDelay,_tmpIsRandomDuration)
            _result.add(_item)
          }
          return _result
        } finally {
          _cursor.close()
        }
      }

      protected fun finalize() {
        _statement.release()
      }
    })
  }

  public override fun pagingSource(): PagingSource<Int, Script> {
    val _sql: String = "SELECT * FROM Script"
    val _statement: RoomSQLiteQuery = acquire(_sql, 0)
    return object : LimitOffsetPagingSource<Script>(_statement, __db, "Script") {
      protected override fun convertRows(cursor: Cursor): List<Script> {
        val _cursorIndexOfId: Int = getColumnIndexOrThrow(cursor, "id")
        val _cursorIndexOfName: Int = getColumnIndexOrThrow(cursor, "name")
        val _cursorIndexOfPoints: Int = getColumnIndexOrThrow(cursor, "points")
        val _cursorIndexOfDelay: Int = getColumnIndexOrThrow(cursor, "delay")
        val _cursorIndexOfDuration: Int = getColumnIndexOrThrow(cursor, "duration")
        val _cursorIndexOfNumberOfRuns: Int = getColumnIndexOrThrow(cursor, "numberOfRuns")
        val _cursorIndexOfIsRandomLocation: Int = getColumnIndexOrThrow(cursor, "isRandomLocation")
        val _cursorIndexOfIsRandomDelay: Int = getColumnIndexOrThrow(cursor, "isRandomDelay")
        val _cursorIndexOfIsRandomDuration: Int = getColumnIndexOrThrow(cursor, "isRandomDuration")
        val _result: MutableList<Script> = ArrayList<Script>(cursor.getCount())
        while (cursor.moveToNext()) {
          val _item: Script
          val _tmpId: Int?
          if (cursor.isNull(_cursorIndexOfId)) {
            _tmpId = null
          } else {
            _tmpId = cursor.getInt(_cursorIndexOfId)
          }
          val _tmpName: String
          _tmpName = cursor.getString(_cursorIndexOfName)
          val _tmpPoints: String
          _tmpPoints = cursor.getString(_cursorIndexOfPoints)
          val _tmpDelay: Long
          _tmpDelay = cursor.getLong(_cursorIndexOfDelay)
          val _tmpDuration: Long
          _tmpDuration = cursor.getLong(_cursorIndexOfDuration)
          val _tmpNumberOfRuns: Int
          _tmpNumberOfRuns = cursor.getInt(_cursorIndexOfNumberOfRuns)
          val _tmpIsRandomLocation: Boolean
          val _tmp: Int
          _tmp = cursor.getInt(_cursorIndexOfIsRandomLocation)
          _tmpIsRandomLocation = _tmp != 0
          val _tmpIsRandomDelay: Boolean
          val _tmp_1: Int
          _tmp_1 = cursor.getInt(_cursorIndexOfIsRandomDelay)
          _tmpIsRandomDelay = _tmp_1 != 0
          val _tmpIsRandomDuration: Boolean
          val _tmp_2: Int
          _tmp_2 = cursor.getInt(_cursorIndexOfIsRandomDuration)
          _tmpIsRandomDuration = _tmp_2 != 0
          _item =
              Script(_tmpId,_tmpName,_tmpPoints,_tmpDelay,_tmpDuration,_tmpNumberOfRuns,_tmpIsRandomLocation,_tmpIsRandomDelay,_tmpIsRandomDuration)
          _result.add(_item)
        }
        return _result
      }
    }
  }

  public companion object {
    @JvmStatic
    public fun getRequiredConverters(): List<Class<*>> = emptyList()
  }
}
