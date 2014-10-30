package com.example.splitv6.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.splitv6.R;
import com.example.splitv6.activities.CriarNovoEventoActivity;
import com.example.splitv6.contracts.OnFragmentSelectedListener;

public class FragmentMain extends Fragment implements OnClickListener
{
	public static final String TAG = "FragmentMain";
	private static final int POSITION = 0;

	private OnFragmentSelectedListener mListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_main, container, false);

		((LinearLayout) view.findViewById(R.id.button_criar_evento)).setOnClickListener(this);

		return view;
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try {
			mListener = (OnFragmentSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " deve implementar OnFragmentSelectedListener");
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();
		mListener.onFragmentSelected(POSITION);
	}

	@Override
	public void onClick(View view)
	{
		// inicia a activity CriarNovoEventoActovity.
		Intent intent = new Intent(getActivity(), CriarNovoEventoActivity.class);
		startActivity(intent);
	}
}
