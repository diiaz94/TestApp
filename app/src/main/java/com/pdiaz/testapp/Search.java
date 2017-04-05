package com.pdiaz.testapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pdiaz.testapp.Adapters.CustomHistoryListAdapter;
import com.pdiaz.testapp.Adapters.CustomSearchListAdapter;
import com.pdiaz.testapp.DTO.Attribute;
import com.pdiaz.testapp.DTO.Content;
import com.pdiaz.testapp.DTO.MainContent;
import com.pdiaz.testapp.DTO.Record;
import com.pdiaz.testapp.DTO.ResultSearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Search.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ProgressBar spinner;
    private OnFragmentInteractionListener mListener;
    private ListView historyList;
    private ListView resultList;
    private HistoryTask mHistoryTask;
    private SearchTask mSearchTask;
    private TestApp mApp;
    private HistoryBD historyBD;
    private EditText inQuery;
    private Button searchBtn;
    private LinearLayout searchBox;
    private TextView noResult;
    private CustomSearchListAdapter mAdapterListResult;
    private ArrayList<Attribute> mDataResultList;

    private CustomHistoryListAdapter mAdapterListHistory;
    private ArrayList<String> mDataHistoryList;

    public Search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search.
     */
    // TODO: Rename and change types and number of parameters
    public static Search newInstance(String param1, String param2) {
        Search fragment = new Search();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = ((TestApp) getActivity().getApplicationContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        historyList = (ListView) view.findViewById(R.id.historial);
        resultList = (ListView) view.findViewById(R.id.search_result);
        spinner = (ProgressBar) view.findViewById(R.id.spinner);
        inQuery = (EditText) view.findViewById(R.id.in_query);
        searchBox = (LinearLayout) view.findViewById(R.id.search_box);
        searchBtn = (Button) view.findViewById(R.id.search_btn);
        noResult = (TextView) view.findViewById(R.id.no_results);

        searchBox.setVisibility(View.GONE);
        historyList.setVisibility(View.GONE);
        resultList.setVisibility(View.GONE);
        noResult.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);

        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (filterLongEnough()) {
                    resultList.setVisibility(View.GONE);
                    noResult.setVisibility(View.GONE);
                    historyList.setVisibility(View.VISIBLE);
                }
            }

            private boolean filterLongEnough() {
                return inQuery.getText().toString().trim().isEmpty();
            }
        };
        inQuery.addTextChangedListener(fieldValidatorTextWatcher);

        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = (String) mAdapterListHistory.getItem(position);
                inQuery.setText(text);
            }

        });
        searchBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View popupView) {
               String s = inQuery.getText().toString().trim();
                if(!s.trim().isEmpty()){
                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    mSearchTask = new SearchTask(s);
                    mSearchTask.execute((Void) null);
                    mHistoryTask = new HistoryTask(s,TypeActionBD.WRITE);
                    mHistoryTask.execute((Void) null);

                }
            }
        });


        mHistoryTask = new HistoryTask("",TypeActionBD.READ);
        mHistoryTask.execute((Void) null);

        mDataHistoryList = new ArrayList<String>();

        mDataResultList = new ArrayList<Attribute>();




        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public enum TypeActionBD {
        READ,
        WRITE
    }
    public class HistoryTask extends AsyncTask<Void, Void, ArrayList<String>> {


        String query;
        TypeActionBD type;

        HistoryTask(String query,TypeActionBD type) {
            this.query = query;
            this.type = type;
        }
        @Override
        protected void onPreExecute() {
            spinner.setVisibility(View.VISIBLE);
        }
        @Override
        protected ArrayList<String> doInBackground(Void... params) {

            historyBD = new HistoryBD(getActivity());

            switch (type){
                case READ:
                    return historyBD.getHistory();
                case WRITE:
                    historyBD.addSearch(query);
                    return historyBD.getHistory();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> list) {

            mHistoryTask = null;
            mDataHistoryList = new ArrayList<String>();
            spinner.setVisibility(View.GONE);
            if (list!=null && !list.isEmpty()){
                mDataHistoryList = list;
                mAdapterListHistory = new CustomHistoryListAdapter(getActivity(),mDataHistoryList);
                historyList.setAdapter(mAdapterListHistory);
            }
            switch (type){
                case READ:
                    historyList.setVisibility(View.VISIBLE);
                    searchBox.setVisibility(View.VISIBLE);
                    break;
                case WRITE:
                    historyList.setVisibility(View.GONE);
                    break;
            }
        }

        @Override
        protected void onCancelled() {
            mHistoryTask = null;
            spinner.setVisibility(View.GONE);
            resultList.setVisibility(View.GONE);
        }
    }

    public class SearchTask extends AsyncTask<Void, Void, ResultSearch> {
        String query;
        SearchTask(String query) {
            this.query = query;
        }
        @Override
        protected void onPreExecute() {
            spinner.setVisibility(View.VISIBLE);
            historyList.setVisibility(View.GONE);
            resultList.setVisibility(View.GONE);
            noResult.setVisibility(View.GONE);
        }
        @Override
        protected ResultSearch doInBackground(Void... params) {
            ResultSearch result = new ResultSearch();
            List<Content> list = new ArrayList<>();
            Map<String, String> data = new HashMap<>();
            data.put("s", query);
            data.put("d3106047a194921c01969dfdec083925", "json");
            Call<ResultSearch> call = mApp.getApiLiverpoolIntance().search(data);

            try {
                Response<ResultSearch> response = call.execute();

                Log.d("mResponseCode::",String.valueOf(response.code()));
                //Log.d(" response.body()::", response.errorBody()   );
                if(response.isSuccessful()){//Created
                    result = response.body();
                }else{
                    Log.d("Register","responseError.::"+response.errorBody().toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception ex){
                ex.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(ResultSearch result) {
            mSearchTask = null;
            mDataResultList = new ArrayList<Attribute>();
            if(result!=null){
                try {
                    ArrayList<MainContent> mainContents = (ArrayList<MainContent>) result.getContent().get(0).getMainContent();
                    for (MainContent  mainContent: mainContents) {
                        if(mainContent.getContents()!=null){
                            for (Content content : mainContent.getContents()) {
                                if(content!=null && content.getRecords()!=null){
                                    for (Record record : content.getRecords()) {
                                        mDataResultList.add(record.getAttribute());
                                    }
                                    if(mDataResultList.isEmpty()){
                                        noResult.setVisibility(View.VISIBLE);
                                        resultList.setVisibility(View.GONE);
                                    }else{
                                        mAdapterListResult = new CustomSearchListAdapter(getActivity(),mDataResultList);
                                        resultList.setAdapter(mAdapterListResult);
                                        resultList.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                }

                            }


                        }
                    }
                }catch (Exception e){
                    Log.e("Error in set data",e.toString());
                }

            }
            spinner.setVisibility(View.GONE);

        }

        @Override
        protected void onCancelled() {
            mSearchTask = null;
            spinner.setVisibility(View.GONE);
            resultList.setVisibility(View.VISIBLE);
        }
    }

}
