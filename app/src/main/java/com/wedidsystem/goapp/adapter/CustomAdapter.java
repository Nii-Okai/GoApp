package com.wedidsystem.goapp.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wedidsystem.goapp.R;

import java.io.IOException;
import java.time.ZoneId;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;

public class CustomAdapter extends BaseAdapter {
    Message[] message;
    Context context;
    LayoutInflater inflater;

    public CustomAdapter(Message[] message, Context context) {
        this.message = message;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return message.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.row, null);
        TextView subject = view.findViewById(R.id.mSubject);
        TextView date = view.findViewById(R.id.mTimestamp);
        TextView body = view.findViewById(R.id.mContent);
        TextView from = view.findViewById(R.id.mFrom);

        try {
            //Object content = message[position].getContent();
            Address[] froms = message[position].getFrom();
            String email = froms == null ? null : ((InternetAddress) froms[0]).getAddress();

            subject.setText(message[position].getSubject());
            from.setText(email + "");
            //date.setText(message[position].getReceivedDate().toString());
            body.setText(message[position].getSubject());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                date.setText(message[position].getReceivedDate().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate() + "");
            }

//            if (content instanceof String)
//            {
//                subject.setText(message[position].getSubject());
//                body.setText((String)content);
//            }
//            else if (content instanceof Multipart)
//            {
//                Multipart mp = (Multipart)content;
//                BodyPart bp = mp.getBodyPart(0);
//                subject.setText(message[position].getSubject());
//                subT.setText((String)bp.getContent());
            //}

        } catch (MessagingException e) {
            e.printStackTrace();
        }


        return view;
    }
}
