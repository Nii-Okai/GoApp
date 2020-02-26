package com.wedidsystem.goapp.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.wedidsystem.goapp.helper.Config;
import com.wedidsystem.goapp.adapter.CustomAdapter;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

public class EmailReader extends AsyncTask<Void,Void,Void> {

    private Context context;
    private Session session;
    private Store store;
    private Folder inbox;

    Message[] msgs;

    private ProgressDialog progressDialog;
    private ListView listView;

    public EmailReader(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context,"Retrieving message","Please wait...",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        //Showing a success message
        Toast.makeText(context,"Messages retrieved", Toast.LENGTH_LONG).show();

        CustomAdapter customAdapter = new CustomAdapter(msgs, context);
        listView.setAdapter(customAdapter);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        Properties props = new Properties();

        props.put("mail.imap.host", Config.HOST);
        props.put("mail.imap.port", Config.PORT);

        // SSL setting
        props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.imap.socketFactory.fallback", "false");
        props.setProperty("mail.imap.socketFactory.port", String.valueOf(Config.PORT));

        try {
            session = Session.getDefaultInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(Config.USERNAME, Config.PASSWORD);
                }
            });

            store = session.getStore("imap");
            store.connect(Config.HOST, Config.USERNAME, Config.PASSWORD);
            inbox = store.getFolder("INBOX"); //[Gmail]/Sent Mail
            inbox.open(Folder.READ_ONLY);

            msgs = inbox.getMessages();
            FetchProfile fp = new FetchProfile();
            fp.add(FetchProfile.Item.ENVELOPE);
            inbox.fetch(msgs, fp);
            
        }catch (Exception mex){
            mex.printStackTrace();
        }

        return null;
    }
}
