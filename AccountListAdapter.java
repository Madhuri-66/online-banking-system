package com.onlinebanking;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.List;

public class AccountListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<AccountsView> Items;


	public AccountListAdapter(Activity activity, List<AccountsView> Items) {
		this.activity = activity;
		this.Items = Items;
	}

	@Override
	public int getCount() {
		return Items.size();
	}

	@Override
	public Object getItem(int location) {
		return Items.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.list_row, null);


		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView panno = (TextView) convertView.findViewById(R.id.panno);
		TextView contact = (TextView) convertView.findViewById(R.id.contact);
		TextView amount = (TextView) convertView.findViewById(R.id.amount);
		TextView actype = (TextView) convertView.findViewById(R.id.actype);



		AccountsView m = Items.get(position);

		name.setText(m.getName());
		panno.setText(m.getPancard());
		contact.setText(m.getPhoneno());
		amount.setText(m.getAmount());
		actype.setText(m.getAccnttype());

		return convertView;
	}

}