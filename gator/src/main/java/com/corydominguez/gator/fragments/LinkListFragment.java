package com.corydominguez.gator.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.corydominguez.gator.R;
import com.corydominguez.gator.adapters.LinkListAdapter;
import com.corydominguez.gator.clients.GatorClient;
import com.corydominguez.gator.handlers.GatorHttpHandler;
import com.corydominguez.gator.models.Link;

import org.apache.http.client.params.ClientPNames;

import java.util.ArrayList;

/**
 * Created by jarrettcoggin on 2/13/14.
 */
public class LinkListFragment extends Fragment {

    protected ListView lvLinks;
    protected LinkListAdapter adapter;
    protected ArrayList<Link> linkList;
    protected GatorHttpHandler gatorHttpHandler;
    protected static GatorClient gatorClient;
    protected ProgressBar pb;

    public ListView getLVLinks(){
        return this.lvLinks;
    }

    public LinkListAdapter getAdapter(){
        return this.adapter;
    }

    public ArrayList<Link> getLinkList() {
        return this.linkList;
    }

    public GatorClient getClient() {
        return gatorClient;
    }

    public void getLinksSinceYoungest() {
        if (linkList.size() > 0) {
            gatorClient.getSince(linkList.get(0).getCreatedAt());
        } else {
            gatorClient.getLast100();
        }
        adapter.notifyDataSetInvalidated();
    }

    public void replaceLinkList(ArrayList<Link> newlinkList) {
        LinkListAdapter newAdapter = new LinkListAdapter(getActivity(), newlinkList);
        newAdapter.setBookmarkMode(adapter.getBookmarkMode());
        newAdapter.setReadMode(adapter.getReadMode());
        adapter = newAdapter;
        linkList = newlinkList;
        lvLinks.setAdapter(adapter);
    }
    // Probably should not use the handler from here, instead instantiate the client
    // that way we can only have one handler and manage whether it is running or not
    public GatorHttpHandler getGatorHttpHandler(){
        return this.gatorHttpHandler;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_linklist, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupAdapter();
        setupViews();
        setupClient();
        gatorClient.getLast100();
    }

    protected void setupAdapter(){
        linkList = new ArrayList<Link>();
        adapter = new LinkListAdapter(getActivity(), linkList);
    }

    protected void setupClient(){
        gatorClient = new GatorClient(pb, adapter);
        gatorClient.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
    }

    protected void setupViews(){
        lvLinks = (ListView) getActivity().findViewById(R.id.lvLinks);
        lvLinks.setAdapter(adapter);
        pb = (ProgressBar) getActivity().findViewById(R.id.pbLoading);
    }


}
