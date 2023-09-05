package sam.thomas.excelsms;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import sam.thomas.excelsms.helpers.DBHelper;
import sam.thomas.excelsms.helpers.ExcelHelper;



public class MainActivity extends AppCompatActivity {

    static {
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLInputFactory",
                "com.fasterxml.aalto.stax.InputFactoryImpl"
        );
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLOutputFactory",
                "com.fasterxml.aalto.stax.OutputFactoryImpl"
        );
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLEventFactory",
                "com.fasterxml.aalto.stax.EventFactoryImpl"
        );
    }

    TextView lbl;
    DBHelper controller = new DBHelper(this);
    ListView lv;

    private static final int PERMISSION_REQUEST_MEMORY_ACCESS = 0;
    private static final int PERMISSION_REQUEST_READ_SMS = 3;
    private static final int PERMISSION_REQUEST_SEND_SMS = 4;


    private static String fileType = "";
    private View mLayout;
    private static String extensionXLS = "XLS";
    private static String extensionXLXS = "XLXS";
    ActivityResultLauncher<Intent> filePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lbl = (TextView) findViewById(R.id.txtresulttext);
        lv = findViewById(R.id.lstView);
        mLayout = findViewById(R.id.main_layout);

        filePicker = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent intent1 = result.getData();

                        Uri uri = intent1.getData();
                        ReadExcelFile(MainActivity.this
                                , uri);

                    }
                });
        FillList();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!CheckPermission()) {
                requestPermissions(new String[]{Manifest.permission.READ_SMS}, PERMISSION_REQUEST_READ_SMS);
            }
        }

    }



    private boolean CheckPermission() {
        boolean hasReadPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        boolean hasSendPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
        boolean hasReceivePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;

        if (hasReadPermission && hasSendPermission && hasReceivePermission) {
            return true;
        } else {
            ArrayList<String> permissions = new ArrayList<>();
            if (!hasReadPermission) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (!hasSendPermission) {
                permissions.add(Manifest.permission.SEND_SMS);
            }
            if (!hasReceivePermission) {
                permissions.add(Manifest.permission.RECEIVE_SMS);
            }

            String[] permissionArray = new String[permissions.size()];
            permissions.toArray(permissionArray);

            ActivityCompat.requestPermissions(this, permissionArray, PERMISSION_REQUEST_MEMORY_ACCESS);

            return false;
        }
    }


    public void FillList() {
        try {
            if (controller == null) {
                DBHelper controller = new DBHelper(MainActivity.this);
            }
            ArrayList<HashMap<String, String>> myList = controller.getProducts();
            if (myList.size() != 0) {
                lv = findViewById(R.id.lstView);
                ListAdapter adapter = new SimpleAdapter(MainActivity.this, myList,
                        R.layout.lst_template, new String[]{DBHelper.Company, DBHelper.Product,
                        DBHelper.Price,"Eng"
                },
                        new int[]{R.id.txtproductcompany, R.id.txtproductname,
                                R.id.txtproductprice,R.id.txtengl
                        });
                lv.setAdapter(adapter);
            }
        } catch (Exception ex) {
            Toast("FillList error: " + ex.getMessage(), ex);
        }
    }

    public void ReadExcelFile(Context context, Uri uri) {
        try {
            InputStream inStream;
            Workbook wb = null;

            try {
                inStream = context.getContentResolver().openInputStream(uri);

                if (fileType == extensionXLS)
                    wb = new HSSFWorkbook(inStream);
                else
                    wb = new XSSFWorkbook(inStream);

                inStream.close();
            } catch (IOException e) {
                lbl.setText("First " + e.getMessage().toString());
                e.printStackTrace();
            }

            DBHelper dbAdapter = new DBHelper(this);
            Sheet sheet1 = wb.getSheetAt(0);

            dbAdapter.open();
            dbAdapter.delete();
            dbAdapter.close();
            dbAdapter.open();
            ExcelHelper.insertExcelToSqlite(dbAdapter, sheet1);

            dbAdapter.close();

            FillList();
        } catch (Exception ex) {
            Toast("ReadExcelFile Error:" + ex.getMessage().toString(), ex);
        }
    }


    public void ChooseFile() {
        try {
            Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
            fileIntent.addCategory(Intent.CATEGORY_OPENABLE);

            if (fileType == extensionXLS)
                fileIntent.setType("application/vnd.ms-excel");
            else
                fileIntent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            filePicker.launch(fileIntent);
        } catch (Exception ex) {
            Toast("ChooseFile error: " + ex.getMessage().toString(), ex);

        }
    }

    void Toast(String message, Exception ex) {
        if (ex != null)
            Log.e("Error", ex.getMessage().toString());
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_MEMORY_ACCESS) {
            boolean allPermissionsGranted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted) {
                OpenFilePicker();
            } else {
                Snackbar.make(mLayout, R.string.storage_access_required, Snackbar.LENGTH_SHORT).show();
            }
        } else if (requestCode == PERMISSION_REQUEST_READ_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read SMS permission granted.", Toast.LENGTH_SHORT).show();
                // Perform actions requiring read SMS permission
            } else {
                Toast.makeText(this, "Read SMS permission denied.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PERMISSION_REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Send SMS permission granted.", Toast.LENGTH_SHORT).show();
                // Perform actions requiring send SMS permission
            } else {
                Toast.makeText(this, "Send SMS permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void requestStoragePermission() {
        ArrayList<String> permissions = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.SEND_SMS);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.RECEIVE_SMS);
        }

        String[] permissionArray = new String[permissions.size()];
        permissions.toArray(permissionArray);

        ActivityCompat.requestPermissions(MainActivity.this, permissionArray, PERMISSION_REQUEST_MEMORY_ACCESS);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_import_xls) {
            fileType = extensionXLS;
            OpenFilePicker();
        } else if (id == R.id.action_import_xlxs) {
            fileType = extensionXLXS;
            OpenFilePicker();
        } else if (id == R.id.search) {
            Intent intent = new Intent(MainActivity.this, Search.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void OpenFilePicker() {
        try {
            if (CheckPermission()) {
                ChooseFile();
            }
        } catch (ActivityNotFoundException e) {
            lbl.setText("No activity can handle picking a file. Showing alternatives.");
        }

    }



}