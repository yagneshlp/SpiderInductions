package com.yagneshlp.spiderinductions.tasks.Task_4.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yagneshlp.spiderinductions.pojo.pojo_favourites.FavouriteArtist;
import com.yagneshlp.spiderinductions.pojo.pojo_favourites.FavouriteTrack;


public class FavouritesDBHelper extends SQLiteOpenHelper {

    private static final String TAG = FavouritesDBHelper.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "favourites";

    // Active Tasks table name
    private static final String TABLE_tracks = "tracks";
    private static final String TABLE_artists = "artists";

    // Login Table Columns names
    private static final String KEY_track_id = "id";
    private static final String KEY_track_name = "name";
    private static final String KEY_track_artists_name = "artists_name";
    private static final String KEY_track_album_name = "album_name";
    private static final String KEY_track_genre = "genre";
    private static final String KEY_track_year = "year";

    private static final String KEY_artist_id = "id";
    private static final String KEY_artist_name = "name";
    private static final String KEY_artist_genre = "genre";
    private static final String KEY_artist_rating = "rating";

    private int noOfTracks=0,noOfArtists=0;




    public FavouritesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_tracks_TABLE = "CREATE TABLE " + TABLE_tracks + "("
                +KEY_track_id + " INTEGER PRIMARY KEY ," + KEY_track_name + " TEXT," + KEY_track_artists_name + " TEXT," + KEY_track_album_name +" TEXT," + KEY_track_genre + " TEXT," + KEY_track_year + " INTEGER"
                + ")";
        String CREATE_artists_TABLE ="CREATE TABLE " + TABLE_artists + "("
                +KEY_artist_id + " INTEGER PRIMARY KEY ," + KEY_artist_name + " TEXT," + KEY_artist_genre + " TEXT," + KEY_artist_rating +" INTEGER"
                + ")";

        db.execSQL(CREATE_tracks_TABLE);
        Log.d(TAG, "Favourite Tracks table created");
        db.execSQL(CREATE_artists_TABLE);
        Log.d(TAG, "Favourite Artists Table created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_tracks);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_artists);
        // Create tables again
        onCreate(db);
    }


