package md.alishop.enter;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Pros extends AppCompatActivity {
TextView t1;
String mess;
Thread mythread;
EditText tr;
boolean tru;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pros);
tr=(EditText)findViewById(R.id.tr);
        t1=(TextView)findViewById(R.id.textView3);
        mess="select orders.id, servicess.nameofservice,clients.fio,personal.fio,orders.dateoforder,statuss.viewstatus from orders   LEFT outer join servicess on orders.IDsevice=servicess.ID  LEFT outer join clients on orders.IDclient=clients.ID  LEFT outer join personal on orders.IDworker=personal.ID  LEFT outer join" +
                " statuss on orders.statuss=statuss.IDstatus";
        md.alishop.tcp.MessageSender ms=new md.alishop.tcp.MessageSender();
        ms.execute(mess);
        mythread =new Thread(new MyServerThread());
        tru=mythread.isAlive();


            mythread.start();
    }
    public void send (View v)
    {
        System.out.println("Thread of pros is "+tru);
        mess="select orders.id, servicess.nameofservice,clients.fio,personal.fio,orders.dateoforder,statuss.viewstatus from orders   LEFT outer join servicess on orders.IDsevice=servicess.ID  LEFT outer join clients on orders.IDclient=clients.ID  LEFT outer join personal on orders.IDworker=personal.ID  LEFT outer join" +
                " statuss on orders.statuss=statuss.IDstatus";
        md.alishop.tcp.MessageSender ms=new md.alishop.tcp.MessageSender();
        ms.execute(mess);
    }
    public class MyServerThread implements Runnable {
        Socket s;
        ServerSocket ss;
        BufferedReader br;
        InputStreamReader isr;
        public String message;
        Handler h=new Handler();


        @Override
        public void run() {
            try
            {

                ss=new ServerSocket(8081);
                while (message==null)
                {
                    s=ss.accept();
                    isr=new InputStreamReader(s.getInputStream());
                    br=new BufferedReader (isr);
                    message=br.readLine();

                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(message);
                            System.out.println("Recieve:"+message);
                           t1.setText(message);
                           tr.setText(message);
                        }
                    });
                }
            }
            catch (IOException e)
            {e.printStackTrace();}
        }

    }



}
