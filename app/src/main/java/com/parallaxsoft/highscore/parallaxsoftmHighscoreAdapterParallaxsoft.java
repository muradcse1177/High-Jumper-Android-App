package com.parallaxsoft.highscore;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

public class parallaxsoftmHighscoreAdapterParallaxsoft extends parallaxsoftDbAdapter {
	private static final String parallaxsoftmDATABASE_TABLE = "rh_highscore";
	public static final String parallaxsoftmKEY_ROWID = "_id";
	public static final String parallaxsoftmKEY_NAME = "name";
	public static final String parallaxsoftmKEY_SCORE = "score";
	public static final String parallaxsoftmKEY_ISONLINE = "isonline";

	public parallaxsoftmHighscoreAdapterParallaxsoft(Context ctx) {
		super(ctx);
		this.mCtx = ctx;
	}

	public long parallaxsoftmcreateHighscore(String score, String name, int isonline) {

		ContentValues parallaxsoftminitialValues = new ContentValues();
		parallaxsoftminitialValues.put(parallaxsoftmKEY_NAME, name);
		parallaxsoftminitialValues.put(parallaxsoftmKEY_SCORE, score);
		parallaxsoftminitialValues.put(parallaxsoftmKEY_ISONLINE, isonline);

		return mDb.insert(parallaxsoftmDATABASE_TABLE, null, parallaxsoftminitialValues);
	}

	public boolean parallaxsoftupdateScore(long rowId, int parallaxsoftisOnline) {
		ContentValues args = new ContentValues();
		args.put(parallaxsoftmKEY_ROWID, rowId);
		args.put(parallaxsoftmKEY_ISONLINE, parallaxsoftisOnline);

		return mDb.update(parallaxsoftmDATABASE_TABLE, args, parallaxsoftmKEY_ROWID + "=" + rowId, null) > 0;
	}

	public boolean delete(long rowId) {
		return mDb.delete(parallaxsoftmDATABASE_TABLE, parallaxsoftmKEY_ROWID + "=" + rowId, null) > 0;
	}

	public boolean clear() {
		return mDb.delete(parallaxsoftmDATABASE_TABLE, null, null) > 0;
	}

	public Cursor parallaxsoftfetchScores(String limit) throws SQLException {

		Cursor parallaxsoftmCursor;
		if (limit == "0")
		{
			parallaxsoftmCursor = mDb.query(parallaxsoftmDATABASE_TABLE, new String[] { parallaxsoftmKEY_ROWID,
					parallaxsoftmKEY_NAME, parallaxsoftmKEY_SCORE, parallaxsoftmKEY_ISONLINE }, null, null, null, null,
					parallaxsoftmKEY_SCORE + " DESC", null);
		}
		else
		{
			parallaxsoftmCursor = mDb.query(parallaxsoftmDATABASE_TABLE, new String[] { parallaxsoftmKEY_ROWID,
					parallaxsoftmKEY_NAME, parallaxsoftmKEY_SCORE, parallaxsoftmKEY_ISONLINE }, null, null, null, null,
					parallaxsoftmKEY_SCORE + " DESC", limit);
		}
		
		if (parallaxsoftmCursor != null) {
			parallaxsoftmCursor.moveToFirst();
		}
		
		return parallaxsoftmCursor;
	}
	public Cursor parallaxsoftfetchSingleScore(long id) throws SQLException {

		Cursor parallaxsoftmCursor = mDb.query(true, parallaxsoftmDATABASE_TABLE, new String[] {
				parallaxsoftmKEY_ROWID, parallaxsoftmKEY_NAME, parallaxsoftmKEY_SCORE, parallaxsoftmKEY_ISONLINE }, parallaxsoftmKEY_ROWID + "="
				+ id, null, null, null, null, null);

		if (parallaxsoftmCursor != null) {
			parallaxsoftmCursor.moveToFirst();
		}

		return parallaxsoftmCursor;
	}
	
	public Cursor parallaxsoftgetHighscore(long place)
	{
		
		Cursor parallaxsoftmCursor = mDb.query(parallaxsoftmDATABASE_TABLE, new String[] { parallaxsoftmKEY_ROWID,
				parallaxsoftmKEY_NAME, parallaxsoftmKEY_SCORE, parallaxsoftmKEY_ISONLINE }, null, null, null, null,
				parallaxsoftmKEY_SCORE + " DESC", ""+place);

		if (parallaxsoftmCursor != null) {
			parallaxsoftmCursor.moveToLast();
		}

		return parallaxsoftmCursor;
	}

	public Cursor parallaxsoftfetchLastEntry() throws SQLException {
		Cursor parallaxsoftmCursor = mDb.query(parallaxsoftmDATABASE_TABLE, new String[] { parallaxsoftmKEY_ROWID,
				parallaxsoftmKEY_NAME, parallaxsoftmKEY_SCORE, parallaxsoftmKEY_ISONLINE }, "isonline = 0", null,
				null, null, parallaxsoftmKEY_ROWID + " DESC", "1");

		if (parallaxsoftmCursor != null) {
			parallaxsoftmCursor.moveToFirst();
		}

		return parallaxsoftmCursor;
	}
}
