package sam.thomas.excelsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import sam.thomas.excelsms.helpers.DBHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
public class SmsAutoReplyReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String[] keywords = {"help", "support", "contact"};
    private static final String replyMessageNotFound = "I'm sorry, the requested information was not found in the database.";
    private DBHelper dbHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus != null) {
                    for (Object pdu : pdus) {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                        String sender = smsMessage.getOriginatingAddress();
                        String message = smsMessage.getMessageBody();

                        // Check if the message contains a keyword that we are interested in.
                        for (String keyword : keywords) {
                            if (message.contains(keyword)) {
                                // Search the SQLite database for the value and retrieve the results.
                                String searchValue = message.substring(message.indexOf(keyword) + keyword.length()).trim();
                                String databaseResults = searchDatabase(context, searchValue);

                                // Send the database results as a reply message if found, otherwise send a not found message.
                                String replyMessage = databaseResults.isEmpty() ? replyMessageNotFound : databaseResults;
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(sender, null, replyMessage, null, null);

                                // Exit the loop once a keyword match is found.
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private String searchDatabase(Context context, String searchValue) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Perform the database query to retrieve the results based on the search value
        Cursor cursor = db.rawQuery("SELECT * FROM MyTable1 WHERE Company LIKE ?", new String[]{"%" + searchValue + "%"});

        StringBuilder resultBuilder = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                int companyColumnIndex = cursor.getColumnIndex("Company");
                int productColumnIndex = cursor.getColumnIndex("Product");
                int priceColumnIndex = cursor.getColumnIndex("Price");
                int engColumnIndex = cursor.getColumnIndex("Eng");
                int xkis = cursor.getColumnIndex("Kis");
                int xmat = cursor.getColumnIndex("Mat");
                int xbio = cursor.getColumnIndex("Bio");
                int xphy = cursor.getColumnIndex("Phy");
                int xche = cursor.getColumnIndex("Che");
                int xhis = cursor.getColumnIndex("His");
                int xgeo = cursor.getColumnIndex("Geo");
                int xcre = cursor.getColumnIndex("Cre");
//                int xagr = cursor.getColumnIndex("Agr");
//                int xcom = cursor.getColumnIndex("Com");
//                int xfre = cursor.getColumnIndex("Fre");
//                int xmus = cursor.getColumnIndex("Mus");
//                int xbst = cursor.getColumnIndex("Bst");
//                int xsbj = cursor.getColumnIndex("Sbj");
//                int xvap = cursor.getColumnIndex("Vap");
//                int xtmks = cursor.getColumnIndex("Tmks");
//                int xttpts = cursor.getColumnIndex("Ttpts");
//                int xgr = cursor.getColumnIndex("Gr");
//                int xspos = cursor.getColumnIndex("SPos");
//                int xopos = cursor.getColumnIndex("OPos");

                if (companyColumnIndex != -1 && productColumnIndex != -1
                        && priceColumnIndex != -1 && engColumnIndex != -1
                        && xkis != -1 && xmat != -1 && xbio != -1
                        && xphy != -1 && xche != -1 && xhis != -1
                        && xgeo != -1 && xcre != -1 //&& xagr != -1
//                        && xcom != -1 //&& xfre != -1 && xmus != -1
//                        && xbst != -1 && xsbj != -1 && xvap != -1
//                        && xtmks != -1 && xttpts != -1 && xgr != -1
//                        && xspos != -1 && xopos != -1
                ) {
                    String company = cursor.getString(companyColumnIndex);
                    String product = cursor.getString(productColumnIndex);
                    String price = cursor.getString(priceColumnIndex);
                    String eng = cursor.getString(engColumnIndex);
                    String kis = cursor.getString(xkis);
                    String mat = cursor.getString(xmat);
                    String bio = cursor.getString(xbio);
                    String phy = cursor.getString(xphy);
                    String che = cursor.getString(xche);
                    String his = cursor.getString(xhis);
                    String geo = cursor.getString(xgeo);
                    String cre = cursor.getString(xcre);
//                    String agr = cursor.getString(xagr);
//                    String com = cursor.getString(xcom);
//                    String fre = cursor.getString(xfre);
//                    String mus = cursor.getString(xmus);
//                    String bst = cursor.getString(xbst);
//                    String sbj = cursor.getString(xsbj);
//                    String vap = cursor.getString(xvap);
//                    String tmks = cursor.getString(xtmks);
//                    String ttpts = cursor.getString(xttpts);
//                    String gr = cursor.getString(xgr);
//                    String spos = cursor.getString(xspos);
//                    String opos = cursor.getString(xopos);

                    resultBuilder.append("ADMNO: ").append(company)
                            .append(", NAME: ").append(product)
                            .append(", STR: ").append(price)
                            .append(", ENG: ").append(eng)
                            .append(", KIS: ").append(kis)
                            .append(", MAT: ").append(mat)
                            .append(", BIO: ").append(bio)
                            .append(", PHY: ").append(phy)
                            .append(", CHE: ").append(che)
                            .append(", HIS: ").append(his)
                            .append(", GEO: ").append(geo)
                            .append(", CRE: ").append(cre)
//                            .append(", AGR: ").append(agr)
//                            .append(", COM: ").append(com)
//                            .append(", FRE: ").append(fre)
//                            .append(", MUS: ").append(mus)
//                            .append(", BST: ").append(bst)
//                            .append(", SBJ: ").append(sbj)
//                            .append(", VAP: ").append(vap)
//                            .append(", TMKS: ").append(tmks)
//                            .append(", TTPTS: ").append(ttpts)
//                            .append(", GR: ").append(gr)
//                            .append(", SPOS: ").append(spos)
//                            .append(", OPOS: ").append(opos)
                            .append("\n");
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return resultBuilder.toString();
    }

}
