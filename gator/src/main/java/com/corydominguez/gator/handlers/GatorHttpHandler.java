package com.corydominguez.gator.handlers;

import android.util.Log;
import android.widget.ProgressBar;

import com.corydominguez.gator.adapters.LinkListAdapter;
import com.corydominguez.gator.models.Link;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.IOException;
import java.util.ArrayList;

public class GatorHttpHandler extends AsyncHttpResponseHandler {
    public Boolean running;
    public Boolean appendMode;
    private LinkListAdapter adapter;
    protected ProgressBar pb;
    public static final ObjectMapper mapper = new ObjectMapper();

    public GatorHttpHandler(ProgressBar pb, LinkListAdapter adapter) {
        this.adapter = adapter;
        this.running = false;
        this.pb = pb;
    }

    @Override
    public void onStart() {
        super.onStart();
        pb.setVisibility(ProgressBar.VISIBLE);
        running = true;
    }

    @Override
    public void onSuccess(String s) {
        try {
            Log.d("DEBUG", "Making api Call");
            TypeReference<ArrayList<Link>> tr = new TypeReference<ArrayList<Link>>() {};
            ArrayList<Link> newLinks;
            newLinks = mapper.readValue(s, tr);
            Log.d("DEBUG", newLinks.toString());
            dealWithNewLinks(newLinks);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Throwable throwable, String s) {
       // TODO: ConnectionException is thrown becuase EC2 box does not have global dns
       Log.d("ERROR", "Everyone Panic! " + throwable.toString());
       throwable.printStackTrace();
    }

    @Override
    public void onFinish() {
        super.onFinish();
        pb.setVisibility(ProgressBar.INVISIBLE);
        running = false;
    }

    protected void dealWithNewLinks(ArrayList<Link> newLinks) {
        for (int i=0; i < newLinks.size(); i++) {
            Link link = newLinks.get(i);
            if (appendMode) {
                adapter.add(link);
            } else {
                adapter.insert(link, i);
            }
        }
        adapter.notifyDataSetChanged();
    }
}

