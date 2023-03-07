package com.vcelani.litabeta;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class RelatorioActivity extends AppCompatActivity{

    public Button botaoPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);

        botaoPDF = findViewById(R.id.buttonpdf);
        botaoPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gerarPDF();
            }
        });


        try {

            //criar banco
            SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null );

            //criar tabela
            //bancoDados.execSQL("CREATE TABLE IF NOT EXISTS passageiros (embarcacao VARCHAR, data_viagem DATE, nome_passageiro VARCHAR, rg  INT(8), data_nascimento DATE, sexo VARCHAR, telefone INT(11), pais VARCHAR )");

            //inserir dados
            //bancoDados.execSQL("INSERT INTO passageiros(embarcacao, data_viagem, nome_passageiro, rg, data_nascimento, sexo, telefone, pais) VALUES('Dona lita','05/03/2023', 'vinicius', 32352964, '11/12/2000', 'masculino', 92992498996, 'Brasil' )");

            //recuperar dados
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

            TableLayout tabela = findViewById(R.id.minha_tabela);

            while (cursor.moveToNext()) {

                TableRow linha = new TableRow(this);
                TextView coluna1 = new TextView(this);
                TextView coluna2 = new TextView(this);
                TextView coluna3 = new TextView(this);
                TextView coluna4 = new TextView(this);
                TextView coluna5 = new TextView(this);
                TextView coluna6 = new TextView(this);
                TextView coluna7 = new TextView(this);
                TextView coluna8 = new TextView(this);

                coluna1.setText(Integer.toString(indiceNome));
                coluna2.setText(Integer.toString(indiceSexo));
                coluna3.setText(Integer.toString(indiceEmbarcacao));
                coluna4.setText(Integer.toString(indiceRg));
                coluna5.setText(Integer.toString(indicePais));
                coluna6.setText(Integer.toString(indiceDataNasc));
                coluna7.setText(Integer.toString(indiceTelefone));
                coluna8.setText(Integer.toString(indiceDataViagem));

                linha.addView(coluna1);
                linha.addView(coluna2);
                linha.addView(coluna3);
                linha.addView(coluna4);
                linha.addView(coluna5);
                linha.addView(coluna6);
                linha.addView(coluna7);
                linha.addView(coluna8);

                tabela.addView(linha);
            }




        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void gerarPDF() {

        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
        Document document = new Document(PageSize.A3);

        try {
            SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null );
            Cursor cursor = bancoDados.rawQuery("SELECT nome_passageiro, sexo, embarcacao, rg, pais, data_nascimento, telefone, data_viagem FROM passageiros", null);

            int indiceNome = cursor.getColumnIndex("nome_passageiro");
            int indiceSexo = cursor.getColumnIndex("sexo");
            int indiceEmbarcacao = cursor.getColumnIndex("embarcacao");
            int indiceRg = cursor.getColumnIndex("rg");
            int indicePais = cursor.getColumnIndex("pais");
            int indiceDataNasc = cursor.getColumnIndex("data_nascimento");
            int indiceTelefone = cursor.getColumnIndex("telefone");
            int indiceDataViagem = cursor.getColumnIndex("data_viagem");

            //criar um novo arquivo pdf
            String nomeArquivo = "Relatorio.pdf";
            File diretorio = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            File arquivoPDF = new File(diretorio, nomeArquivo);
            FileOutputStream fos = new FileOutputStream(arquivoPDF);
            PdfWriter.getInstance(document, fos);

            document.open();

            // Adiciona um título ao PDF
            Paragraph p = new Paragraph("Relatorio PDF");
            p.setAlignment(1);
            document.add(p);
            p =  new Paragraph("   ");
            document.add(p);

            // Cria uma tabela no PDF

            PdfPTable tabelaPDF = new PdfPTable(8);

            tabelaPDF.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell cabecalho1 = new PdfPCell(new Phrase( "Nome", font1 ));
            PdfPCell cabecalho2 = new PdfPCell(new Phrase("Sexo", font1));
            PdfPCell cabecalho3 = new PdfPCell(new Phrase("Embarcação", font1));
            PdfPCell cabecalho4 = new PdfPCell(new Phrase("Rg", font1));
            PdfPCell cabecalho5 = new PdfPCell(new Phrase("Pais", font1));
            PdfPCell cabecalho6 = new PdfPCell(new Phrase("DT Nascimento", font1));
            PdfPCell cabecalho7 = new PdfPCell(new Phrase("Telefone", font1));
            PdfPCell cabecalho8 = new PdfPCell(new Phrase("DT Viagem", font1));




            // Definir preenchimento das células

            //tabelaPDF.getDefaultCell().setPadding(8);

            cabecalho1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cabecalho2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cabecalho3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cabecalho4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cabecalho5.setHorizontalAlignment(Element.ALIGN_CENTER);
            cabecalho6.setHorizontalAlignment(Element.ALIGN_CENTER);
            cabecalho7.setHorizontalAlignment(Element.ALIGN_CENTER);
            cabecalho8.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabelaPDF.addCell(cabecalho1);
            tabelaPDF.addCell(cabecalho2);
            tabelaPDF.addCell(cabecalho3);
            tabelaPDF.addCell(cabecalho4);
            tabelaPDF.addCell(cabecalho5);
            tabelaPDF.addCell(cabecalho6);
            tabelaPDF.addCell(cabecalho7);
            tabelaPDF.addCell(cabecalho8);


            // Adiciona os dados da tabela ao PDF
            while (cursor.moveToNext()){

                tabelaPDF.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                //tabelaPDF.getDefaultCell().setPaddingRight(5);

                String nome = cursor.getString(indiceNome);
                String sexo = cursor.getString(indiceSexo);
                String embarcacao = cursor.getString(indiceEmbarcacao);
                String rg = cursor.getString(indiceRg);
                String pais = cursor.getString(indicePais);
                String nascimento = cursor.getString(indiceDataNasc);
                String telefone = cursor.getString(indiceTelefone);
                String viagem = cursor.getString(indiceDataViagem);

                tabelaPDF.addCell(new Phrase(nome, font2));
                tabelaPDF.addCell(new Phrase(sexo, font2));
                tabelaPDF.addCell(new Phrase(embarcacao, font2));
                tabelaPDF.addCell(new Phrase(rg, font2));
                tabelaPDF.addCell(new Phrase(pais, font2));
                tabelaPDF.addCell(new Phrase(nascimento, font2));
                tabelaPDF.addCell(new Phrase(telefone, font2));
                tabelaPDF.addCell(new Phrase(viagem, font2));
            }
            document.add(tabelaPDF);

            // Fecha o documento PDF
            document.close();
            Toast.makeText(this, "PDF gerado com sucesso!", Toast.LENGTH_SHORT).show();


        } catch (FileNotFoundException | DocumentException e) {
            throw new RuntimeException(e);
        }
    }
}