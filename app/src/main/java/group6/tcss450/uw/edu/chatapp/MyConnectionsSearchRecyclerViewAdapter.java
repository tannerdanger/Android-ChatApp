package group6.tcss450.uw.edu.chatapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import group6.tcss450.uw.edu.chatapp.contacts.ConnectionsSearchFragment;
import group6.tcss450.uw.edu.chatapp.contacts.ConnectionsSearchFragment.OnConnectionSearchFragmentInteractionListener;
import group6.tcss450.uw.edu.chatapp.contacts.Connection;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Connection} and makes a call to the
 * specified {@link OnConnectionSearchFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyConnectionsSearchRecyclerViewAdapter extends RecyclerView.Adapter<MyConnectionsSearchRecyclerViewAdapter.ViewHolder> {

    private final List<Connection> mValues;
    private final OnConnectionSearchFragmentInteractionListener mListener;

    public MyConnectionsSearchRecyclerViewAdapter(List<Connection> items, ConnectionsSearchFragment.OnConnectionSearchFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_connectionssearch, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mConnection = mValues.get(position);
        holder.mUsername.setText(mValues.get(position).getUsername());
        holder.mEmail.setText(mValues.get(position).getEmail());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onConnectionSearchFragmentInteraction(holder.mConnection);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mUsername;
        public final TextView mEmail;
        public Connection mConnection;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUsername = (TextView) view.findViewById(R.id.tv_connectionsearch_user);
            mEmail = (TextView) view.findViewById(R.id.tv_connectionsearch_email);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mUsername.getText() + "'";
        }
    }
}
