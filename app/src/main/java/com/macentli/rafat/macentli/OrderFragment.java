package com.macentli.rafat.macentli;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.model.Token;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    EditText azules, amarillas;
    CardInputWidget mCardInputWidget;
    Button button;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mCardInputWidget = (CardInputWidget) view.findViewById(R.id.cardInputWidget);
        button = (Button) view.findViewById(R.id.button);
        azules = (EditText) view.findViewById(R.id.azules);
        amarillas = (EditText) view.findViewById(R.id.amarillas);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View finalView = view;
                Card cardToSave = mCardInputWidget.getCard();

                final String nAzules = azules.getText().toString();
                final String nAmarillas = amarillas.getText().toString();

                if(TextUtils.isEmpty(nAzules)) {
                    azules.setError("Introduce un numero");
                    return;
                }

                if(TextUtils.isEmpty(nAmarillas)) {
                    amarillas.setError("Introduce un numero");
                    return;
                }

                if (cardToSave == null) {
                    Toast.makeText(view.getContext(), "Tarjeta Invalida, intenta de nuevo", Toast.LENGTH_SHORT).show();
                    return;
                }

                Stripe stripe = new Stripe(view.getContext(), "pk_test_Nthl0qAE3Hax18Tk4pRJAMLo");
                stripe.createToken(cardToSave, new TokenCallback() {
                    @Override
                    public void onError(Exception error) {
                        Toast.makeText(finalView.getContext(), "Error al realizar la transaccion, intentalo de nuevo.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(Token token) {
                        String tokenId = token.getId();
                        int cantidadAmarillas = Integer.parseInt(nAmarillas);
                        int cantidadAzules = Integer.parseInt(nAzules);
                        String description = "Azules:" + cantidadAzules + "-" + "Amarillas:" + cantidadAmarillas;
                        int amount = cantidadAmarillas + cantidadAzules;


                        JSONObject body = new JSONObject();
                        try {
                            body.put("token", tokenId);
                            body.put("amount", amount);
                            body.put("description", description);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "http://34.237.236.57:8001/api/stripe", body,
                                // success
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Toast.makeText(finalView.getContext(), "Exito, tu orden ha sido procesada", Toast.LENGTH_LONG).show();
                                    }
                                },
                                // error
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(finalView.getContext(), "Error, orden no realizada, intenta de nuevo.", Toast.LENGTH_LONG).show();
                                    }
                                });
                        VolleySingleton.getInstance(finalView.getContext()).getRequestQueue().add(request);


                    }
                });

            }
        });
    }

}
