package com.example.splitv6.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.splitv6.R;
import com.example.splitv6.contracts.OnFragmentSelectedListener;


public class FragmentSobre extends Fragment
{
	private static final int POSITION = 3;

	private OnFragmentSelectedListener mListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_sobre, container, false);

		WebView wvSobre = (WebView) view.findViewById(R.id.webview_sobre);
		wvSobre.loadDataWithBaseURL(null,
				getActivity().getResources().getString(R.string.texto_sobre),
				"text/html", "UTF-8", null);

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
}
