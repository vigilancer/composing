package ae.ayeaye.guavapay.db

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "tokens")
data class Token(
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "price") val price: Float,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
)

@Dao
interface TokenDao {
    @Query("select * from tokens where code = :code")
    fun getAll(code: String): Flow<List<Token>>

    @Insert
    fun insert(token: Token)
}

@Database(entities = [Token::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
}
