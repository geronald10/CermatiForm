package goronald.web.id.cermatiform;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OutputActivity extends AppCompatActivity {

    public static final String EXTRA_INPUT_OBJECT = "extra_input_object";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
    }
}
