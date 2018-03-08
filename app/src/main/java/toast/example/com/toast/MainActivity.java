package toast.example.com.toast;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

   public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private EditText viewById;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewById = findViewById(R.id.et_view);
        viewById.setText("去年八月，曹云金被拍到与唐菀拍到十指交扣牵手同行，还同回公寓，整夜未出，恋情曝光。此后在新剧探班时，曹云金大方承认恋情，称“奔着结婚去");
    }


    public void onclick(View view) {

//        FragmentManager fragmentManager = getFragmentManager();
//        Log.e("fragmentManager","fragmentManager:"+fragmentManager);
//        startActivity(new Intent(this,SubActivity.class));

//        ZToast zToast = new ZToast(this);
//        zToast.show("sadsada");

//        Toast.makeText().show();
        CustomToast.make(this, 4000,viewById.getText().toString()).show();

    }
}
