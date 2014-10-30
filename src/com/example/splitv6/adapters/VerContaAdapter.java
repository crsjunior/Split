package com.example.splitv6.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.splitv6.R;
import com.example.splitv6.model.Pessoa;

public class VerContaAdapter extends ArrayAdapter<Pessoa>
{
	private Context context;
	private int layoutResourceId;
	private List<Pessoa> listPessoas;

	public VerContaAdapter(Context context, int layoutResourceId, List<Pessoa> pessoas)
	{
		super(context, layoutResourceId, pessoas);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.listPessoas = pessoas;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View row = convertView;
		final RowHolder holder;
		final Pessoa pessoa;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new RowHolder();
			holder.textView_nomePessoa = (TextView) row.findViewById(R.id.textview_nome_pessoa);
			holder.textView_qtdProdutosVinculados = (TextView) row.findViewById(R.id.textview_qtd_produtos_vinculados);
			holder.textView_totalConta = (TextView) row.findViewById(R.id.textview_total_conta);

			row.setTag(holder);
		} else {
			holder = (RowHolder) row.getTag();
		}

		pessoa = listPessoas.get(position);

		holder.textView_nomePessoa.setText(pessoa.getNome());
		holder.textView_qtdProdutosVinculados.setText(String.valueOf(pessoa.getProdutos().size()));
		holder.textView_totalConta.setText(pessoa.getTextoValorConta());

		row.setBackgroundResource(R.drawable.selector_list_ver_conta);

		return row;
	}

	private static class RowHolder
	{
		TextView textView_nomePessoa;
		TextView textView_qtdProdutosVinculados;
		TextView textView_totalConta;
	}
}
