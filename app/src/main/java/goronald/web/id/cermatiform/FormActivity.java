package goronald.web.id.cermatiform;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import goronald.web.id.cermatiform.model.Content;
import goronald.web.id.cermatiform.model.Form;

public class FormActivity extends AppCompatActivity {

    private static final String TAG = FormActivity.class.getSimpleName();

    private Form form;
    private Context mContext;
    private LinearLayout mFormLayout;
    private LinearLayout.LayoutParams mLabelLayoutParams;
    private LinearLayout.LayoutParams mFieldLayoutParams;
    private LinearLayout.LayoutParams mButtonLayoutParams;
    private TextView tvLabel;
    private EditText edtLabel;
    private List<View> allViewInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        preLoadJsonFormRaw();
        mContext = this;
        mFormLayout = (LinearLayout) findViewById(R.id.inputFormLayout);

        mLabelLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mFieldLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mButtonLayoutParams = new LinearLayout.LayoutParams(320, 120);

        getSupportActionBar().setTitle(form.getFormHeading());
        setFormContent();
    }

    private void setFormContent() {
        allViewInstance = new ArrayList<>();
        for (int i = 0; i < form.getContent().size(); i++) {
            Content content = form.getContent().get(i);
            switch (form.getContent().get(i).getType()) {
                case "currency":
                    TextView tvLabel = new TextView(mContext);
                    tvLabel.setLayoutParams(mLabelLayoutParams);
                    tvLabel.setText(content.getLabel());
                    mFormLayout.addView(tvLabel);

                    EditText myEditText = new EditText(mContext);
                    myEditText.setLayoutParams(mFieldLayoutParams);
                    myEditText.setHint(content.getPlaceholder());
                    myEditText.setTag(content.getSlug());

                    allViewInstance.add(myEditText);
                    mFormLayout.addView(myEditText);
                    break;

                case "select":
                    TextView tvLabelSpinner = new TextView(mContext);
                    tvLabelSpinner.setLayoutParams(mLabelLayoutParams);
                    tvLabelSpinner.setText(content.getLabel());
                    mFormLayout.addView(tvLabelSpinner);

                    Spinner mySpinner = new Spinner(mContext);
                    allViewInstance.add(mySpinner);
                    mySpinner.setLayoutParams(mFieldLayoutParams);
                    String[] spinnerData = content.getValues();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_item, spinnerData);
                    mySpinner.setAdapter(adapter);
                    mySpinner.setSelection(0, false);
                    mFormLayout.addView(mySpinner);
            }
        }

        Button btnNext = new Button(mContext);
        btnNext.setText(R.string.btn_next_string);
        btnNext.setLayoutParams(mButtonLayoutParams);
        mButtonLayoutParams.gravity = Gravity.CENTER;
        mButtonLayoutParams.topMargin = 120;

        mFormLayout.addView(btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                submitForm();
            }
        });
    }

//    private void submitForm() {
//        Intent intent = new Intent(mContext, OutputActivity.class);
//        intent.putExtra(OutputActivity.EXTRA_INPUT_OBJECT, );
//        for (int i = 0; i < allViewInstance.size(); i++) {
//            switch (allViewInstance.get(i).getTag()) {
//                case currency:
//                    TextView tvLabel = new TextView(mContext);
//                    tvLabel.setLayoutParams(mLabelLayoutParams);
//                    tvLabel.setText(form.getContent().get(0).getContent().get(i).getLabel());
//                    mFormLayout.addView(tvLabel);
//
//                    EditText myEditText = new EditText(mContext);
//                    myEditText.setLayoutParams(mFieldLayoutParams);
//                    myEditText.setHint(form.getContent().get(0).getContent().get(i).getPlaceholder());
//                    myEditText.setTag(form.getContent().get(0).getContent().get(i).getSlug());
//
//                    allViewInstance.add(myEditText);
//                    mFormLayout.addView(myEditText);
//                    break;
//
//                case "select":
//                    TextView tvLabelSpinner = new TextView(mContext);
//                    tvLabelSpinner.setLayoutParams(mLabelLayoutParams);
//                    tvLabelSpinner.setText(form.getContent().get(0).getContent().get(i).getLabel());
//                    mFormLayout.addView(tvLabelSpinner);
//
//                    Spinner mySpinner = new Spinner(mContext);
//                    allViewInstance.add(mySpinner);
//                    mySpinner.setLayoutParams(mFieldLayoutParams);
//                    String[] spinnerData = form.getContent().get(0).getContent().get(i).getValues().toArray(new String[0]);
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                            android.R.layout.simple_spinner_item, spinnerData);
//                    mySpinner.setAdapter(adapter);
//                    mySpinner.setSelection(0, false);
//                    mFormLayout.addView(mySpinner);
//            }
//        }
//    }

    private void preLoadJsonFormRaw() {
        String json = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(R.raw.data);
            reader = new BufferedReader(new InputStreamReader(raw_dict));
            json = String.valueOf(reader.read());
            Log.d(TAG, json);

            JSONObject responseObject = new JSONObject(json);
            JSONObject _content = responseObject.getJSONObject("content");
            form = new Form(_content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
