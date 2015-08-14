package com.nowait.adapter;



import com.example.nowait.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ×´Ì¬¼ìË÷listviewµÄÊÊÅäÆ÷
 * @author sheng
 *
 */
public class StatulistAdapter extends BaseAdapter {

	String[] statu; 
	Context mcontext;
	
	public StatulistAdapter(Context context,String[] statu){
		this.mcontext = context;
		this.statu = statu;
	}
	
	@Override
	public int getCount() {
		return statu.length;
	}

	@Override
	public Object getItem(int position) {
		return statu[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mcontext);
            convertView = mInflater.inflate(R.layout.statulist_item, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
		}else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

		 viewHolder.name.setText(statu[position]);
		return convertView;
	}
	
	private static class ViewHolder {
		TextView name;
	}

}
