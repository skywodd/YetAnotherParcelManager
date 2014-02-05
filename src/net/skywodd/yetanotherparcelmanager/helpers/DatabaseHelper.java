package net.skywodd.yetanotherparcelmanager.helpers;

import java.sql.SQLException;

import net.skywodd.yetanotherparcelmanager.R;
import net.skywodd.yetanotherparcelmanager.models.Parcel;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * SQlite database helper for parcels managing using ORMlite.
 * 
 * @author skywodd
 * @version 1.0.0
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	/** Database file name */
	private static final String DATABASE_NAME = "parcels.db";

	/** Database version number */
	private static final int DATABASE_VERSION = 1;

	// the DAO object we use to access the Parcel table
	private Dao<Parcel, Integer> parcelDao = null;
	private RuntimeExceptionDao<Parcel, Integer> parcelRuntimeDao = null;

	/**
	 * Database helper constructor.
	 * 
	 * @param context
	 *            Parent context.
	 */
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION,
				R.raw.ormlite_config);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper#onCreate(android
	 * .database.sqlite.SQLiteDatabase,
	 * com.j256.ormlite.support.ConnectionSource)
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Parcel.class);
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot create the database", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper#onUpgrade(android
	 * .database.sqlite.SQLiteDatabase,
	 * com.j256.ormlite.support.ConnectionSource, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, Parcel.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot upgrade the database", e);
		}
	}

	/**
	 * Returns the Database Access Object (DAO) for our Parcel class. It will
	 * create it or just give the cached value.
	 */
	public Dao<Parcel, Integer> getDao() throws SQLException {
		if (parcelDao == null) {
			parcelDao = getDao(Parcel.class);
		}
		return parcelDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao
	 * for our Parcel class. It will create it or just give the cached value.
	 * RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<Parcel, Integer> getParcelDao() {
		if (parcelRuntimeDao == null) {
			parcelRuntimeDao = getRuntimeExceptionDao(Parcel.class);
		}
		return parcelRuntimeDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper#close()
	 */
	@Override
	public void close() {
		super.close();
		parcelDao = null;
		parcelRuntimeDao = null;
	}
}
