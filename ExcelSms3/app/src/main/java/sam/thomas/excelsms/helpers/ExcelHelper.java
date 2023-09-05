package sam.thomas.excelsms.helpers;

import android.content.ContentValues;
import android.util.Log;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Iterator;


public class ExcelHelper {

    public static final String Tablename = "MyTable1";
    public static final String id = "_id";// 0 integer
    public static final String Company = "Company";// 1 text(String)
    public static final String Product = "Product";// 2 text(String)
    public static final String Price = "Price";// 3 text(String)

    public static void insertExcelToSqlite(DBHelper dbAdapter, Sheet sheet) {

        for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext(); ) {
            Row row = rit.next();

            ContentValues contentValues = new ContentValues();
            row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//ad
            row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//nam
            row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//str
            row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//eng
            row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//kis
            row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//mat
            row.getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//bio
            row.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//phy
            row.getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//che
            row.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//his
            row.getCell(10, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//geo
            row.getCell(11, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//cre
            row.getCell(12, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//agr
            row.getCell(13, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//com
            row.getCell(14, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//fre
            row.getCell(15, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//mus
            row.getCell(16, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//bst
            row.getCell(17, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//sbj
            row.getCell(19, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//vap
            row.getCell(21, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//tmks
            row.getCell(22, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//ttpts
            row.getCell(24, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//gr
            row.getCell(25, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//s.pos
            row.getCell(26, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);//O.POS


            contentValues.put(Company, row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(Product, row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put(Price, row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put("Eng", row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put("Kis", row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put("Mat", row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put("Bio", row.getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put("Phy", row.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put("Che", row.getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put("His", row.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put("Geo", row.getCell(10, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put("Cre", row.getCell(11, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put("Agr", row.getCell(12, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put("Com", row.getCell(13, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put("Fre", row.getCell(14, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put("Mus", row.getCell(15, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put("Bst", row.getCell(16, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put("Sbj", row.getCell(17, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put("Vap", row.getCell(19, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put("Tmks", row.getCell(21, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put("Ttpts", row.getCell(22, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put("Gr", row.getCell(24, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put("SPos", row.getCell(25, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            contentValues.put("OPos", row.getCell(26, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
            try {
                if (dbAdapter.insert("MyTable1", contentValues) < 0) {
                    return;
                }
            } catch (Exception ex) {
                Log.d("Exception in importing", ex.getMessage().toString());
            }
        }
    }
}