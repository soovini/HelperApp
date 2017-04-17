package kr.ac.incheon.ns.helperapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.ac.incheon.ns.helperapp.model.PointInfoItem;
import kr.ac.incheon.ns.helperapp.utility.Log;
import kr.ac.incheon.ns.helperapp.utility.Preference;
import kr.ac.incheon.ns.helperapp.utility.ServerUrl;
import kr.ac.incheon.ns.helperapp.volly.GsonPostRequest;


/**
 * Created by subin on 2017-04-10.
 */

public class MyPointListFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener {
    private View mRootView;

    public MyPointListFragment newInstance(String text){
        MyPointListFragment mFragment = new MyPointListFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("USER_TYPE", text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    private String mUserType;
    private String mUserId;
    private PointListAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mUserType = getArguments().getString("USER_TYPE");
        mUserId = Preference.getValue(MyApp.getApplicationCtx(), "id", "");
        mRootView = inflater.inflate(R.layout.fragment_my_point_list, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout)mRootView.findViewById(R.id.fragment_listview_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(new int[]{R.color.colorPrimary});

        return mRootView;
    }

    private RequestQueue mQueue;
    private List<PointInfoItem.Point> mList = new ArrayList<PointInfoItem.Point>();
    private GsonPostRequest<PointInfoItem> postRequest;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        mQueue = MyApp.getInstance().getRequestQueue();

        mAdapter = new PointListAdapter(getActivity(), mList);
        getListView().setAdapter(mAdapter);
        //getListView().setOnItemClickListener(this);

        loadPage();

    }

    private void loadPage() {
        String url = ServerUrl.urlMaker("getMyPointList.php");

        Map<String, String> params = new HashMap<String, String>();
        String get_id = Preference.getValue(getActivity().getApplicationContext(),"id","");
        params.put("id", get_id);

        mList.clear();
        mQueue.stop();

        postRequest = new GsonPostRequest<PointInfoItem>(Request.Method.POST,
                url,
                PointInfoItem.class,
                params,
                null,
                createMyReqSuccessListener(),
                createMyReqErrorListener());

        mQueue.add(postRequest);
        mQueue.start();
    }

    // 통신 후 발생하는 이벤트
    private Response.Listener<PointInfoItem> createMyReqSuccessListener() {
        return new Response.Listener<PointInfoItem>() {
            @Override
            public void onResponse(PointInfoItem response) {
                int size = response.getPoint().size();
                for(int i = 0 ; i < size ; i++) {
                    mList.add(response.getPoint().get(i));
                }

                mAdapter.notifyDataSetChanged();
                setRefreshing(false);

            }
        };
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(error.getMessage());
                Log.showToast(error.getMessage());
                setRefreshing(false);
            }
        };
    }
    public void setRefreshing(boolean refreshing) {
        if (mSwipeRefreshLayout != null) mSwipeRefreshLayout.setRefreshing(refreshing);
    }

    @Override
    public void onRefresh() {
        loadPage();
    }

    public class PointListAdapter extends BaseAdapter {

        private List<PointInfoItem.Point> mItem;
        private LayoutInflater mInflater;
        //private Context mContext;
        private String mUserId;
        private Activity mActivity;

        public PointListAdapter(Activity activity, List<PointInfoItem.Point> items) {
            this.mInflater = LayoutInflater.from(activity);
            this.mActivity = activity;
            this.mUserId = Preference.getValue(MyApp.getApplicationCtx(), "id", "");
            this.mItem = items;
        }

        @Override
        public Object getItem(int position) {
            return mItem.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return mItem.size();
        }

        ViewHolder viewHolder;
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {

                convertView = mInflater.inflate(R.layout.fragment_my_point_list_item, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                viewHolder.tv_ins_date = (TextView) convertView.findViewById(R.id.tv_ins_date);
                viewHolder.tv_point = (TextView) convertView.findViewById(R.id.tv_point);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tv_name.setText(mItem.get(position).getId());
            viewHolder.tv_ins_date.setText(mItem.get(position).getIns_date());
            viewHolder.tv_point.setText(mItem.get(position).getPoint());

            return convertView;
        }

        private class ViewHolder {
            TextView tv_name;
            TextView tv_ins_date;
            TextView tv_point;
        }

    }
}
