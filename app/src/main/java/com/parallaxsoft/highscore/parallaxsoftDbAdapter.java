package com.parallaxsoft.highscore;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class parallaxsoftDbAdapter {
	
	protected parallaxsoftDBManager parallaxsoftmDbHelper;
	protected SQLiteDatabase mDb;
    protected Context mCtx;

    protected parallaxsoftDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public parallaxsoftDbAdapter open() throws SQLException {
        parallaxsoftmDbHelper = new parallaxsoftDBManager(mCtx);
        mDb = parallaxsoftmDbHelper.getWritableDatabase();
        if (!mDb.isReadOnly()) {
        	mDb.execSQL("PRAGMA foreign_keys=ON;");
        }
        return this;
    }

    public void close() {
        parallaxsoftmDbHelper.close();
    }
    public void toastMessage(int msg) {
		Toast.makeText(this.mCtx, msg, Toast.LENGTH_SHORT).show();
    }
}