package com.example.recipeapp.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.recipeapp.Model.DAO.AccountDAO;
import com.example.recipeapp.Model.DAO.CategoryDAO;
import com.example.recipeapp.Model.DAO.FavoriteRecipeDAO;
import com.example.recipeapp.Model.DAO.RecipeDAO;
import com.example.recipeapp.Model.DAO.ReviewDAO;
import com.example.recipeapp.Model.DAO.UserDAO;
import com.example.recipeapp.Model.Entity.Account;
import com.example.recipeapp.Model.Entity.Category;
import com.example.recipeapp.Model.Entity.FavoriteRecipe;
import com.example.recipeapp.Model.Entity.Recipe;
import com.example.recipeapp.Model.Entity.Review;
import com.example.recipeapp.Model.Entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Account.class, Category.class, User.class, Recipe.class, Review.class, FavoriteRecipe.class},version = 5,exportSchema = true)
public abstract class DBConnection extends RoomDatabase {
    public abstract AccountDAO createAccountDao();
    public abstract CategoryDAO createCategoryDao();
    public abstract RecipeDAO createRecipeDao();
    public abstract ReviewDAO createReviewDao();
    public abstract UserDAO createUserDao();
    public abstract FavoriteRecipeDAO createFavoriteRecipeDAO();
    private static volatile DBConnection INSTANCE;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE review_new (" +
                            "reviewID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "rating INTEGER NOT NULL, " +
                            "comment TEXT, " +
                            "userID INTEGER NOT NULL, " +
                            "recipeID INTEGER NOT NULL, " +
                            "FOREIGN KEY(userID) REFERENCES User(userID) ON DELETE CASCADE, " +
                            "FOREIGN KEY(recipeID) REFERENCES Recipe(recipeID) ON DELETE CASCADE" +
                            ")"
            );

            // 2. Copy dữ liệu từ bảng cũ sang bảng mới
            database.execSQL(
                    "INSERT INTO review_new (reviewID, rating, comment, userID, recipeID) " +
                            "SELECT reviewID, rating, comment, userID, recipeID FROM Review"
            );

            // 3. Xóa bảng Review cũ
            database.execSQL("DROP TABLE Review");

            // 4. Đổi tên bảng mới thành Review
            database.execSQL("ALTER TABLE review_new RENAME TO Review");
        }
    };

    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS FavoriteRecipe (" +
                            "favoriteRecipeID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "recipeID INTEGER NOT NULL, " +
                            "userID INTEGER NOT NULL, " +
                            "favorite INTEGER NOT NULL, " +
                            "FOREIGN KEY(recipeID) REFERENCES Recipe(recipeID) ON DELETE CASCADE, " +
                            "FOREIGN KEY(userID) REFERENCES User(userID) ON DELETE CASCADE" +
                            ")"
            );
        }
    };

    public static DBConnection getINSTANCE(Context context){
        if(INSTANCE == null){
            synchronized (DBConnection.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    DBConnection.class,
                                    "recipe_database")
                            .addMigrations(MIGRATION_3_4,MIGRATION_4_5)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
