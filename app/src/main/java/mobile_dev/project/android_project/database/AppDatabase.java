package mobile_dev.project.android_project.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mobile_dev.project.android_project.frag_Cocktail.Cocktail;

@Database(entities = {Ingredients.class, Cocktail.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract IngredientsDao IngredientsDao();
    public abstract CocktailsDao CocktailsDao();


    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "ingredients_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);


            //TODO delete
            databaseWriteExecutor.execute(()->{
                CocktailsDao dao = INSTANCE.CocktailsDao();

                dao.deleteAll();
                //Cocktail cocktail = new Cocktail("Margarita", true, "Mix it and die!", 	"https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg");
                //dao.insert(cocktail);*/
            });

        }
    };
}
