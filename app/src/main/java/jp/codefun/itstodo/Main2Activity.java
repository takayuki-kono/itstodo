package jp.codefun.itstodo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jp.codefun.itstodo.MainActivity.todoLists;

public class Main2Activity extends AppCompatActivity {
    static final int MAXDATA = 20;

    public static Map<String, String> data;
    public static List<Map<String, String>> dataList;
    public static ListView listView;
    public static ListViewAdapter adapter2;
    public static String lists[][] = new String[10][5];
    public static int position;
    public static ArrayList<Integer> komokuTaskNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        position = -1;
        lists = MainActivity.lists;
        if(lists == null){
            return;
        }

        dataList = new ArrayList<Map<String, String>>();

        komokuTaskNo = new ArrayList<Integer>();

        // ListViewに表示するためのDATAを作成する
        for (int i = 0; i < MAXDATA; i++) {
            if(lists[i][0] != null) {
                data = new HashMap<String, String>();
                data.put("text1", lists[i][0]);
                data.put("text2", lists[i][3]);
                data.put("text3", lists[i][5]);
                dataList.add(data);
                komokuTaskNo.add(i);
            }
        }

        // アダプターにデータを渡す
        adapter2 = new ListViewAdapter(
                this,
                dataList,
                R.layout.ras,
                new String[] { "text1", "text2", "text3"},
                new int[] { android.R.id.text1,
                        android.R.id.text2,
                        android.R.id.textAssist});

        // アダプターにDATAをSETする
        listView = (ListView) findViewById(R.id.main2list);
        listView.setAdapter(adapter2);
        listView.setTextFilterEnabled(false);

    }

    public void shiftInsert(View view){
        Main3Activity.editFlg = false;

        startActivity(new Intent(getApplicationContext(), Main3Activity.class));

    }

    public void shiftTodo(View view){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

//    public class ListViewAdapter extends SimpleAdapter {
//
//        private LayoutInflater inflater;
//        private List<? extends Map<String, ?>> listData;
//
//        // 各行が保持するデータ保持クラス
//        public class ViewHolder {
//            TextView text1;
//            TextView text2;
//            TextView text3;
//        }
//
//        public ListViewAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
//            super(context, data, resource, from, to);
//            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            this.listData = data;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            final ListViewAdapter.ViewHolder holder;
//
//            // ビューを受け取る
//            View view = convertView;
//
//            if (view == null) {
//                // 画面起動時にViewHolderを作成する
//                view = inflater.inflate(R.layout.ras, parent, false);
//
//                holder = new ViewHolder();
//                holder.text1 = (TextView) view.findViewById(R.id.text1);
//                holder.text2 = (TextView) view.findViewById(R.id.text2);
//
//                view.setTag(holder);
//            } else {
//                // 行選択時などは既に作成されているものを取得する
//                holder = (ListViewAdapter.ViewHolder) view.getTag();
//            }
//
//            // holderにデータをセットする
//            String text1 = ((HashMap<?, ?>) listData.get(position)).get("text1").toString();
//            String text2 = ((HashMap<?, ?>) listData.get(position)).get("text2").toString();
//            holder.text1.setText(text1);
//            holder.text2.setText(text2);
//
//            return view;
//
//        }
//    }

    public void edit(View view){
        //通知フラグオン
//        test_service.tuuchiFlg = true;
        //編集フラグオン
        Main3Activity.editFlg = true;
        // リスト内部番号
        Main3Activity.editTaskNo = komokuTaskNo.get((Integer)view.getTag());

        startActivity(new Intent(getApplicationContext(), Main3Activity.class));
    }

    public void delete(View view){
        //通知フラグオン
//        test_service.tuuchiFlg = true;
        // リスト内部番号
        position = komokuTaskNo.get((Integer)view.getTag()) + 1;
//        int tag = (Integer)view.getTag();
//        // リスト内部番号
//        position = Integer.parseInt(lists[tag][5]);

        // プリファレンスの準備 //
        SharedPreferences pref =
                this.getSharedPreferences( "taskList", Context.MODE_PRIVATE );
        // プリファレンスに書き込むためのEditorオブジェクト取得 //
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean( "exist" + position, false);

        editor.apply();
        editor.commit();

        // 画面再起動
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}


