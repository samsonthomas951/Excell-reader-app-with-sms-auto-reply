package app.parallelcodes.excel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import app.parallelcodes.excel.helpers.DBHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;


public class SmsAutoReplyReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String[] keywords = {"help", "support", "contact"};
    private static final String replyMessageNotFound = "I'm sorry, the requested information was not found in the database.";

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
                if (companyColumnIndex != -1 && productColumnIndex != -1 && priceColumnIndex != -1) {
                    String company = cursor.getString(companyColumnIndex);
                    String product = cursor.getString(productColumnIndex);
                    String price = cursor.getString(priceColumnIndex);
                    resultBuilder.append("ADMNO: ").append(company).append(", NAME: ").append(product).append(", STR: ").append(price).append("\n");
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return resultBuilder.toString();
    }
}

