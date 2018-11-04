package md.alishop.enter;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Enter extends AppCompatActivity {
    static String mess="";
    EditText e1,e2;
    ImageView i1;
    Thread mythread;
    boolean tru;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        e1=(EditText)findViewById(R.id.loginn);
        e2=(EditText)findViewById(R.id.password);
        i1=(ImageView) findViewById(R.id.imageView);
        i1.setImageResource(R.drawable.xeon);
        mythread =new Thread(new MyServerThread1());
      tru=mythread.isAlive();

       if (tru == false)
            mythread.start();
    }
    public class MyServerThread1 implements Runnable {
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
                           // peredaca=message;
                            if (message==null)
                            {
                                Toast.makeText(getApplicationContext(),"Ошибка Ввода",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Intent intent =new Intent(  Enter.this,Menu.class);
                                startActivity(intent);
                               onStop();
                               onDestroy();

                            }


                        }
                    });
                }
            }
            catch (IOException e)
            {e.printStackTrace();}
        }

    }
    public void Send1 (View v)
    {
System.out.println("Thread of enter is "+tru);
        mess="Select id  from dbo.enter where loginn='"+e1.getText().toString()+"' and passwordd="+e2.getText().toString()+";";
     md.alishop.tcp.MessageSender ms=new md.alishop.tcp.MessageSender();
        ms.execute(mess);

    }
}
