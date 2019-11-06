package com.ahnsafety.ex79httprequetvolleytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    EditText etMsg;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName= findViewById(R.id.et_name);
        etMsg= findViewById(R.id.et_message);
        tv= findViewById(R.id.tv);

    }

    public void clickGet(View view) {

        String name= etName.getText().toString();
        String msg= etMsg.getText().toString();

        //Get방식으로 보낼 서버주소
        String serverUrl= "http://tjrrms0809.dothome.co.kr/Android/getTest.php";

        //Get방식을 통해 보낼 데이터를 URL 뒤에
        //붙여서 보내기위해 utf-8fh 인코딩( 한글은 안되서 )
        try {
            name= URLEncoder.encode(name,"utf-8");
            msg= URLEncoder.encode(msg, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String getUrl= serverUrl+"?name="+ name +"&msg="+msg;

        //서버 getTest.php로 보낼 데이터전송 요청객체 생성
        //이때 서버로 돌려받는 결과 Data가 String일때 사용하는 요청객체 생성
        StringRequest stringRequest= new StringRequest(Request.Method.GET, getUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tv.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        //서버와 데이터를 주고받는 요청객체를
        //서버로 보내줄 우체통가은 역할의 객체
        //요청큐(RequestQueue)
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        //우체통에 요청편지 넣기
        //요청큐에 요청객체 추가..
        requestQueue.add(stringRequest);
    }

    public void clickPost(View view) {

        final String name= etName.getText().toString();
        final String msg= etMsg.getText().toString();

        String serverUrl="http://tjrrms0809.dothome.co.kr/Android/postTest.php";

        //postText.php로 보낼 요청객체 생성
        //결과를 String으로 받는 객체
        StringRequest stringRequest= new StringRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tv.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        }){
            //PST방식으로 보낼 데이터를 리턴해주는 콜백 메소드

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String,String> datas= new HashMap<>();
                datas.put("name", name);
                datas.put("msg",msg);

                return datas;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
