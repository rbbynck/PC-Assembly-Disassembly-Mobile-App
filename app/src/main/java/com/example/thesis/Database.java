package com.example.thesis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    String username;

    public static final String DATABASE_NAME = "pc_assembly";

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public Cursor viewLessonStatus() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT lesson_status FROM lesson";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public String viewLessonStatus(int lesson) {
        String status = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT lesson_status FROM Lesson WHERE lesson_no = " + lesson;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            status = cursor.getString(0);
        }
        return status;
    }

    public Cursor viewLesson1ModuleStatus(int lesson) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT module_status FROM Module WHERE lesson_no = " + lesson;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public String viewModuleStatus(String module) {
        String status = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT module_status FROM Module WHERE module_id= '" + module + "'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            status = cursor.getString(0);
        }
        return status;
    }

    public String viewLessonProgress(int lesson) {
        String status = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT lesson_progress FROM Lesson WHERE lesson_no = " + lesson;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            status = cursor.getString(0);
        }
        return status;
    }

    public String viewSimulationStatus(String type) {
        String status = "";

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT status FROM Simulation WHERE type = '" + type + "'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            status = cursor.getString(0);
        }
        return status;
    }

    public void updateLessonStatus(int lessonNo, String status) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("lesson_status", status);
        db.update("Lesson", contentValues, "lesson_no=" + lessonNo, null);
        db.close();
    }

    public void updateModuleStatus(String id, String update) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("module_status", update);
        db.update("Module", contentValues, "module_id = '" + id + "'", null);
        db.close();
    }

    public void updateSimulationStatus(String type, String status) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", status);
        db.update("Simulation", contentValues, "type='" + type + "'", null);
    }

    public void updateSimulationTimeStatus(String status) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", status);
        db.update("Simulation_Time", contentValues, "Name='Time'", null);
    }

    public void updateSimulationTime(String status, int minutes, int seconds) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Status", status);
        contentValues.put("Minutes", minutes);
        contentValues.put("Seconds", seconds);
        db.update("Simulation_Time", contentValues, "Name='Time'", null);
    }

    public int answer(int lesson, int module, int question_no, int try_no) {
        int correct_answer = 0;

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT correct_answer FROM Quiz_Questions WHERE lesson_no = " + lesson + " AND module_no = " + module + " AND question_no = " + question_no + " AND try_no = " + try_no;
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            correct_answer = cursor.getInt(0);
        }
        return correct_answer;
    }

    public int numberOfQuestions(int lesson, int module, int try_no) {
        int number_of_question = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT question_no FROM Quiz_Questions WHERE lesson_no = " + lesson + " AND module_no = " + module + " AND try_no = " + try_no;
        Cursor cursor = db.rawQuery(query, null);
        number_of_question = cursor.getCount();
        return number_of_question;
    }

    public String viewQuestion(int lesson, int module, int question_no, int try_no) {
        String question = "";
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT question FROM Quiz_Questions WHERE lesson_no = " + lesson + " AND module_no = " + module + " AND question_no = " + question_no + " AND try_no = " + try_no;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            question = cursor.getString(0);
        }
        return question;
    }

    public int numberOfOptions(int lesson, int module, int question_no, int try_no) {
        int number_of_option = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT option_no FROM Quiz_Questions WHERE lesson_no = " + lesson + " AND module_no = " + module + " AND question_no = " + question_no + " AND try_no = " + try_no;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            number_of_option = cursor.getInt(0);
        }
        return number_of_option;
    }

    public String viewOption(int lesson, int module, int question_no, int option_no, int try_no) {
        String option = "";
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT option FROM Quiz_Options WHERE lesson_no = " + lesson + " AND module_no = " + module + " AND question_no = " + question_no + " AND option_no = " + option_no + " AND try_no = " + try_no;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            option = cursor.getString(0);
        }
        return option;
    }

    public void insertQuizLog(int lesson, int module, String status) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("lesson", lesson);
        contentValues.put("module", module);
        contentValues.put("status", status);
        db.insert("Quiz_Log", null, contentValues);
        db.close();
    }

    public String viewQuizStatusTries(int lesson, int module) {
        String status = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT status FROM Quiz_Log WHERE lesson = " + lesson + " AND module = " + module;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        status = String.valueOf(count);

        return status;
    }

    public String viewQuizStatusPass(int lesson, int module) {
        String status = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT status FROM Quiz_Log WHERE lesson = " + lesson + " AND module = " + module + " AND status = 'Passed'";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        status = String.valueOf(count);
        return status;
    }

    public String viewQuizStatusFailed(int lesson, int module) {
        String status = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT status FROM Quiz_Log WHERE lesson = " + lesson + " AND module = " + module + " AND status = 'Failed'";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        status = String.valueOf(count);
        return status;
    }

    public int viewNoOfModule(int lesson) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT lesson_no FROM Module WHERE lesson_no = " + lesson;
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }

    public void updateLessonProgress(int lesson, int progress) {
        String string = String.valueOf(progress);
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("lesson_progress", string);
        db.update("Lesson", contentValues, "lesson_no = " + lesson, null);
        db.close();
    }

    public void updateAppProgress(int progress) {
        String string = String.valueOf(progress);
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("progress_student", string);
        db.update("User", contentValues, "", null);
        db.close();
    }

    public int getModulesStatus() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT module_status FROM Module WHERE module_status = 'Done'";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        return count;
    }

    public String viewAppProgress() {
        String status = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT progress_student FROM User";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            status = cursor.getString(0);
        }
        return status;
    }

    public void updateFastest(int minutes, int seconds) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("minutes", String.valueOf(minutes));
        contentValues.put("seconds", String.valueOf(seconds));
        db.update("Simulation_Time", contentValues, "type = 'Highest'", null);
        db.close();
    }

    public void updateSlowest(int minutes, int seconds) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("minutes", String.valueOf(minutes));
        contentValues.put("seconds", String.valueOf(seconds));
        db.update("Simulation_Time", contentValues, "type = 'Lowest'", null);
        db.close();
    }

    public int getSlowestMinutes() {
        int minutes = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT minutes FROM Simulation_Time WHERE type = 'Slowest'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            minutes = cursor.getInt(0);
        }
        return minutes;
    }

    public int getSlowestSeconds() {
        int seconds = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT seconds FROM Simulation_Time WHERE type = 'Slowest'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            seconds = cursor.getInt(0);
        }
        return seconds;
    }

    public int getFastestMinutes() {
        int minutes = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT minutes FROM Simulation_Time WHERE type = 'Fastest'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            minutes = cursor.getInt(0);
        }
        return minutes;
    }

    public int getFastestSeconds() {
        int seconds = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT seconds FROM Simulation_Time WHERE type = 'Fastest'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            seconds = cursor.getInt(0);
        }
        return seconds;
    }


    //LOGIN DATABASE
    public void login(int id, String username, String email, String name, int class_student, String status, int progress) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_student", id);
        contentValues.put("username_student", username);
        contentValues.put("email_student", email);
        contentValues.put("name_student", name);
        contentValues.put("class_student", class_student);
        contentValues.put("status_student", status);
        contentValues.put("progress_student", progress);
        db.insert("User", null, contentValues);
        db.close();
    }

    public void login_Lesson(String lesson_status_1, int lesson_progress_1, String lesson_status_2, int lesson_progress_2,
                             String lesson_status_3, int lesson_progress_3, String lesson_status_4, int lesson_progress_4,
                             String lesson_status_5, int lesson_progress_5, String lesson_status_6, int lesson_progress_6,
                             String lesson_status_7, int lesson_progress_7, String lesson_status_8, int lesson_progress_8) {

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        //lesson 1
        contentValues.put("lesson_no", 1);
        contentValues.put("lesson_status", lesson_status_1);
        contentValues.put("lesson_progress", lesson_progress_1);
        db.insert("Lesson", null, contentValues);
        //lesson 2
        contentValues.put("lesson_no", 2);
        contentValues.put("lesson_status", lesson_status_2);
        contentValues.put("lesson_progress", lesson_progress_2);
        db.insert("Lesson", null, contentValues);
        //lesson 3
        contentValues.put("lesson_no", 3);
        contentValues.put("lesson_status", lesson_status_3);
        contentValues.put("lesson_progress", lesson_progress_3);
        db.insert("Lesson", null, contentValues);
        //lesson 4
        contentValues.put("lesson_no", 4);
        contentValues.put("lesson_status", lesson_status_4);
        contentValues.put("lesson_progress", lesson_progress_4);
        db.insert("Lesson", null, contentValues);
        //lesson 5
        contentValues.put("lesson_no", 5);
        contentValues.put("lesson_status", lesson_status_5);
        contentValues.put("lesson_progress", lesson_progress_5);
        db.insert("Lesson", null, contentValues);
        //lesson 6
        contentValues.put("lesson_no", 6);
        contentValues.put("lesson_status", lesson_status_6);
        contentValues.put("lesson_progress", lesson_progress_6);
        db.insert("Lesson", null, contentValues);
        //lesson 7
        contentValues.put("lesson_no", 7);
        contentValues.put("lesson_status", lesson_status_7);
        contentValues.put("lesson_progress", lesson_progress_7);
        db.insert("Lesson", null, contentValues);
        //lesson 8
        contentValues.put("lesson_no", 8);
        contentValues.put("lesson_status", lesson_status_8);
        contentValues.put("lesson_progress", lesson_progress_8);
        db.insert("Lesson", null, contentValues);

        db.close();
    }

    public void login_Simulation_Time(int lowest_minutes, int lowest_seconds, int highest_minutes, int highest_seconds) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", "Highest");
        contentValues.put("minutes", highest_minutes);
        contentValues.put("seconds", highest_seconds);
        db.insert("Simulation_Time", null, contentValues);

        contentValues.put("type", "Lowest");
        contentValues.put("minutes", lowest_minutes);
        contentValues.put("seconds", lowest_seconds);
        db.insert("Simulation_Time", null, contentValues);

        db.close();
    }

    public void login_Simulation(String current_status) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        if (current_status.matches("None")) {
            contentValues.put("type", "Tutorial");
            contentValues.put("status", "Not_Active");
            db.insert("Simulation", null, contentValues);
            contentValues.put("type", "Practice");
            contentValues.put("status", "Not_Active");
            db.insert("Simulation", null, contentValues);
            contentValues.put("type", "Time");
            contentValues.put("status", "Not_Active");
            db.insert("Simulation", null, contentValues);
        } else if (current_status.matches("Tutorial")) {
            contentValues.put("type", "Tutorial");
            contentValues.put("status", "Active");
            db.insert("Simulation", null, contentValues);
            contentValues.put("type", "Practice");
            contentValues.put("status", "Not_Active");
            db.insert("Simulation", null, contentValues);
            contentValues.put("type", "Time");
            contentValues.put("status", "Not_Active");
            db.insert("Simulation", null, contentValues);
        } else if (current_status.matches("Practice") || current_status.matches("Time")) {
            contentValues.put("type", "Tutorial");
            contentValues.put("status", "Done");
            db.insert("Simulation", null, contentValues);
            contentValues.put("type", "Practice");
            contentValues.put("status", "Active");
            db.insert("Simulation", null, contentValues);
            contentValues.put("type", "Time");
            contentValues.put("status", "Active");
            db.insert("Simulation", null, contentValues);
        }
    }

    public void login_Module(int current_module_no_1, int current_module_no_2, int current_module_no_3, int current_module_no_4,
                             int current_module_no_5, int current_module_no_6, int current_module_no_7, int current_module_no_8,
                             int progress_1, int progress_2, int progress_3, int progress_4, int progress_5, int progress_6, int progress_7, int progress_8) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        //lesson 1
        for (int i = 0; i < 5; i++) {
            if ((i + 1) == current_module_no_1) {
                contentValues.put("module_status", "Active");
                db.update("Module", contentValues, "module_id = '" + ((1) + "_" + (i + 1)) + "'", null);
                if ((i + 1) == 5) {
                    if (progress_1 > 97) {
                        contentValues.put("module_status", "Done");
                        db.update("Module", contentValues, "module_id = '" + ((1) + "_" + (i + 1)) + "'", null);
                    }
                }
            } else if ((i + 1) < current_module_no_1) {
                contentValues.put("module_status", "Done");
                db.update("Module", contentValues, "module_id = '" + ((1) + "_" + (i + 1)) + "'", null);
            } else if ((i + 1) > current_module_no_1) {
                contentValues.put("module_status", "Not_Active");
                db.update("Module", contentValues, "module_id = '" + ((1) + "_" + (i + 1)) + "'", null);
            }
        }
        //lesson 2
        for (int i = 0; i < 3; i++) {
            if ((i + 1) == current_module_no_2) {
                contentValues.put("module_status", "Active");
                db.update("Module", contentValues, "module_id = '" + ((2) + "_" + (i + 1)) + "'", null);
                if ((i + 1) == 3) {
                    if (progress_2 > 97) {
                        contentValues.put("module_status", "Done");
                        db.update("Module", contentValues, "module_id = '" + ((2) + "_" + (i + 1)) + "'", null);
                    }
                }
            } else if ((i + 1) < current_module_no_2) {
                contentValues.put("module_status", "Done");
                db.update("Module", contentValues, "module_id = '" + ((2) + "_" + (i + 1)) + "'", null);
            } else if ((i + 1) > current_module_no_2) {
                contentValues.put("module_status", "Not_Active");
                db.update("Module", contentValues, "module_id = '" + ((2) + "_" + (i + 1)) + "'", null);
            }
        }
        //lesson 3
        for (int i = 0; i < 4; i++) {
            if ((i + 1) == current_module_no_3) {
                contentValues.put("module_status", "Active");
                db.update("Module", contentValues, "module_id = '" + ((3) + "_" + (i + 1)) + "'", null);
                if ((i + 1) == 4) {
                    if (progress_3 > 97) {
                        contentValues.put("module_status", "Done");
                        db.update("Module", contentValues, "module_id = '" + ((3) + "_" + (i + 1)) + "'", null);
                    }
                }
            } else if ((i + 1) < current_module_no_3) {
                contentValues.put("module_status", "Done");
                db.update("Module", contentValues, "module_id = '" + ((3) + "_" + (i + 1)) + "'", null);
            } else if ((i + 1) > current_module_no_3) {
                contentValues.put("module_status", "Not_Active");
                db.update("Module", contentValues, "module_id = '" + ((3) + "_" + (i + 1)) + "'", null);
            }
        }
        //lesson 4
        for (int i = 0; i < 6; i++) {
            if ((i + 1) == current_module_no_4) {
                contentValues.put("module_status", "Active");
                db.update("Module", contentValues, "module_id = '" + ((4) + "_" + (i + 1)) + "'", null);
                if ((i + 1) == 6) {
                    if (progress_4 > 97) {
                        contentValues.put("module_status", "Done");
                        db.update("Module", contentValues, "module_id = '" + ((4) + "_" + (i + 1)) + "'", null);
                    }
                }
            } else if ((i + 1) < current_module_no_4) {
                contentValues.put("module_status", "Done");
                db.update("Module", contentValues, "module_id = '" + ((4) + "_" + (i + 1)) + "'", null);
            } else if ((i + 1) > current_module_no_4) {
                contentValues.put("module_status", "Not_Active");
                db.update("Module", contentValues, "module_id = '" + ((4) + "_" + (i + 1)) + "'", null);
            }
        }
        //lesson 5
        for (int i = 0; i < 3; i++) {
            if ((i + 1) == current_module_no_5) {
                contentValues.put("module_status", "Active");
                db.update("Module", contentValues, "module_id = '" + ((5) + "_" + (i + 1)) + "'", null);
                if ((i + 1) == 3) {
                    if (progress_5 > 97) {
                        contentValues.put("module_status", "Done");
                        db.update("Module", contentValues, "module_id = '" + ((5) + "_" + (i + 1)) + "'", null);
                    }
                }
            } else if ((i + 1) < current_module_no_5) {
                contentValues.put("module_status", "Done");
                db.update("Module", contentValues, "module_id = '" + ((5) + "_" + (i + 1)) + "'", null);
            } else if ((i + 1) > current_module_no_5) {
                contentValues.put("module_status", "Not_Active");
                db.update("Module", contentValues, "module_id = '" + ((5) + "_" + (i + 1)) + "'", null);
            }
        }
        //lesson 6
        for (int i = 0; i < 3; i++) {
            if ((i + 1) == current_module_no_6) {
                contentValues.put("module_status", "Active");
                db.update("Module", contentValues, "module_id = '" + ((6) + "_" + (i + 1)) + "'", null);
                if ((i + 1) == 3) {
                    if (progress_6 > 97) {
                        contentValues.put("module_status", "Done");
                        db.update("Module", contentValues, "module_id = '" + ((6) + "_" + (i + 1)) + "'", null);
                    }
                }
            } else if ((i + 1) < current_module_no_6) {
                contentValues.put("module_status", "Done");
                db.update("Module", contentValues, "module_id = '" + ((6) + "_" + (i + 1)) + "'", null);
            } else if ((i + 1) > current_module_no_6) {
                contentValues.put("module_status", "Not_Active");
                db.update("Module", contentValues, "module_id = '" + ((6) + "_" + (i + 1)) + "'", null);
            }
        }
        //lesson 7
        for (int i = 0; i < 2; i++) {
            if ((i + 1) == current_module_no_7) {
                contentValues.put("module_status", "Active");
                db.update("Module", contentValues, "module_id = '" + ((7) + "_" + (i + 1)) + "'", null);
                if ((i + 1) == 2) {
                    if (progress_7 > 97) {
                        contentValues.put("module_status", "Done");
                        db.update("Module", contentValues, "module_id = '" + ((7) + "_" + (i + 1)) + "'", null);
                    }
                }
            } else if ((i + 1) < current_module_no_7) {
                contentValues.put("module_status", "Done");
                db.update("Module", contentValues, "module_id = '" + ((7) + "_" + (i + 1)) + "'", null);
            } else if ((i + 1) > current_module_no_7) {
                contentValues.put("module_status", "Not_Active");
                db.update("Module", contentValues, "module_id = '" + ((7) + "_" + (i + 1)) + "'", null);
            }
        }
        //lesson 8
        for (int i = 0; i < 2; i++) {
            if ((i + 1) == current_module_no_8) {
                contentValues.put("module_status", "Active");
                db.update("Module", contentValues, "module_id = '" + ((8) + "_" + (i + 1)) + "'", null);
                if ((i + 1) == 2) {
                    if (progress_8 > 97) {
                        contentValues.put("module_status", "Done");
                        db.update("Module", contentValues, "module_id = '" + ((8) + "_" + (i + 1)) + "'", null);
                    }
                }
            } else if ((i + 1) < current_module_no_8) {
                contentValues.put("module_status", "Done");
                db.update("Module", contentValues, "module_id = '" + ((8) + "_" + (i + 1)) + "'", null);
            } else if ((i + 1) > current_module_no_8) {
                contentValues.put("module_status", "Not_Active");
                db.update("Module", contentValues, "module_id = '" + ((8) + "_" + (i + 1)) + "'", null);
            }
        }

        db.close();
    }

    public void login_QuizLog(int lesson, int module, String status) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("lesson", lesson);
        contentValues.put("module", module);
        contentValues.put("status", status);
        db.insert("Quiz_Log", null, contentValues);
        db.close();
    }


    //REGISTER DATABASE
    public void register(int id, String username, String email, String name, int class_) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_student", id);
        contentValues.put("username_student", username);
        contentValues.put("email_student", email);
        contentValues.put("name_student", name);
        contentValues.put("class_student", class_);
        contentValues.put("status_student", "Lesson 1");
        contentValues.put("progress_student", 0);
        db.insert("User", null, contentValues);
        db.close();
    }

    public void register_Lesson() {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("lesson_no", 1);
        contentValues.put("lesson_status", "Active");
        contentValues.put("lesson_progress", 0);
        db.insert("Lesson", null, contentValues);

        for (int i = 1; i < 8; i++) {
            contentValues.put("lesson_no", (i + 1));
            contentValues.put("lesson_status", "Not_Active");
            contentValues.put("lesson_progress", 0);
            db.insert("Lesson", null, contentValues);
        }

        db.close();
    }

    public void register_Module() {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("module_status", "Active");
        db.update("Module", contentValues, "module_id = '1_1'", null);
    }

    public void register_Simulation() {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", "Tutorial");
        contentValues.put("status", "Not_Active");
        db.insert("Simulation", null, contentValues);
        contentValues.put("type", "Practice");
        contentValues.put("status", "Not_Active");
        db.insert("Simulation", null, contentValues);
        contentValues.put("type", "Time");
        contentValues.put("status", "Not_Active");
        db.insert("Simulation", null, contentValues);
        db.close();
    }

    public void register_Simulation_Time() {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", "Highest");
        contentValues.put("minutes", 0);
        contentValues.put("seconds", 0);
        db.insert("Simulation_Time", null, contentValues);

        contentValues.put("type", "Lowest");
        contentValues.put("minutes", 0);
        contentValues.put("seconds", 0);
        db.insert("Simulation_Time", null, contentValues);

        db.close();
    }

    public int getClass_No() {
        int class_no = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT class_student FROM User";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            class_no = cursor.getInt(0);
        }
        return class_no;
    }

    public String get_LessonStatus(int lesson_no) {
        String status = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT lesson_status FROM Lesson WHERE lesson_no = " + lesson_no;

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            status = cursor.getString(0);
        }

        return status;
    }

    public int get_LessonProgress(int lesson_no) {
        int progress = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT lesson_progress FROM Lesson WHERE lesson_no = " + lesson_no;

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            progress = cursor.getInt(0);
        }

        return progress;
    }

    public String get_Status() {
        String status = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT status_student FROM User";

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            status = cursor.getString(0);
        }

        return status;
    }

    public int get_Progress() {
        int status = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT progress_student FROM User";

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            status = cursor.getInt(0);
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("progress_student", status);
        db.update("User", contentValues, "", null);

        return status;
    }

    public String get_ID() {
        String id = "";

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT id_student FROM User";

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            id = String.valueOf(cursor.getInt(0));
        }

        return id;
    }

    public void updateStatus(String status) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status_student", status);
        db.update("User", contentValues, "", null);
    }

    public String getSound() {
        String string = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT Sound FROM Sound";
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            string = cursor.getString(0);
        }
        return string;
    }

    public void updateSound(String status) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Sound", status);
        db.update("Sound", contentValues, "", null);
    }

    public String getTrivia() {
        String string = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT trivia FROM Trivia";
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            string = cursor.getString(0);
        }
        return string;
    }

    public void updateTrivia(String status) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trivia", status);
        db.update("Trivia", contentValues, "", null);
    }

    public String[][] quizLog() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Quiz_Log";
        Cursor cursor = db.rawQuery(query, null);

        String[][] array = new String[cursor.getCount()][3];
        int i = 0;
        while (cursor.moveToNext()) {
            array[i][0] = cursor.getString(0);
            array[i][1] = cursor.getString(1);
            array[i][2] = cursor.getString(2);
            i++;
        }

        return array;
    }

    public String[] getSimulation() {
        String[] array = new String[3];

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT type FROM Simulation WHERE type = 'Tutorial'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            array[0] = cursor.getString(0);
        }

        String query_1 = "SELECT type FROM Simulation WHERE type = 'Practice'";
        Cursor cursor_1 = db.rawQuery(query_1, null);
        while (cursor_1.moveToNext()) {
            array[1] = cursor_1.getString(0);
        }

        String query_2 = "SELECT type FROM Simulation WHERE type = 'Time'";
        Cursor cursor_2 = db.rawQuery(query_2, null);
        while (cursor_2.moveToNext()) {
            array[2] = cursor_2.getString(0);
        }

        return array;
    }

    public boolean isTableExists() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select DISTINCT tbl_name from sqlite_master where tbl_name = 'User'";
        try (Cursor cursor = db.rawQuery(query, null)) {
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    return true;
                }
            }
            return false;
        }
    }

    public String getName() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT name_student FROM User";
        Cursor cursor = db.rawQuery(query, null);

        String name = "";
        while (cursor.moveToNext()) {
            name = cursor.getString(0);
        }

        return name;
    }

    public String getEmail() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT email_student FROM User";
        Cursor cursor = db.rawQuery(query, null);

        String name = "";
        while (cursor.moveToNext()) {
            name = cursor.getString(0);
        }

        return name;
    }

    public String getUsername() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT username_student FROM User";
        Cursor cursor = db.rawQuery(query, null);

        String name = "";
        while (cursor.moveToNext()) {
            name = cursor.getString(0);
        }

        return name;
    }

    public void updateUser(String name, String username, String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username_student", username);
        contentValues.put("email_student", email);
        contentValues.put("name_student", name);
        db.update("User", contentValues, "", null);
        db.close();
    }

    public int getRating() {
        int rating = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT rating FROM Feedback";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            rating = cursor.getInt(0);
        }
        return rating;
    }

    public void insertFeedback(int rating, String feedback) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("feedback", feedback);
        contentValues.put("rating", rating);
        db.insert("Feedback", null, contentValues);
        db.close();
    }

    public void insertCertificate(String certificate) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("certificate", certificate);
        db.insert("Certificate", null, contentValues);
        db.close();
    }

    public boolean checkIfEmpty() {
        boolean check = true;
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM USER";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {
            check = true;
        } else {
            check = false;
        }

        return check;
    }

    public void setClass_No(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("class_student", id);
        db.update("User", contentValues, "", null);
        db.close();
    }
}


