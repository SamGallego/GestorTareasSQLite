package gal.cifpacarballeira.unidad4_tarea7gestordeberes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BaseDatos extends SQLiteOpenHelper {

    private static final String nombreBD = "BaseDeDatos";
    private static final int versionBD = 1;

    public BaseDatos(@Nullable Context context) {
        super(context, nombreBD, null, versionBD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE tareas (id INTEGER PRIMARY KEY AUTOINCREMENT, subject TEXT, description TEXT, dueDate TEXT, isCompleted INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tareas");
        onCreate(sqLiteDatabase);
    }

    // Método para insertar una tarea
    public void insertarTarea(Homework homework) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("subject", homework.getSubject());
        valores.put("description", homework.getDescription());
        valores.put("dueDate", homework.getDueDate());
        valores.put("isCompleted", homework.isCompleted() ? 1 : 0);
        db.insert("tareas", null, valores);
        db.close();
    }

    // Método para obtener todas las tareas
    public List<Homework> obtenerTareas() {
        List<Homework> tareas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("tareas", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Homework tarea = new Homework(
                        cursor.getString(cursor.getColumnIndexOrThrow("subject")),
                        cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        cursor.getString(cursor.getColumnIndexOrThrow("dueDate")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("isCompleted")) == 1
                );
                tarea.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                tareas.add(tarea);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return tareas;
    }

    // Método para actualizar una tarea
    public void actualizarTarea(Homework homework) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("subject", homework.getSubject());
        valores.put("description", homework.getDescription());
        valores.put("dueDate", homework.getDueDate());
        valores.put("isCompleted", homework.isCompleted() ? 1 : 0);
        db.update("tareas", valores, "id = ?", new String[]{String.valueOf(homework.getId())});
        db.close();
    }

    // Método para eliminar una tarea
    public void eliminarTarea(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tareas", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}

