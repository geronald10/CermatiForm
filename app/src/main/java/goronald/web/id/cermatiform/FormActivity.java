package goronald.web.id.cermatiform;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
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

import java.io.BufferedInputStream;
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
        mFieldLayoutParams.setMargins(10, 20, 10, 20);

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
                    myEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

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
                Log.d(TAG, "onClick on btnNext");
                submitForm();
            }
        });
    }

    private void submitForm() {
        for (int i = 0; i < form.getContent().size(); i++) {
            Content content = form.getContent().get(i);
            switch (content.getType()) {
                case "currency":
                    EditText edtText = (EditText) allViewInstance.get(i);
                    if (TextUtils.isEmpty(edtText.getText().toString())) {
                        if (content.isRequired()) {
                            edtText.setError(content.getLabel() + " is required!");
                            return;
                        } else {
                            content.setValue(edtText.getText().toString());
                        }
                    } else {
                        content.setValue(edtText.getText().toString());
                    }
                    break;

                case "select":
                    Spinner mySpinner = (Spinner) allViewInstance.get(i);
                    String[] values = content.getValues();
                    content.setValue(values[mySpinner.getSelectedItemPosition()]);
                    break;
            }
        }
        Intent intent = new Intent(mContext, OutputActivity.class);
        intent.putExtra(OutputActivity.EXTRA_INPUT_OBJECT, form);
        startActivity(intent);
    }

    private void preLoadJsonFormRaw() {
        StringBuilder json;
        try {
            BufferedInputStream resourceStream = new BufferedInputStream(getResources()
                    .openRawResource(R.raw.data));
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream));
            String line;
            json = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            reader.close();
            resourceStream.close();
            JSONObject responseObject = new JSONObject(json.toString());
            JSONObject _content = responseObject.getJSONArray("content")
                    .getJSONObject(0);
            form = new Form(_content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
