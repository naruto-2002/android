package DATABASE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.helloworld.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import model.GiaoDich;
import model.LoaiGD;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="ChiTieu.db";
    private static int DATABASE_VERSION = 1;
    private final Context mContext;
    public SQLiteHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table loaiGD( "+
                "id integer primary key autoincrement,"+
                "src integer, " +
                "name TEXT " +
                ")";
        db.execSQL(sql);
        String insertValues = "INSERT INTO loaiGD (src, name) VALUES " +
                "("+ R.drawable.all+",'Tất cả'), " +
                "("+ R.drawable.eating+",'Ăn Uống'), " +
                "("+ R.drawable.ecology+",'Tiền Nước'), " +
                "("+ R.drawable.education+",'Học tập'), " +
                "("+ R.drawable.eletric+",'Tiền điện'), " +
                "("+ R.drawable.giftbox+",'Quà Tặng'), " +
                "("+ R.drawable.gym+",'GYM'), " +
                "("+ R.drawable.heart+",'Sức khỏe'), " +
                "("+ R.drawable.giaitri+",'Giải trí'), " +
                "("+ R.drawable.moving+",'Di chuyển'), " +
                "("+ R.drawable.plus+",'Khác')";
        db.execSQL(insertValues);
        String createTableNew = "CREATE TABLE GiaoDich (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "date DATE," +
                "sotien REAL," +
                "loaigd INTEGER," +
                "chitiet TEXT," +
                "inorout INTEGER," +
                "FOREIGN KEY(loaigd) REFERENCES loaiGD(id)" +
                ")";
        db.execSQL(createTableNew);
        String insertValues1 = "INSERT INTO GiaoDich (date, sotien, loaigd, chitiet,inorout) VALUES " +
                "('2024-03-01', 10000, 2, 'ádfasdf',1)," + // Đảm bảo định dạng ngày tháng YYYY-MM-DD
                "('2024-03-02', 51322, 3, 'qwerqwer',2)," +
                "('2024-03-03', 234, 4, 'xvczxcv',1)," +
                "('2024-03-05', 1246, 6, 'uenciao',2)," +
                "('2024-03-07', 842, 3, 'qwerqwer',2)," +
                "('2024-03-08', 632, 4, 'xvczxcv',1)," +
                "('2024-03-11', 777, 6, 'uenciao',2)," +
                "('2024-03-18', 332, 3, 'qwerqwer',2)," +
                "('2024-03-23', 894, 4, 'xvczxcv',1)," +
                "('2024-03-25', 12, 6, 'uenciao',2)," +
                "('2024-03-31', 52368, 9, '274icujw3ixj',1)";
        db.execSQL(insertValues1);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public List<LoaiGD> getAllLoaiGD(){
        List<LoaiGD> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String order = "id ASC";
        Cursor rs = sqLiteDatabase.query("loaiGD",null,null,
                                            null,null,null,order);
        while (rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            int src = rs.getInt(1);
            String name = rs.getString(2);
            list.add(new LoaiGD(id,src,name));
        }
        return list;
    }
    public long insertLoaiGiaoDich(LoaiGD loaiGD){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("src", loaiGD.getSrcIcon());
        values.put("name", loaiGD.getNameIcon());
        long id = db.insert("loaiGD", null, values);
        db.close();
        return id;
    }
    public LoaiGD getLoaiGD(String id){
        LoaiGD list = null;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] columns = {"id", "src", "name"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor rs = sqLiteDatabase.query("loaiGD", columns, selection,
                selectionArgs, null, null, null);
        while (rs!=null && rs.moveToNext()){
            int src = rs.getInt(1);
            String name = rs.getString(2);
            list = new LoaiGD(Integer.valueOf(id),src,name);
        }
        return list;
    }
    public List<GiaoDich> getAllGD(String order) {
        if(order.equals("1")) order="";
        List<GiaoDich> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String whereClause = "loaigd = ?";
        String[] whereArgs = {order};
        Cursor rs = null;
        if(order.equals("")){
            rs = sqLiteDatabase.query("GiaoDich", null, null,
                    null, null, null, null);
        }else{
            rs = sqLiteDatabase.query("GiaoDich", null, whereClause,
                    whereArgs, null, null, null);
        }
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String date = rs.getString(1);
            String sotien = rs.getString(2);
            String loaigd = rs.getString(3);
            String mota = rs.getString(4);
            int inorout = rs.getInt(5);
            LoaiGD loaiGD1 = getLoaiGD(loaigd);
//            System.out.println(loaiGD1.getID()+" ahshsj "+loaiGD1.getNameIcon());
            list.add(new GiaoDich(id, Date.valueOf(date), Float.parseFloat(sotien), loaiGD1, mota,inorout));
        }
        return list;
    }
    public GiaoDich getGD(String id){
        GiaoDich list = null;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] columns = {"id", "date", "sotien", "loaigd","inorout"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor rs = sqLiteDatabase.query("GiaoDich", columns, selection,
                selectionArgs, null, null, null);

        while (rs!=null && rs.moveToNext()){
            String date = rs.getString(1);
            String sotien = rs.getString(2);
            String loaigd = rs.getString(3);
            String mota = rs.getString(4);
            int inorout = rs.getInt(5);
            LoaiGD loaiGD1 = getLoaiGD(loaigd);
            list = new GiaoDich(Integer.valueOf(id), Date.valueOf(date),Float.parseFloat(sotien),
                    loaiGD1,mota,inorout);
        }
        return list;
    }
    public long insertGiaoDich(GiaoDich giaoDich) {
        SQLiteDatabase db = getReadableDatabase();
//        System.out.println(giaoDich.getID()+" "+giaoDich.getSotien()+" "+giaoDich.getDate().toString()
//                +" "+giaoDich.getLoaiGD().getID()+" "+
//                giaoDich.getMota()+" "+giaoDich.getKieugd());
        ContentValues values = new ContentValues();
        values.put("date", giaoDich.getDate().toString());
        values.put("sotien", giaoDich.getSotien());
        values.put("loaigd", giaoDich.getLoaiGD().getID());
        values.put("chitiet", giaoDich.getMota());
        values.put("inorout",giaoDich.getKieugd());
        long id = db.insert("GiaoDich", null, values);
        db.close();
        return id;
    }

    public List<GiaoDich> getListOrderGD(String idloaigd, String sotienmin,String sotienmax, String ngayStart, String ngayEnd) {
        List<GiaoDich> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        StringBuilder whereClauseBuilder = new StringBuilder();
        List<String> whereArgsList = new ArrayList<>();

        if(idloaigd.equals("1")) idloaigd="";
        if (!idloaigd.equals("")) {
            System.out.println(idloaigd+" id loai gd");
            whereClauseBuilder.append("loaigd = ?");
            whereArgsList.add(idloaigd);
        }

        if (!sotienmin.equals("")) {
            if (whereClauseBuilder.length() > 0) {
                whereClauseBuilder.append(" AND ");
            }
            whereClauseBuilder.append("sotien >= ?");
            whereArgsList.add(sotienmin);
        }

        if (!sotienmax.equals("")) {
            if (whereClauseBuilder.length() > 0) {
                whereClauseBuilder.append(" AND ");
            }
            whereClauseBuilder.append("sotien <= ?");
            whereArgsList.add(sotienmax);
        }

        if (!ngayStart.equals("")) {
            if (whereClauseBuilder.length() > 0) {
                whereClauseBuilder.append(" AND ");
            }
            whereClauseBuilder.append("date >= ?");
            whereArgsList.add(ngayStart);
        }

        if (!ngayEnd.equals("")) {
            if (whereClauseBuilder.length() > 0) {
                whereClauseBuilder.append(" AND ");
            }
            whereClauseBuilder.append("date <= ?");
            whereArgsList.add(ngayEnd);
        }

        String[] whereArgs = whereArgsList.toArray(new String[0]);

        Cursor rs = sqLiteDatabase.query("GiaoDich", null,
                whereClauseBuilder.length() > 0 ? whereClauseBuilder.toString() : null,
                whereClauseBuilder.length() > 0 ? whereArgs : null,
                null, null, null);

        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String date = rs.getString(1);
            String sotien = rs.getString(2);
            String loaigd = rs.getString(3);
            String mota = rs.getString(4);
            int inorout = rs.getInt(5);
            LoaiGD loaiGD1 = getLoaiGD(loaigd);

            list.add(new GiaoDich(id, Date.valueOf(date), Float.parseFloat(sotien), loaiGD1, mota, inorout));
        }
        return list;
    }
}
