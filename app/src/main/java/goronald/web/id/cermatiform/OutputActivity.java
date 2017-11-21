package goronald.web.id.cermatiform;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import goronald.web.id.cermatiform.model.Content;
import goronald.web.id.cermatiform.model.Form;

public class OutputActivity extends AppCompatActivity {

    public static final String EXTRA_INPUT_OBJECT = "extra_input_object";

    private Form form;
    private Context mContext;
    private LinearLayout mFormLayout;
    private LinearLayout.LayoutParams mOutputFieldLayoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        mContext = this;
        form = getIntent().getParcelableExtra(EXTRA_INPUT_OBJECT);
        mFormLayout = (LinearLayout) findViewById(R.id.output_form);

        mOutputFieldLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mOutputFieldLayoutParams.setMargins(10, 0, 0, 20);

        getSupportActionBar().setTitle(form.getFormHeading());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setOutputForm();
    }

    private void setOutputForm() {
        for (int i = 0; i < form.getContent().size(); i++) {
            Content content = form.getContent().get(i);
            String field;
            TextView tvField = new TextView(mContext);
            tvField.setLayoutParams(mOutputFieldLayoutParams);
            tvField.setTextSize(20);
            switch (content.getType()) {
                case "currency":
                    field = content.getLabel() + " : IDR " + content.getValue();
                    break;
                default:
                    field = content.getLabel() + " : " + content.getValue();
                    break;
            }
            tvField.setText(field);
            mFormLayout.addView(tvField);
        }
    }
}
