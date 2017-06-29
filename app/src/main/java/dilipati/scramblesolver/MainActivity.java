package dilipati.scramblesolver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private EditText scrambledWord1, scrambledWord2, scrambledWord3, scrambledWord4;
    private TextView properWord1, properWord2, properWord3, properWord4;
    private String input1, input2, input3, input4 , output1, output2, output3, output4;
    private Button solve;
    private HashSet<String> dictionary, anagramList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrambledWord1 = (EditText) findViewById(R.id.scrambledWord1);
        scrambledWord2 = (EditText) findViewById(R.id.scrambledWord2);
        scrambledWord3 = (EditText) findViewById(R.id.scrambledWord3);
        scrambledWord4 = (EditText) findViewById(R.id.scrambledWord4);
        properWord1 = (TextView) findViewById(R.id.properWord1);
        properWord2 = (TextView) findViewById(R.id.properWord2);
        properWord3 = (TextView) findViewById(R.id.properWord3);
        properWord4 = (TextView) findViewById(R.id.properWord4);
        solve = (Button) findViewById(R.id.solve);
        solve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solve.setText("Loading Dictionary...");
                input1 = scrambledWord1.getText().toString().toLowerCase();
                input2 = scrambledWord2.getText().toString().toLowerCase();
                input3 = scrambledWord3.getText().toString().toLowerCase();
                input4 = scrambledWord4.getText().toString().toLowerCase();
                int minLength = Integer.MAX_VALUE, maxLength = Integer.MIN_VALUE;
                if(input1.length() > maxLength){
                    maxLength = input1.length();
                }
                if(input1.length() < minLength){
                    minLength = input1.length();
                }
                if(input2.length() > maxLength){
                    maxLength = input2.length();
                }
                if(input2.length() < minLength){
                    minLength = input2.length();
                }
                if(input3.length() > maxLength){
                    maxLength = input3.length();
                }
                if(input3.length() < minLength){
                    minLength = input3.length();
                }
                if(input4.length() > maxLength){
                    maxLength = input4.length();
                }
                if(input4.length() < minLength){
                    minLength = input4.length();
                }
                if(!input1.equals("") || !input1.equals("") || !input1.equals("") || !input1.equals("")) {
                    try {
                        loadDictionary(minLength, maxLength, input1, input2, input3, input4);
                    } catch (FileNotFoundException ignore) {}
                    solve();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter all the 4 scrambled words", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void loadDictionary(int minLength, int maxLength, String input1, String input2, String input3, String input4) throws FileNotFoundException{

        InputStream is = getApplicationContext().getResources().openRawResource(R.raw.words);
        Scanner file = new Scanner(is);
        dictionary = new HashSet<>();
        while (file.hasNext()) {
            String next = file.next();
            if(next.length() >= minLength && next.length() <= maxLength) {
                if(next.charAt(0) == input1.charAt(0) || next.charAt(0) == input2.charAt(0) || next.charAt(0) == input3.charAt(0) || next.charAt(0) == input4.charAt(0)) {
                    dictionary.add(next.toLowerCase());
                }
            }
        }
    }

    private void solve() {
        properWord1.setText("");
        properWord2.setText("");
        properWord3.setText("");
        properWord4.setText("");
        if(dictionary!=null) {
            output1 = dictionaryAnagramTraversal(input1);
            output2 = dictionaryAnagramTraversal(input2);
            output3 = dictionaryAnagramTraversal(input3);
            output4 = dictionaryAnagramTraversal(input4);
            if(output1!=null) {
                properWord1.setText(output1);
            }
            if(output2!=null) {
                properWord2.setText(output2);
            }
            if(output3!=null) {
                properWord3.setText(output3);
            }
            if(output4!=null) {
                properWord4.setText(output4);
            }
        }
        solve.setText("Solve");
    }

    private String dictionaryAnagramTraversal(String input){
        String output = null;
        anagramList = new HashSet<>();
        generateAnagrams(input);
        if(anagramList.size()!=0){
            for (String anagram : anagramList) {
                if(dictionary.contains(anagram)) {
                    output = anagram;
                    anagramList = null;
                    break;
                }
            }
        }
        return output;
    }

    public void generateAnagrams(String str) {
        permutation("", str);
    }

    private void permutation(String prefix, String str) {
        int n = str.length();
        if (n == 0) {
            anagramList.add(prefix);
        }
        else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
        }
    }
}
