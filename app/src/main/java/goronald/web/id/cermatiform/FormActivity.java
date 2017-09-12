package goronald.web.id.cermatiform;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import goronald.web.id.cermatiform.model.Form;

public class FormActivity extends AppCompatActivity {

    private Form form;
    private Context mContext;
    private LinearLayout mFormLayout;
    private LinearLayout.LayoutParams mLabelLayoutParams;
    private LinearLayout.LayoutParams mFieldLayoutParams;
    private LinearLayout.LayoutParams mButtonLayoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        mContext = this;
        mFormLayout = (LinearLayout) findViewById(R.id.inputFormLayout);

        mLabelLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mFieldLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mButtonLayoutParams = new LinearLayout.LayoutParams(320, 120);

        Gson gson = new Gson();
        String jsonInString = "{\"content\":[{\"content\":[{\"required\":true,\"slug\":\"loan-amount\",\"placeholder\":\"Contoh : 50.000.000\",\"type\":\"currency\",\"label\":\"Jumlah Pinjaman\"},{\"required\":true,\"slug\":\"loan-period\",\"values\":[\"12 bulan\",\"24 bulan\",\"36 bulan\"],\"type\":\"select\",\"label\":\"Jangka Waktu Pinjaman\"},{\"required\":true,\"slug\":\"loan-purpose\",\"values\":[\"Pendidikan\",\"Kesehatan\",\"Renovasi Rumah\",\"Beli Barang Elektronik\",\"Pernikahan\",\"Liburan\"],\"type\":\"select\",\"label\":\"Tujuan Pinjaman\"},{\"required\":true,\"slug\":\"question-dbs-personal-loan\",\"values\":[\"Ya\",\"Tidak\"],\"type\":\"select\",\"label\":\"Apakah Anda pernah mengajukan aplikasi Dana Bantuan Sahabat dalam 3 bulan terakhir?\"}],\"heading\":\"Informasi Pinjaman\"}]}";
        form = gson.fromJson(jsonInString, Form.class);


        getSupportActionBar().setTitle(form.getContent().get(0).getHeading());
        setFormContent();
    }

    private void setFormContent() {
        for (int i = 0; i < form.getContent().get(0).getContent().size(); i++) {
            switch (form.getContent().get(0).getContent().get(i).getType()) {
                case "currency":
                    TextView tvLabel = new TextView(mContext);
                    tvLabel.setLayoutParams(mLabelLayoutParams);
                    tvLabel.setText(form.getContent().get(0).getContent().get(i).getLabel());

                    EditText myEditText = new EditText(mContext);
                    myEditText.setLayoutParams(mFieldLayoutParams);
                    myEditText.setHint(form.getContent().get(0).getContent().get(i).getPlaceholder());

                    mFormLayout.addView(tvLabel);
                    mFormLayout.addView(myEditText);
                    break;

                case "select":
                    TextView tvLabelSpinner = new TextView(mContext);
                    tvLabelSpinner.setLayoutParams(mLabelLayoutParams);
                    tvLabelSpinner.setText(form.getContent().get(0).getContent().get(i).getLabel());

                    Spinner mySpinner = new Spinner(mContext);
                    mySpinner.setLayoutParams(mFieldLayoutParams);

                    mFormLayout.addView(tvLabelSpinner);
                    mFormLayout.addView(mySpinner);
            }
        }

        Button btnNext = new Button(mContext);
        btnNext.setText(R.string.btn_next_string);
        btnNext.setLayoutParams(mButtonLayoutParams);
        mButtonLayoutParams.gravity = Gravity.CENTER;
        mButtonLayoutParams.topMargin = 120;

        mFormLayout.addView(btnNext);
    }


}
