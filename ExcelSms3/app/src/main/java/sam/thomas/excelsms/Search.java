package sam.thomas.excelsms;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import sam.thomas.excelsms.helpers.DBHelper;
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
                resultBuilder.append("STR: ").append(result.get(DBHelper.Price)).append("\t\t");
                resultBuilder.append("ENG: ").append(result.get("Eng")).append("\t\t");
                resultBuilder.append("KIS: ").append(result.get("Kis")).append("\t\t");
                resultBuilder.append("MAT: ").append(result.get("Mat")).append("\t\t");
                resultBuilder.append("BIO: ").append(result.get("Bio")).append("\t\t");
                resultBuilder.append("PHY: ").append(result.get("Phy")).append("\t\t");
                resultBuilder.append("CHE: ").append(result.get("Che")).append("\t\t");
                resultBuilder.append("HIS: ").append(result.get("His")).append("\t\t");
                resultBuilder.append("GEO: ").append(result.get("Geo")).append("\t\t");
                resultBuilder.append("CRE: ").append(result.get("Cre")).append("\t\t");
                resultBuilder.append("AGR: ").append(result.get("Agr")).append("\t\t");
                resultBuilder.append("COM: ").append(result.get("Com")).append("\t\t");
                resultBuilder.append("FRE: ").append(result.get("Fre")).append("\t\t");
                resultBuilder.append("MUS: ").append(result.get("Mus")).append("\t\t");
                resultBuilder.append("BST: ").append(result.get("Bst")).append("\t\t");
                resultBuilder.append("SBJ: ").append(result.get("Sbj")).append("\t\t");
                resultBuilder.append("VAP: ").append(result.get("Vap")).append("\t\t");
                resultBuilder.append("TMKS: ").append(result.get("Tmks")).append("\t\t");
                resultBuilder.append("TTPTS: ").append(result.get("Ttpts")).append("\t\t");
                resultBuilder.append("GR: ").append(result.get("Gr")).append("\t\t");
                resultBuilder.append("SPOS: ").append(result.get("SPos")).append("\t\t");
                resultBuilder.append("OPOS: ").append(result.get("OPos")).append("\t\t");
            }
            txtResults.setText(resultBuilder.toString());
        } else {
            // No matching record found
            Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
            txtResults.setText("");
        }
    }
}
