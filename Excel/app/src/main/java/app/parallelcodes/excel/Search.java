package app.parallelcodes.excel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import app.parallelcodes.excel.helpers.DBHelper;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class Search extends AppCompatActivity {
    private EditText txtSearch;
    private Button btnSearch;
    private TextView txtResults;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        txtSearch = findViewById(R.id.search);
        btnSearch = findViewById(R.id.btnsearch);
        txtResults = findViewById(R.id.textresults);

        dbHelper = new DBHelper(this);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });
    }

    private void performSearch() {
        String searchValue = txtSearch.getText().toString();

        // Open the database
        dbHelper.open();

        // Perform the search
        ArrayList<HashMap<String, String>> searchResults = dbHelper.getCompany(searchValue);

        // Close the database
        dbHelper.close();

        if (!searchResults.isEmpty()) {
            // Display the results
            StringBuilder resultBuilder = new StringBuilder();
            for (HashMap<String, String> result : searchResults) {
                resultBuilder.append("ADMNO: ").append(result.get(DBHelper.Company)).append("\t\t");
                resultBuilder.append("NAME: ").append(result.get(DBHelper.Product)).append("\t\t");
                resultBuilder.append("STR: ").append(result.get(DBHelper.Price)).append("\n");
            }
            txtResults.setText(resultBuilder.toString());
        } else {
            // No matching record found
            Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
            txtResults.setText("");
        }
    }
}




