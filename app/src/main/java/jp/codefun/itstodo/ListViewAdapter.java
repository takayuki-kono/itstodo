package jp.codefun.itstodo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jp.codefun.itstodo.MainActivity.afterLists;

public class ListViewAdapter extends SimpleAdapter {

        private LayoutInflater inflater;
        private List<? extends Map<String, ?>> listData;
        private int resource;

        // 各行が保持するデータ保持クラス
        public class ViewHolder {
            TextView text1;
            TextView text2;
            TextView text3;
            RadioButton radioButton;
            Button edit;
            Button delete;
        }

        public ListViewAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.listData = data;
            this.resource = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;

            // ビューを受け取る
            View view = convertView;

            if (view == null) {
                // 画面起動時にViewHolderを作成する
                view = inflater.inflate(resource, parent, false);

                holder = new ViewHolder();
                holder.text1 = (TextView) view.findViewById(R.id.text1);
                holder.text2 = (TextView) view.findViewById(R.id.text2);
                holder.text3 = (TextView) view.findViewById(R.id.text3);

                view.setTag(holder);
            } else {
                // 行選択時などは既に作成されているものを取得する
                holder = (ViewHolder) view.getTag();
            }

            // holderにデータをセットする
            String text1 = ((HashMap<?, ?>) listData.get(position)).get("text1").toString();
            String text2 = ((HashMap<?, ?>) listData.get(position)).get("text2").toString();
            String text3 = ((HashMap<?, ?>) listData.get(position)).get("text3").toString();
            holder.text1.setText(text1);
            holder.text2.setText(text2);
            holder.text3.setText(text3);

            if(R.layout.raw == resource){
                int red = afterLists;
                holder.radioButton = view.findViewById(R.id.radioButton);
                holder.radioButton.setTag(position);
//                holder.radioButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        ((ListView) parent).performItemClick(view, position, R.id.edit);
//                    }
//                });
                if(position < red){
                    holder.text1.setTextColor(Color.RED);
                }
            }

            if(R.layout.ras == resource){
                holder.edit = view.findViewById(R.id.edit);
                holder.edit.setTag(position);
                holder.delete = view.findViewById(R.id.delete);
                holder.delete.setTag(position);
            }

            return view;
        }
    }
