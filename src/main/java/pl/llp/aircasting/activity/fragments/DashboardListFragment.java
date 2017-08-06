package pl.llp.aircasting.activity.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import pl.llp.aircasting.Intents;
import pl.llp.aircasting.R;
import pl.llp.aircasting.activity.ChartOptionsActivity;
import pl.llp.aircasting.activity.DashboardActivity;
import pl.llp.aircasting.activity.DashboardBaseActivity;
import pl.llp.aircasting.activity.adapter.StreamAdapter;
import pl.llp.aircasting.activity.adapter.StreamAdapterFactory;
import pl.llp.aircasting.activity.extsens.ExternalSensorActivity;
import pl.llp.aircasting.model.DashboardChartManager;

import static pl.llp.aircasting.Intents.startSensors;
import static pl.llp.aircasting.Intents.stopSensors;

/**
 * Created by radek on 28/06/17.
 */
public class DashboardListFragment extends ListFragment implements View.OnClickListener {
    private View view;
    private Button microphoneButton;
    private Button sensorsButton;
    private StreamAdapterFactory adapterFactory;
    private StreamAdapter adapter;
    private boolean startPopulated;

    public DashboardListFragment() {
    }

    public static DashboardListFragment newInstance(StreamAdapterFactory adapterFactory, boolean startPopulated) {
        DashboardListFragment fragment = new DashboardListFragment();
        fragment.setData(adapterFactory, startPopulated);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dashboard_list_fragment, container, false);

        microphoneButton = (Button) view.findViewById(R.id.dashboard_microphone);
        sensorsButton = (Button) view.findViewById(R.id.dashboard_sensors);

        adapter = adapterFactory.getAdapter((DashboardBaseActivity) getActivity());

        if (startPopulated) {
            setListAdapter(adapter);
        }

        microphoneButton.setOnClickListener(this);
        sensorsButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        adapter.start();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();

        adapter.stop();
    }

    public boolean isAdapterSet() {
        return getListAdapter() != null;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.dashboard_microphone:
                setListAdapter(adapter);
                getActivity().invalidateOptionsMenu();
                break;
            case R.id.dashboard_sensors:
                startActivity(new Intent(getActivity(), ExternalSensorActivity.class));
                break;
        }
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        getContext().startActivity(new Intent(getContext(), ChartOptionsActivity.class));
    }

    private void setData(StreamAdapterFactory adapterFactory, boolean startPopulated) {
        this.adapterFactory = adapterFactory;
        this.startPopulated = startPopulated;
    }
}
