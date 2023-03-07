package com.vcelani.litabeta;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Date;

public class TlCadastroActivity extends AppCompatActivity {

    private EditText editTextEmbarcacao;
    private EditText editTextDataPartida;
    private EditText editTextNomePassageiro;
    private EditText editTextRg;
    private EditText editTextDataNascimento;
    private RadioButton sexoMasculino, sexoFeminino;
    private EditText editTextTelefone;
    private EditText editTextPais;
    Button botaocad;

    private RadioGroup opcaoSexo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tl_cadastro);

        botaocad = findViewById(R.id.buttonSalvar);

        botaocad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarPassageiro();
            }
        });

        editTextEmbarcacao = findViewById(R.id.editNomeEmbarcacao);
        editTextDataPartida = findViewById(R.id.editDataPartida);
        editTextNomePassageiro = findViewById(R.id.editNome);
        editTextRg = findViewById(R.id.editRg);
        editTextDataNascimento = findViewById(R.id.editdataNasc);
        sexoMasculino = findViewById(R.id.radioButtonMasculino);
        sexoFeminino = findViewById(R.id.radioButtonFeminino);
        editTextTelefone = findViewById(R.id.editTelefone);
        editTextPais = findViewById(R.id.editPais);
        opcaoSexo = findViewById(R.id.radioGroupSexo);



    }



    public void cadastrarPassageiro(){

        if (!TextUtils.isEmpty(editTextEmbarcacao.getText().toString())){




            try {

                int opcaoSelecionada = opcaoSexo.getCheckedRadioButtonId();

                RadioButton rbSexoSelecionado = findViewById(opcaoSelecionada);


               Log.i("algo","entrou");
                SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null );
                String sql = "INSERT INTO passageiros(embarcacao, data_viagem, nome_passageiro, rg, data_nascimento, sexo, telefone, pais) VALUES (?,?,?,?,?,?,?,?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindString(1,editTextEmbarcacao.getText().toString());
                stmt.bindString(2,editTextDataPartida.getText().toString());
                stmt.bindString(3,editTextNomePassageiro.getText().toString());
                stmt.bindString(4,editTextRg.getText().toString());
                stmt.bindString(5,editTextDataNascimento.getText().toString());
                stmt.bindString(6,rbSexoSelecionado.getText().toString());
                stmt.bindString(7,editTextTelefone.getText().toString());
                stmt.bindString(8,editTextPais.getText().toString());
                stmt.execute();
                finish();

                Cursor cursor = bancoDados.rawQuery("SELECT nome_passageiro, sexo, embarcacao, rg, pais, data_nascimento, telefone, data_viagem FROM passageiros", null);

                //indices tabela
                int indiceNome = cursor.getColumnIndex("nome_passageiro");
                int indiceSexo = cursor.getColumnIndex("sexo");
                int indiceEmbarcacao = cursor.getColumnIndex("embarcacao");
                int indiceRg = cursor.getColumnIndex("rg");
                int indicePais = cursor.getColumnIndex("pais");
                int indiceDataNasc = cursor.getColumnIndex("data_nascimento");
                int indiceTelefone = cursor.getColumnIndex("telefone");
                int indiceDataViagem = cursor.getColumnIndex("data_viagem");


                cursor.moveToFirst();
                while (cursor != null){
                    Log.i("RESULTADO - nome:", cursor.getString(indiceNome));
                    Log.i("RESULTADO - sexo:", cursor.getString(indiceSexo));
                    Log.i("RESULTADO - Embarcacao:", cursor.getString(indiceEmbarcacao));
                    Log.i("RESULTADO - rg:", cursor.getString(indiceRg));
                    Log.i("RESULTADO - pais:", cursor.getString(indicePais));
                    Log.i("RESULTADO - data nascimento:", cursor.getString(indiceDataNasc));
                    Log.i("RESULTADO - telefone:", cursor.getString(indiceTelefone));
                    Log.i("RESULTADO - data viagem:", cursor.getString(indiceDataViagem));
                    cursor.moveToNext();
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }



}