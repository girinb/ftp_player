package com.bf.lineplus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MainActivity extends AppCompatActivity {
	//변수 선언부
	RecyclerView rv_mainlist;
	ArrayList<note_item> itemlist;
	container itemAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		//변수 초기화

		rv_mainlist = findViewById(R.id.id_rv_mainlist);
		itemlist = new ArrayList<>();


		/*메모장 초기화부
		무조건 오픈부터하고 에러시에는 DB초기화
		XML과 예제  tdb생성

		 */

//		deleteFile("note_list_db.xml");

		try {
			FileInputStream fis = openFileInput
					("note_list_db.xml");//
			StringBuffer sb = new StringBuffer();
			byte Buffer[] = new byte[1024];
			int n = 0;
			while ((n = fis.read(Buffer)) != -1) {
				sb.append(new String(Buffer, 0, n));
			}
			tools.log(sb.toString());
			fis.close(); //파일입력스트림 닫기

		} catch (Exception e) {
			tools.log(e.toString());
			try {
				FileOutputStream fos = openFileOutput
						("note_list_db.xml", // 파일명 지정
								Context.MODE_PRIVATE);// 저장모드
				PrintWriter out = new PrintWriter(fos);
				out.print("<NOTES>\n" +
						"<note>\n" +
						"<time>20200220022022</time>\n" +
						"<title>처음 메모장을 쓰시는 분께 감사드립니다.</title>\n" +
						"<imagelist></imagelist>\n" +
						"</note>\n" +
						"</NOTES>\n");
				out.flush();
				out.close();

				FileOutputStream fos2 = openFileOutput
						("20200220022022.tdb", // 파일명 지정
								Context.MODE_PRIVATE);// 저장모드
				PrintWriter out2 = new PrintWriter(fos);
				out2.print("행복하세요.");
				out2.flush();
				out2.close();

				try {
					FileInputStream fis = openFileInput
							("note_list_db.xml");//
					StringBuffer sb = new StringBuffer();
					byte dataBuffer[] = new byte[1024];

					int n = 0;
					while ((n = fis.read(dataBuffer)) != -1) {
						sb.append(new String(dataBuffer, 0, n));
					}
					fis.close();

					tools.log(sb.toString());

					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document doc = builder.parse(new InputSource(new StringReader(sb.toString())));
					Element order = doc.getDocumentElement();
					

				} catch (Exception e3) {
				}


			} catch (Exception e2) {
				tools.log(e2.toString());
			}
		}


		ArrayList<String> t_imgs = new ArrayList<>();

//		t_imgs.add("dddd");
//		itemlist.add(new note_item("asdasd1","asddsad",t_imgs));
//		itemlist.add(new note_item("asdasd2","asddsad",t_imgs));
//		itemlist.add(new note_item("asdasd3","asddsad",t_imgs));
//		itemlist.add(new note_item("asdasd4","asddsad",t_imgs));
//		itemlist.add(new note_item("asdasd5","asddsad",t_imgs));
//		itemlist.add(new note_item("asdasd6","asddsad",t_imgs));
//		itemlist.add(new note_item("asdasd7","asddsad",t_imgs));

		rv_mainlist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
		FloatingActionButton fab_createnote = findViewById(R.id.fab_createnote);
		itemAdapter = new container(itemlist);
		rv_mainlist.setAdapter(itemAdapter);
//		itemAdapter.notifyDataSetChanged();
		//

		fab_createnote.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent intent = new Intent(getApplicationContext(), Create_Note.class);
				startActivity(intent);


//				Snackbar.make(view, itemlist.get(0).getTitle(), Snackbar.LENGTH_LONG)
//						.setAction("Action", null).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