    public void addTrack(FavouriteTrack track) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_track_id, track.getTrack_id()); // Name
        values.put(KEY_track_name, track.getTrack_name()); // Email
        values.put(KEY_track_artists_name, track.getArtist_name()); // Email
        values.put(KEY_track_album_name, track.getAlbum_name()); // Email
        values.put(KEY_track_genre, track.getGenre()); // Email
        values.put(KEY_track_year, track.getYear()); // Email
        // Inserting Row
        db.insert(TABLE_tracks, null, values);
        db.close(); // Closing database connection
        Log.d(TAG, "New Track has been Added into the table");
    }

    public void addArtist(FavouriteArtist artist) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_artist_id,artist.getArtist_id()); // Name
        values.put(KEY_artist_name, artist.getArtist_name()); // Email
        values.put(KEY_artist_genre, artist.getGenre()); // Email
        values.put(KEY_artist_rating, artist.getRating()); // Email

        // Inserting Row
        db.insert(TABLE_artists, null, values);
        db.close(); // Closing database connection
        Log.d(TAG, "New Artist has been Added into the table");
    }

    public void deleteTrack(FavouriteTrack track) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_tracks, KEY_track_id + "= '"+track.getTrack_id()+"'", null);
        db.close();
        Log.d(TAG, "Track " + track.getTrack_name() +" has been Deleted");
    }

    public void deleteArtist(FavouriteArtist artist) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_artists, KEY_artist_id + "= '"+artist.getArtist_id()+"'", null);
        db.close();
        Log.d(TAG, "Artist " + artist.getArtist_name() +" has been Deleted");
    }

    public boolean searchTrack(long trackID){
        boolean isTrackIDFavourite = false;
        String selectQuery = "SELECT  * FROM " + TABLE_tracks;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "Fetching tracks from Sqlite: ");
        Log.d(TAG, "No of tracks fetched: " + cursor.getCount());
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            for(int i=0;i<cursor.getCount();i++){
                if(cursor.getInt(0) == trackID){
                        isTrackIDFavourite = true;
                        break;
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return isTrackIDFavourite;
    }

    public boolean searchArtist(long artistID){
        boolean isArtistIDFavourite = false;
        String selectQuery = "SELECT  * FROM " + TABLE_artists;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "Fetching artists from Sqlite: ");
        Log.d(TAG, "No of artists fetched: " + cursor.getCount());
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            for(int i=0;i<cursor.getCount();i++){
                if(cursor.getInt(0) == artistID){
                    isArtistIDFavourite = true;
                    break;
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return isArtistIDFavourite;
    }

    public FavouriteTrack getTrackByID(long trackID){
        FavouriteTrack track = new FavouriteTrack();
        String selectQuery = "SELECT  * FROM " + TABLE_tracks;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "Fetching tracks from Sqlite: ");
        Log.d(TAG, "No of tracks fetched: " + cursor.getCount());
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            for(int i=0;i<cursor.getCount();i++){
                if(cursor.getLong(0) == trackID){
                    track.setTrack_id(cursor.getLong(0));
                    track.setTrack_name(cursor.getString(1));
                    track.setArtist_name(cursor.getString(2));
                    track.setAlbum_name(cursor.getString(3));
                    track.setGenre(cursor.getString(4));
                    track.setYear(cursor.getInt(5));
                    break;
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return track;

    }

    public FavouriteArtist getArtistByID(long artistID){
        FavouriteArtist artist = new FavouriteArtist();
        String selectQuery = "SELECT  * FROM " + TABLE_artists;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "Fetching artists from Sqlite: ");
        Log.d(TAG, "No of artists fetched: " + cursor.getCount());
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            for(int i=0;i<cursor.getCount();i++){
                if(cursor.getLong(0) == artistID){
                    artist.setArtist_id(cursor.getLong(0));
                    artist.setArtist_name(cursor.getString(1));
                    artist.setGenre(cursor.getString(2));
                    artist.setRating(cursor.getInt(3));
                    break;
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return artist;
    }

    public FavouriteTrack[] getAllTracks(){
        FavouriteTrack[] tracklist = new FavouriteTrack[1000];
        String selectQuery = "SELECT  * FROM " + TABLE_tracks;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "Fetching tracks from Sqlite: ");
        Log.d(TAG, "No of tracks fetched: " + cursor.getCount());
        noOfTracks = cursor.getCount();
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            for(int i=0;i<cursor.getCount();i++){
                tracklist[i] = new FavouriteTrack();
                tracklist[i].setTrack_id(cursor.getLong(0));
                tracklist[i].setTrack_name(cursor.getString(1));
                tracklist[i].setArtist_name(cursor.getString(2));
                tracklist[i].setAlbum_name(cursor.getString(3));
                tracklist[i].setGenre(cursor.getString(4));
                tracklist[i].setYear(cursor.getInt(5));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return tracklist;
    }

    public FavouriteArtist[] getAllArtists(){
        FavouriteArtist[] artistlist = new FavouriteArtist[1000];
        String selectQuery = "SELECT  * FROM " + TABLE_artists;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG, "Fetching artists from Sqlite: ");
        Log.d(TAG, "No of artists fetched: " + cursor.getCount());
        noOfArtists = cursor.getCount();
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            for(int i=0;i<cursor.getCount();i++){
                artistlist[i] = new FavouriteArtist();
                artistlist[i] .setArtist_id(cursor.getLong(0));
                artistlist[i] .setArtist_name(cursor.getString(1));
                artistlist[i] .setGenre(cursor.getString(2));
                artistlist[i] .setRating(cursor.getInt(3));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return artistlist;
    }

    public int getNoOfTracks() {
        return noOfTracks;
    }

    public int getNoOfArtists() {
        return noOfArtists;
    }
}

