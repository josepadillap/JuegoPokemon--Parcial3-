package com.example.labsoftware06.juegopokemon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class batalla extends AppCompatActivity implements View.OnClickListener{

    int sal1 = 100;
    int sal2 = 100;
    Button bt2;
    TextView pokemon1_nombre;
    TextView pokemon2_nombre;
    ImageView pokemon1_imagen;
    ImageView pokemon2_imagen;
    TextView healt1;
    TextView healt2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batalla);

        pokemon1_nombre= (TextView) findViewById(R.id.pokemon1_nombre);
        pokemon2_nombre= (TextView) findViewById(R.id.pokemon2_nombre);
        pokemon1_imagen= (ImageView) findViewById(R.id.pokemon1);
        pokemon2_imagen= (ImageView) findViewById(R.id.pokemon2);
        healt1 = (TextView) findViewById(R.id.salud);
        healt2 = (TextView)findViewById(R.id.salud2);
        bt2 = (Button)findViewById(R.id.btatac);
        bt2.setOnClickListener(this);
        Random random=new Random();
        int id1= (int) (Math.random()*700);
        int id2=(int) (Math.random()*700);
        healt1.setText(String.valueOf(sal1));
        healt2.setText(String.valueOf(sal2));

        traernombre(id1,pokemon1_nombre);
        traernombre(id2,pokemon2_nombre);

        cargarimagen(id1,pokemon1_imagen);
        cargarimagen(id2,pokemon2_imagen);


    }

    public void descargarimagen(ImageView imageView,String url){
        ImageLoader mImageLoader;
// The URL for the image that is being loaded.


// Get the ImageLoader through your singleton class.
        mImageLoader = MySingleton.getInstance(this).getImageLoader();
        mImageLoader.get(url, ImageLoader.getImageListener(imageView,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher));

    }

    public void cargarimagen(int id, final ImageView imageView){
        MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        String url ="http://pokeapi.co/api/v2/pokemon-form/"+id+"/";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.i("response",response);
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONObject jsonObject1=new JSONObject(jsonObject.getString("sprites"));
                            String url=jsonObject1.getString("front_default").toString();
                            descargarimagen(imageView,url);
                            Log.i("imagen: ", jsonObject1.getString("front_default").toString());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("","That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    public void traernombre(int id, final TextView t){

        MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();

// ...
        String url ="http://pokeapi.co/api/v2/pokemon/"+id+"/";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.i("response",response);
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            Log.i("nombre",jsonObject.getString("name"));
                            t.setText( jsonObject.getString("name"));
                            JSONArray jsonArray=jsonObject.getJSONArray("abilities");
                            for (int i=0;i<jsonArray.length();i++) {

                                Log.i("abilities: ", jsonArray.getJSONObject(i).toString());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("","That didn't work!");
            }
        });
// Add the request to the RequestQueue.

// Add a request (in this example, called stringRequest) to your RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);



    }
    @Override
    public void onClick(View v) {
        //Un id para implementar con un swith de 1 caso
        int id;
        id = v.getId();
        switch (id)
        {
            case R.id.btatac:

                int id1 = (int) (Math.random() * 40);
                int id2 = (int) (Math.random() * 40);

                sal1= sal1 - id1;
                healt1.setText(String.valueOf(sal1));
                sal2 = sal2 - id2;
                healt2.setText(String.valueOf(sal2));

                if (Integer.parseInt(healt1.getText().toString()) <= 0){
                    bt2.setEnabled(false);
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Fin del Juego, Ganaste", Toast.LENGTH_SHORT);

                    toast1.show();
                }else if(Integer.parseInt(healt2.getText().toString()) <= 0) {
                    bt2.setEnabled(false);
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Fin del Juego, Perdiste", Toast.LENGTH_SHORT);

                    toast1.show();
                }else {

                }

                break;
        }
    }

}
